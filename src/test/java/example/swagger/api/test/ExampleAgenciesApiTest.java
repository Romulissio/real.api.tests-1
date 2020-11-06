package example.swagger.api.test;

import entity.agencies.DataAgency;
import helpers.AgenciesHelper;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import entity.agencies.Agency;
import org.junit.jupiter.api.*;
import io.restassured.response.Response;
import services.AuthService;
import services.UtilsData;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static io.restassured.RestAssured.*;

@DisplayName("Тестовый набор проверок функционала 'Agencies'")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExampleAgenciesApiTest {

    private static final String AGENCIES_API = "/api/agencies";
    private static String authToken = null;
    private static String baseUrl = null;

    private static Agency agency = new Agency();

    private static Map <String, String> cookies = new HashMap<>();
    private static Set<String> agencyLinksPage = new HashSet<>();
    private static List<Agency> agencyFullObject = new ArrayList<>();

    /**
     * Получение кукисов и токена
     */
    @BeforeAll
    @DisplayName("Получение куки и токена для логина")
    public static void getCookiesAndAuthToken() throws IOException {
        baseUrl = AuthService.getBaseUrl();
        cookies =  AuthService.getCookiesAuth();
        authToken = AuthService.getApiTokenLoginEmail(cookies);
    }

    /**
     * Получение базового списка агенств
     */
    @Test
    @Order(1)
    @DisplayName("Получение базового списка агенств")
    public void getAgenciesList() throws UnsupportedEncodingException {
        Response response = given().auth().oauth2(authToken)
                .header(new Header("accept", "application/json"))
                .header(new Header("X-XSRF-TOKEN", UtilsData.decodeCookieToken(cookies)))
                .cookies(cookies)
                .get(baseUrl + AGENCIES_API);
        DataAgency dataAgency = AgenciesHelper.getAgenciesList(response.getBody().asString());
        agencyLinksPage = AgenciesHelper.getFullAgenciesList(dataAgency);
        Assertions.assertEquals(response.statusCode(), 200);
    }

    /**
     * Получение полного списка агенств, т.к. при запросе отдается по 30 объектов
     */
    @Test
    @Order(2)
    @DisplayName("Получение полного списка агенств, т.к. при запросе отдается по 30 объектов")
    public void getFullAgenciesList() throws UnsupportedEncodingException {
        List<DataAgency> dataAgencies = new ArrayList<DataAgency>();
        for (String linkAgencyPage: agencyLinksPage) {
            Response response = given().auth().oauth2(authToken)
                    .header(new Header("accept", "application/json"))
                    .header(new Header("X-XSRF-TOKEN", UtilsData.decodeCookieToken(cookies)))
                    .cookies(cookies)
                    .get(linkAgencyPage);
            Assertions.assertEquals(response.statusCode(), 200);
            dataAgencies.add(AgenciesHelper.getAgenciesList(response.getBody().asString()));
        }
        agencyFullObject = AgenciesHelper.createFullListAgencies(dataAgencies);
    }

    /**
     * Получение агенства, которое НЕ существует по id
     */
    @Test
    @Order(3)
    @DisplayName("Получение агенства, которое НЕ существует по id")
    public void getAgenciesByNullId() throws UnsupportedEncodingException {
        Long agencyIncorrectId = AgenciesHelper.getIncorrectAgencyId(agencyFullObject);
        Response response = given().auth().oauth2(authToken)
                .contentType(ContentType.JSON)
                .header(new Header("X-XSRF-TOKEN", UtilsData.decodeCookieToken(cookies)))
                .cookies(cookies)
                .get(baseUrl + AGENCIES_API + "/" + agencyIncorrectId);
        Assertions.assertEquals(response.statusCode(), 401);
    }

    /**
     * Создание агенства
     */
    @Test
    @Order(4)
    @DisplayName("Создание нового агенства")
    public void createAgencies() throws UnsupportedEncodingException {
        agency = AgenciesHelper.createAgency();
        Response response = given().auth().oauth2(authToken)
                .contentType(ContentType.JSON)
                .header(new Header("X-XSRF-TOKEN", UtilsData.decodeCookieToken(cookies)))
                .body(agency)
                .cookies(cookies)
                .post(baseUrl + AGENCIES_API);
        Agency agencyResponse = AgenciesHelper.createAgencyResponse(response.getBody().asString());
        Assertions.assertEquals(response.statusCode(), 200);
        Assertions.assertTrue(AgenciesHelper.chekAgencyResponse(agencyResponse, agency));
    }

    /**
     * Создание агенства с полями, которое уже существуют
     */
    @Test
    @Order(5)
    @DisplayName("Создание агенства с полями, которое уже существуют")
    public void createDuplicateAgencies() throws UnsupportedEncodingException {
        Response response = given().auth().oauth2(authToken)
                .contentType(ContentType.JSON)
                .header(new Header("X-XSRF-TOKEN", UtilsData.decodeCookieToken(cookies)))
                .body(agency)
                .cookies(cookies)
                .post(baseUrl + AGENCIES_API);
        Assertions.assertEquals(response.statusCode(), 302);
    }

    /**
     * Получение агенства, которое существует по id
     */
    @Test
    @Order(6)
    @DisplayName("Получение агенства, которое существует по id")
    public void getAgenciesById() throws UnsupportedEncodingException {
        Long agencyId = AgenciesHelper.getCorrectAgencyId(agencyFullObject);
        Response response = given().auth().oauth2(authToken)
                .contentType(ContentType.JSON)
                .header(new Header("X-XSRF-TOKEN", UtilsData.decodeCookieToken(cookies)))
                .cookies(cookies)
                .get(baseUrl + AGENCIES_API + "/" + agencyId);
        Agency agencyResponse = AgenciesHelper.createAgencyResponse(response.getBody().asString());
        Assertions.assertEquals(response.statusCode(), 200);
        Assertions.assertTrue(AgenciesHelper.checkAgencyIdAndAgencyResponseId(agencyId, agencyResponse));
    }

    @Test
    @Order(7)
    @DisplayName("Апдейт существующего агенства")
    public void updateAgenciesById() throws UnsupportedEncodingException {
        Long agencyCorrectId = AgenciesHelper.getCorrectAgencyId(agencyFullObject);
        Agency updateAgency = AgenciesHelper.createAgency();
        Response response = given().auth().oauth2(authToken)
                .contentType(ContentType.JSON)
                .header(new Header("X-XSRF-TOKEN", UtilsData.decodeCookieToken(cookies)))
                .body(updateAgency)
                .cookies(cookies)
                .put(baseUrl + AGENCIES_API + "/" + agencyCorrectId);
        Agency agencyResponse = AgenciesHelper.createAgencyResponse(response.getBody().asString());
        Assertions.assertTrue(AgenciesHelper.checkAgenciesUpdate(agencyResponse, updateAgency));
        Assertions.assertEquals(response.statusCode(), 200);
    }
}
