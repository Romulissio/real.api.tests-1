package api.tests.users.updated;

import api.tests.BaseTest;
import config.Endpoints;
import entity.users.UserRolesAgencyPermissions;
import entity.users.Users;
import helpers.users.UsersDatabaseHelper;
import helpers.users.UsersHelper;
import helpers.utils.DataGeneratorEntity;
import helpers.utils.DataGeneratorUtils;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.responses.RequestSpec;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.given;

@Feature("Пользователи")
@DisplayName("Набор проверок изменения юзера")
public class UpdatedUsersTest extends BaseTest {

    private DataGeneratorUtils dataGeneratorUtils = new DataGeneratorUtils();

    @Test
    //TODO ^ (SQL: select * from \"users\" where \"api_token\" = 24|0rXmv4oviv3czy5UjuWHs7BmUAV7IbRPmD1HPJHg limit 1)",
    @DisplayName("Изменение информации об аккаунте")
    void updateMeInfo() {
        Response response = given().spec(requestSpecAuth).when().get(Endpoints.API_ME);
        UserRolesAgencyPermissions user = response.as(UserRolesAgencyPermissions.class);

        user.setLast_name(dataGeneratorUtils.generateLastName());

        Response response1 = given().spec(requestSpecAuth).body(user).put(Endpoints.API_USERS_ID, 1L);
        response1.then().spec(RequestSpec.checkingResponseFromServer200());
        response1.getBody().prettyPrint();
        System.out.println(response1.statusCode());

    }

    @Test
    @DisplayName("Изменение информации в аккаунте не аутентифицируемым пользователем")
    void updateMeInfoUnauthenticatedUser(){
        UserRolesAgencyPermissions usersGet = given().spec(requestSpecAuth)
                .when().get(Endpoints.API_ME)
                .then().spec(RequestSpec.checkingResponseFromServer200()).extract().body().as(UserRolesAgencyPermissions.class);
        given().spec(requestSpec).body(usersGet).put(Endpoints.API_USERS_ID, usersGet.getId())
                .then().spec(RequestSpec.checkingResponseFromServer401().expect());
    }

    @Test
    @DisplayName("Изменение информации несуществующего аккаунта")
    void updateMeInfoNotFound(){
        given().spec(requestSpecAuth).body("").put(Endpoints.API_USERS_ID, 333)
                .then().spec(RequestSpec.checkingResponseFromServer404());
    }

    @Test
    @DisplayName("Изменение информации невалидными данными аккаунта")
    void updateMeInfoInvalidData(){
        Users user = UsersDatabaseHelper.getRandUserFromDatabase();
        given().spec(requestSpecAuth).body("").put(Endpoints.API_USERS_ID, user.getId())
                .then().spec(RequestSpec.checkingResponseFromServer422());
    }

    @Test
    @DisplayName("Изменение поля phone невалидными данными")
    void updateMeInfoCloneData(){
        UserRolesAgencyPermissions usersGet = given().spec(requestSpecAuth)
                .when().get(Endpoints.API_ME)
                .then().spec(RequestSpec.checkingResponseFromServer200()).extract().body().as(UserRolesAgencyPermissions.class);
        usersGet.setPhone("test");
        String response = given().spec(requestSpecAuth).body(usersGet).put(Endpoints.API_USERS_ID, usersGet.getId())
                .then().spec(RequestSpec.checkingResponseFromServer422()).extract().body().asString();
    }

    @Test
    @DisplayName("Изменение статуса активности юзера")
    void updateStatusUser() {
        Users randUser = UsersDatabaseHelper.getRandUserFromDatabase();
        String updateActiveStatusBody = UsersHelper.swapActiveStatus(randUser);
        Response response = given().spec(requestSpecAuth).body(updateActiveStatusBody).put(Endpoints.API_USERS_SET_ACTIVE);
        Users updateRandUser = UsersDatabaseHelper.getUserByIdFromDatabase(randUser.getId());
        assertAll(() -> assertEquals(200, response.statusCode()),
                () -> assertNotSame(randUser.getActive(), (updateRandUser.getActive())));
    }

    @Test
    @DisplayName("Изменение статуса активности юзера не аутентифицированным юзером")
    void updateStatusUserUnauthenticatedUser() {
        Users randUser = UsersDatabaseHelper.getRandUserFromDatabase();
        String updateActiveStatusBody = UsersHelper.swapActiveStatus(randUser);
        Response response = given().spec(requestSpec).body(updateActiveStatusBody).put(Endpoints.API_USERS_SET_ACTIVE);
        Users updateRandUser = UsersDatabaseHelper.getUserByIdFromDatabase(randUser.getId());
        assertAll(() -> assertEquals(401, response.statusCode()),
                () -> assertSame(randUser.getActive(), (updateRandUser.getActive())));
    }

    @Test
    @Disabled
    //TODO не работает  "message": "Server Error"
    @DisplayName("Создание юзера")
    void createUser(){
        Users newUser = DataGeneratorEntity.generateNewUser();
        List<Long> listRoleId = new ArrayList<>();
        listRoleId.add(1L);
        newUser.setRole_ids(listRoleId);

        String str = "{\n" +
                "  \"email\": \"jack@daniels.com\",\n" +
                "  \"password\": \"password\",\n" +
                "  \"password_confirmation\": \"password\",\n" +
                "  \"phone\": \"+79311121111\",\n" +
                "  \"first_name\": \"Jack\",\n" +
                "  \"last_name\": \"Daniels\",\n" +
                "  \"middle_name\": \"Whiskey\",\n" +
                "  \"type\": \"seller\",\n" +
                "  \"agency_id\": 1,\n" +
                "  \"role_ids\": [\n" +
                "    2\n" +
                "  ]\n" +
                "}";
        Response response = given().spec(requestSpecAuth).body(str).when().post(Endpoints.API_USERS);
        System.out.println(response.getBody().prettyPrint());
    }
}
