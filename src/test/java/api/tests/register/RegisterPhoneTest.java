package api.tests.register;

import api.tests.BaseTest;
import config.Endpoints;
import entity.Phone;
import entity.users.Users;
import helpers.AssertionsHelper;
import helpers.users.UsersDatabaseHelper;
import helpers.utils.DataGeneratorEntity;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import services.responses.RequestSpec;

import static io.restassured.RestAssured.*;

@Feature("Регистрация")
@DisplayName("Набор тестов функционала регистрации по SMS (code)")
public class RegisterPhoneTest extends BaseTest {

    @Test
    @DisplayName("Получение кода для смс по существующему номеру в DB")
    void getCodeByNumberFromDatabase() {
        Users user = UsersDatabaseHelper.getRandUserFromDatabase();
        Phone phone = given().spec(requestSpec).body(user)
                .when().post(Endpoints.API_LOGIN_SMS)
                .then().spec(RequestSpec.checkingResponseFromServer200()).extract().as(Phone.class);
        phone.setPhone(user.getPhone());
        AssertionsHelper.notNullFieldObj(phone);
    }

    @Test
    @DisplayName("Получение кода для смс по несуществующему номеру в DB")
    void getCodeFromNewUser() {
        Users user = DataGeneratorEntity.generateNewUser();
        Phone phone = given().spec(requestSpec).body(user)
                .when().post(Endpoints.API_LOGIN_SMS)
                .then().spec(RequestSpec.checkingResponseFromServer200()).extract().as(Phone.class);
        phone.setPhone(user.getPhone());
        AssertionsHelper.notNullFieldObj(phone);
    }

    @Test
    @DisplayName("Отправка невалидного номера телефона")
    void postIncorrectNumberPhone() {
        Phone phone = new Phone();
        phone.setPhone(null);
        given().spec(requestSpec).body(phone)
                .when().post(Endpoints.API_LOGIN_SMS)
                .then().spec(RequestSpec.checkingResponseFromServer422());
    }

    @Test
    @DisplayName("Отправка валидного номера телефона без csrf токена")
    void postNotToken() {
        Users user = DataGeneratorEntity.generateNewUser();
        given().spec(requestSpecNotToken).body(user)
                .when().post(Endpoints.API_LOGIN_SMS)
                .then().spec(RequestSpec.checkingResponseFromServer419());
    }

    @Test
    @DisplayName("Отправка валидного номера телефона несколько раз подряд")
    void doublePostNumberPhone() {
        Users user = DataGeneratorEntity.generateNewUser();
        given().spec(requestSpec).body(user)
                .when().post(Endpoints.API_LOGIN_SMS)
                .then().spec(RequestSpec.checkingResponseFromServer200());
        String message = given().spec(requestSpec).body(user)
                .when().post(Endpoints.API_LOGIN_SMS).getBody().prettyPrint();
        AssertionsHelper.compareMessageCode(message);
    }
}
