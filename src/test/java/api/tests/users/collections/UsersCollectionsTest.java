package api.tests.users.collections;

import api.tests.BaseTest;
import config.Endpoints;
import entity.users.UserRolesAgencyPermissions;
import entity.users.Users;
import helpers.AssertionsHelper;
import helpers.users.UsersDatabaseHelper;
import helpers.users.UsersHelper;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import services.responses.RequestSpec;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Feature("Пользователи")
@DisplayName("Набор проверок при получении всех юзеров")
public class UsersCollectionsTest extends BaseTest {

    @Test
    @DisplayName("Проверка получения всех юзеров через API без токена авторизации")
    void getUsersFormPageUnauthenticatedUser(){
        List<String> links = UsersHelper.getUsersPages(requestSpecAuth);
        for(String url : links){
            UsersHelper.getUnauthenticatedUserRequest(requestSpec, url);
        }
    }

    @Test
    @DisplayName("Проверка получения всех юзеров через API")
    void checkAllCollectionUser(){
        List<String> pages = UsersHelper.getUsersPages(requestSpecAuth);
        List<Users> listFromUsers = UsersHelper.getUsersFromPage(pages, requestSpecAuth);
        List<Users> listUsersFromDatabase = UsersDatabaseHelper.getUsesCollection();
        AssertionsHelper.comparisonCollections(listUsersFromDatabase, listFromUsers);
    }

    @Test
    @DisplayName("Получение информации о моем аккаунте")
    void getInfoMe(){
        UserRolesAgencyPermissions usersGet = given().spec(requestSpecAuth)
                .when().get(Endpoints.API_ME)
                .then().spec(RequestSpec.checkingResponseFromServer200()).extract().body().as(UserRolesAgencyPermissions.class);
        assertEquals(usersGet.getEmail(), (user.getEmail()));
    }

    @Test
    @DisplayName("Получение информации о моем аккаунте без авторизации")
    void getInfoMeIncorrect(){
        given().spec(requestSpec).when().get(Endpoints.API_ME).then().spec(RequestSpec.checkingResponseFromServer401());
    }
}
