package api.tests.register;

import api.tests.BaseTest;
import config.Endpoints;
import entity.Phone;
import entity.users.DataUser;
import entity.users.Users;
import helpers.AssertionsHelper;
import helpers.users.UsersDatabaseHelper;
import helpers.utils.DataGeneratorEntity;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.responses.RequestSpec;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Feature("Регистрация")
@DisplayName("Набор тестов функционала регистрации по SMS (phone)")
public class RegisterCodeTest extends BaseTest {

    @Test
    @DisplayName("Отправка валидного кода и номера, которого нет в базе пользователей")
    void postPhoneAndCode() throws IOException {
        //getOnlyToken();
        Users user = DataGeneratorEntity.generateNewUser();
        Phone phone = given().spec(requestSpec).body(user)
                .when().post(Endpoints.API_LOGIN_SMS)
                .then().spec(RequestSpec.checkingResponseFromServer200()).extract().as(Phone.class);
        phone.setPhone(user.getPhone());
        DataUser dataUser = given().spec(requestSpec).body(phone)
                .when().post(Endpoints.API_LOGIN_SMS_CHECK)
                .then().spec(RequestSpec.checkingResponseFromServer201()).extract().as(DataUser.class);
        System.out.println(dataUser.toString());
        assertTrue(AssertionsHelper.checkRegisterUser(dataUser));
        assertEquals(dataUser.getData().getPhone(), phone.getPhone());
    }

    @Test
    @DisplayName("Отправка валидного кода и номера, который уже есть в базе пользователей")
    void postPhoneAndCodeExistDatabase() throws IOException {
        //getOnlyToken();
        Users user = UsersDatabaseHelper.getRandUserFromDatabase();
        Phone phone = given().spec(requestSpec).body(user)
                .when().post(Endpoints.API_LOGIN_SMS)
                .then().spec(RequestSpec.checkingResponseFromServer200()).extract().as(Phone.class);
        phone.setPhone(user.getPhone());
        DataUser dataUser = given().spec(requestSpec).body(phone)
                .when().post(Endpoints.API_LOGIN_SMS_CHECK)
                .then().spec(RequestSpec.checkingResponseFromServer200()).extract().as(DataUser.class);
        assertFalse(AssertionsHelper.checkRegisterUser(dataUser));
        assertEquals(dataUser.getData().getPhone(), phone.getPhone());
    }

    @Test
    @DisplayName("Отправка валидного кода и номера без кукисов")
    void checkXcsrfToken() throws IOException {
        //getOnlyToken();
        Users user = DataGeneratorEntity.generateNewUser();
        Phone phone = given().spec(requestSpec).body(user)
                .when().post(Endpoints.API_LOGIN_SMS)
                .then().spec(RequestSpec.checkingResponseFromServer200()).extract().as(Phone.class);
        phone.setPhone(user.getPhone());

        given().spec(requestSpecNotToken).body(phone)
                .when().post(Endpoints.API_LOGIN_SMS_CHECK)
                .then().spec(RequestSpec.checkingResponseFromServer419());
    }

    @Test
    @DisplayName("Отправка невалидного кода (5 цифр) и номера")
    void incorrectCode() throws IOException {
        //getOnlyToken();
        Users user = DataGeneratorEntity.generateNewUser();
        Phone phone = given().spec(requestSpec).body(user)
                .when().post(Endpoints.API_LOGIN_SMS)
                .then().spec(RequestSpec.checkingResponseFromServer200()).extract().as(Phone.class);
        phone.setPhone(user.getPhone());
        phone.setCode(phone.getCode() + 1);
        System.out.println(phone.getCode());
        String message = given().spec(requestSpec).body(phone)
                .when().post(Endpoints.API_LOGIN_SMS_CHECK).then().spec(RequestSpec.checkingResponseFromServer422()).extract().body().asString();
        AssertionsHelper.compareMessageSizeCode(message);
    }

    @Test
    @DisplayName("Отправка null кода и номера")
    void nullCode() throws IOException {
        //getOnlyToken();
        Users user = DataGeneratorEntity.generateNewUser();
        Phone phone = given().spec(requestSpec).body(user)
                .when().post(Endpoints.API_LOGIN_SMS)
                .then().spec(RequestSpec.checkingResponseFromServer200()).extract().as(Phone.class);
        phone.setPhone(user.getPhone());
        phone.setCode(null);
        System.out.println(phone.getCode());
        String message = given().spec(requestSpec).body(phone)
                .when().post(Endpoints.API_LOGIN_SMS_CHECK).then().spec(RequestSpec.checkingResponseFromServer422()).extract().body().asString();
        AssertionsHelper.compareMessageRequiredCode(message);
    }
}
