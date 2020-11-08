package services.responses;


import entity.User;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import services.UtilsData;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class ResponseAuth {

    @Step("GET запрос на получение cookies")
    public static Response getAuthTokenAndCookies(String str){
        return get(str);
    }

    @Step("POST запрос на сервер для создания АН")
    public static Response getAuthToken(
            Map<String, String> cookies,
            User user,
            String str) throws UnsupportedEncodingException {
        return given()
                .contentType(ContentType.JSON)
                .header(new Header("X-XSRF-TOKEN", UtilsData.decodeCookieToken(cookies)))
                .body(user)
                .cookies(cookies)
                .post(str);
    }
}
