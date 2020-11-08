package services.responses;

import entity.agencies.Agency;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import services.UtilsData;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ResponseAgency {


    @Step("PUT запрос на сервер по адресу {url} для изменения данных АН")
    public static Response putResponseUpdateAgency(
            Agency newDataAgency,
            String url,
            Map<String, String> cookies,
            String authToken) throws UnsupportedEncodingException {
        return given()
                .auth().oauth2(authToken)
                .contentType(ContentType.JSON)
                .header(new Header("X-XSRF-TOKEN", UtilsData.decodeCookieToken(cookies)))
                .body(newDataAgency)
                .cookies(cookies)
                .put(url);
    }

    @Step("GET запрос на сервер для получения всех ссылок на pages АН")
    public static Response getListAgencies(
            String url,
            Map<String, String> cookies,
            String authToken) throws UnsupportedEncodingException {
        return given()
                .auth().oauth2(authToken)
                .header(new Header("accept", "application/json"))
                .header(new Header("X-XSRF-TOKEN", UtilsData.decodeCookieToken(cookies)))
                .cookies(cookies)
                .get(url);
    }

    @Step("GET запрос на сервер для получения АН по адресу {url}")
    public static Response getAgencyById(
            String url,
            Long id,
            Map<String, String> cookies,
            String authToken) throws UnsupportedEncodingException {
        return given().auth()
                .oauth2(authToken)
                .contentType(ContentType.JSON)
                .header(new Header("X-XSRF-TOKEN", UtilsData.decodeCookieToken(cookies)))
                .cookies(cookies)
                .get(url + "/" + id);
    }

    @Step("POST запрос на сервер для создания АН")
    public static Response postCreateAgency(
            String url,
            Map<String, String> cookies,
            String authToken,
            Agency agency) throws UnsupportedEncodingException {
        return given().auth().oauth2(authToken)
                .contentType(ContentType.JSON)
                .header(new Header("X-XSRF-TOKEN", UtilsData.decodeCookieToken(cookies)))
                .body(agency)
                .cookies(cookies)
                .post(url);
    }

    @Step("PUT запрос на сервер для изменения статуса АН")
    public static Response putUpdateStatusAgency(
            String agenciesApi,
            Map<String, String> cookies,
            String authToken,
            Agency agency,
            Long id) throws UnsupportedEncodingException {
        return given().auth().oauth2(authToken)
                .contentType(ContentType.JSON)
                .header(new Header("X-XSRF-TOKEN", UtilsData.decodeCookieToken(cookies)))
                .body(agency)
                .cookies(cookies)
                .put(agenciesApi + "/" + id + "/status");
    }
}