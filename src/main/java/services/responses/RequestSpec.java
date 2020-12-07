package services.responses;

import helpers.agencies.AgenciesHelper;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static io.restassured.RestAssured.oauth2;
import static org.hamcrest.Matchers.*;

public class RequestSpec {

    @Step("Отправка запроса с токеном авторизации {authToken}")
    public static RequestSpecification requestAuthorizationToken(String authToken, Map<String, String> cookies) throws UnsupportedEncodingException {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setAuth(oauth2(authToken))
                .addHeader("X-XSRF-TOKEN", AgenciesHelper.decodeCookieToken(cookies))
                .addCookies(cookies)
                .log(LogDetail.ALL)
                .build();
    }
    
    @Step("Отправка запроса без токена")
    public static RequestSpecification requestWithoutAuthorizationToken(Map<String, String> cookies) throws UnsupportedEncodingException {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .addHeader("Accept-Encoding", "gzip, deflate, br")
                .addHeader("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                .addHeader("X-XSRF-TOKEN", AgenciesHelper.decodeCookieToken(cookies))
                .addCookies(cookies)
                .log(LogDetail.ALL)
                .build();
    }

    @Step("Отправка запроса без токена")
    public static RequestSpecification requestNotXcsrfToken(Map<String, String> cookies) throws UnsupportedEncodingException {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .addCookies(cookies)
                .log(LogDetail.ALL)
                .build();
    }
    
    @Step("Проверка ответа от сервера. Ожидаемый ответ - 200")
    public static ResponseSpecification checkingResponseFromServer200(){
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .expectStatusCode(200)
                .build();
    }

    @Step("Проверка ответа от сервера. Ожидаемый ответ - 201")
    public static ResponseSpecification checkingResponseFromServer201() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .log(LogDetail.ALL)
                .build();
    }
    
    @Step("Проверка ответа от сервера. Ожидаемый ответ - 401")
    public static ResponseSpecification checkingResponseFromServer401(){
        return new ResponseSpecBuilder()
                .expectStatusCode(401)
                .log(LogDetail.ALL)
                .expectBody(containsString("Unauthenticated"))
                .build();
    }
    
    @Step("Проверка ответа от сервера. Ожидаемый ответ - 404")
    public static ResponseSpecification checkingResponseFromServer404(){
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .log(LogDetail.ALL)
                .expectBody(containsString("No query results for model"))
                .build();
    }
    
    @Step("Проверка ответа от сервера. Ожидаемый ответ - 422")
    public static ResponseSpecification checkingResponseFromServer422(){
        return new ResponseSpecBuilder()
                .expectStatusCode(422)
                .log(LogDetail.ALL)
                .expectBody(containsString("The given data was invalid"))
                .build();
    }

    @Step("Проверка ответа от сервера. Ожидаемый ответ - 419")
    public static ResponseSpecification checkingResponseFromServer419() {return new ResponseSpecBuilder()
            .expectStatusCode(419)
            .log(LogDetail.ALL)
            .expectBody(containsString("CSRF token mismatch"))
            .build();

    }
}
