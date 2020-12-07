package helpers.users;

import config.Endpoints;
import entity.users.DataListUsers;
import entity.users.UserRolesAgencyPermissions;
import entity.users.Users;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import services.responses.RequestSpec;
import java.util.ArrayList;
import java.util.List;

import static helpers.agencies.AgenciesHelper.getLinksAgencies;
import static io.restassured.RestAssured.given;

public class UsersHelper {

    /** Превращение списка UserRolesAgencyPermissions в Users */
    public static List<Users> swapInUsersList(List<UserRolesAgencyPermissions> list){
        List<Users> res = new ArrayList<>();
        for(UserRolesAgencyPermissions o : list){
            Users userFromAllUser = Users.builder()
                    .id(o.getId())
                    .first_name(o.getFirst_name())
                    .middle_name(o.getMiddle_name())
                    .last_name(o.getLast_name())
                    .email(o.getEmail())
                    .type(o.getType())
                    .phone(o.getPhone())
                    .created_at(o.getCreated_at())
                    .build();
            res.add(userFromAllUser);
        }
        return res;
    }

    /** Отдача пэйджей с юзерами */
    public static List<String> getUsersPages(RequestSpecification requestSpecAuth){
        //DataListUsers dataListUsers = given().spec(requestSpecAuth).when().get(Endpoints.API_USERS).as(DataListUsers.class);

        Response response = given().spec(requestSpecAuth).when().get(Endpoints.API_USERS);
        System.out.println(response.statusCode());
        response.getBody().prettyPrint();


        DataListUsers dataListUsers = response.as(DataListUsers.class);


        return getLinksAgencies(dataListUsers.getMeta().getLinks());
    }

    @Step("Получение всех юзеров через API")
    public static List<Users> getUsersFromPage(List<String> pages, RequestSpecification requestSpecAuth) {
        List<Users> users = new ArrayList<>();
        for(String str : pages){
            DataListUsers dataListUsers = requestPages(requestSpecAuth, str);
            users.addAll(dataListUsers.getData());
        }
        //return UsersHelper.swapInUsersList(users);
        return users;
    }

    @Step("Запрос на сервер без токена аутентификации")
    public static void getUnauthenticatedUserRequest(RequestSpecification requestSpec, String url){
        given().spec(requestSpec).when().get(url).then().spec(RequestSpec.checkingResponseFromServer401());
    }

    @Step("Запрос на сервер по адресу: {url}")
    public static DataListUsers requestPages(RequestSpecification requestSpecAuth, String url){
        DataListUsers users = given().spec(requestSpecAuth)
                .when().get(url)
                .then().spec(RequestSpec.checkingResponseFromServer200()).extract().body().as(DataListUsers.class);
        Allure.addAttachment("Полученные данные от сервера", users.toString());
        return users;
    }

    /** Отдача коллекции всех отсортированныйх юзеров через API */
    public static List<Users> getSortedCollectionUsersFromApi(RequestSpecification requestSpecAuth, String sortField){
        List<String> pages = UsersHelper.getUsersPages(requestSpecAuth);
        List<String> sortPages = updateSortedLink(pages,sortField);
        return UsersHelper.getUsersFromPage(sortPages, requestSpecAuth);
    }

    /** Добавление параметра сортировки в адрес запрашиваемой страницы */
    private static List<String> updateSortedLink(List<String> pages, String fieldSort){
        List<String> sortedParam = new ArrayList<>();
        for(String str : pages){
            sortedParam.add(str + "&sort=" + fieldSort);
        }
        return sortedParam;
    }

    /** Изменение статуса юзера на активный */
    public static String swapActiveStatus(Users randUser) {
        Allure.addAttachment("Юзер у которого изменяется статус", randUser.toString());
        boolean b = true;
        String body = "";
        if(!randUser.getId().equals(1L)) {
            if (randUser.getActive()) {
                b = false;
            }
            if (!randUser.getActive()) {
                b = true;
            }
            body = "{\"user_id\": \"" + randUser.getId() + "\", \"active\": " + b + " }";
        }
        return body;
    }
}
