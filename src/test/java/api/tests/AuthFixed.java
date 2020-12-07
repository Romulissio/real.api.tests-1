package api.tests;

import config.ConfigProperties;
import config.Endpoints;
import entity.users.Users;
import helpers.agencies.AgenciesHelper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import services.responses.RequestSpec;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static io.restassured.authentication.FormAuthConfig.springSecurity;

public class AuthFixed {

    private static Map<String, String> cookies;

    /** спецификация для запроса */
    public static RequestSpecification requestSpec(Map<String, String> cookies) throws UnsupportedEncodingException {
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

    /** спецификация для запроса */
    public static RequestSpecification requestSpecAuth(Map<String, String> cookies) throws UnsupportedEncodingException {
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

    /** админ */
    private static Users getAdminUser() throws IOException {
        return Users.builder()
                .email(ConfigProperties.getValueProperties("email"))
                .password(ConfigProperties.getValueProperties("password")).build();
    }

    public static void main(String[] args) throws IOException {
        RestAssured.baseURI = Endpoints.BASE_URI;

        System.out.println("----------------------- делаем нет запрос за куками (токен и сессия)----------------------- ");
        cookies = given().log().all().basePath(basePath).get(Endpoints.API_CSRF_COOKIE).getCookies();

        System.out.println("----------------------- делаем POST запрос для логина -----------------------");
        Response responseAuth = given().spec(requestSpec(cookies)).body(getAdminUser()).post(Endpoints.API_LOGIN_EMAIL);
        cookies = responseAuth.getCookies();
        System.out.println(responseAuth.statusCode());

        System.out.println("----------------------- делаем GET запрос для получения инфо о аккаунте -----------------------");
        Response responseMe = given().spec(requestSpecAuth(cookies)).get(Endpoints.API_ME);
        System.out.println(responseMe.statusCode());
    }
}
