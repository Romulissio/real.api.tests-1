package example.swagger.api.test;

import entity.agencies.DataAgency;
import helpers.AgenciesHelper;
import io.qameta.allure.Epic;
import entity.agencies.Agency;
import org.junit.jupiter.api.*;
import io.restassured.response.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import services.AssertionsUtils;
import services.AuthService;
import services.UtilsJson;
import services.responses.ResponseAgency;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Демо набор проверок функционала 'Agencies'")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AgenciesDemoTest {

    private static String AGENCIES_API = null;
    private static String authToken = null;

    private static Agency agency = new Agency();

    private static Map <String, String> cookies = new HashMap<>();
    private static List<Agency> agencyFullObject = new ArrayList<>();

    List<DataAgency> dataAgencies = new ArrayList<>();

    private static Stream<String> getStream() throws UnsupportedEncodingException {
        Response response = ResponseAgency.getListAgencies(AGENCIES_API, cookies, authToken);
        Set<String> agencyPages = AgenciesHelper.getAgenciesList(response.getBody().asString());
        return agencyPages.stream();
    }

    @BeforeAll
    @DisplayName("Получение куки и токена для логина")
    public static void getCookiesAndAuthToken() throws IOException {
        AGENCIES_API = AuthService.getBaseUrl() + "/api/agencies";
        cookies =  AuthService.getCookiesAuth();
        authToken = AuthService.getApiTokenLoginEmail(cookies);
    }

    @Order(1)
    @Epic(value = "Агенства недвижимости")
    @ParameterizedTest
    @MethodSource("getStream")
    @DisplayName("Проверка страниц с АН stream {url}")
    public void getAgenciesList(String url) throws UnsupportedEncodingException {
        Response response = ResponseAgency.getListAgencies(url, cookies, authToken);
        assertTrue(AssertionsUtils.checkStatusCode200(response.statusCode()));
        dataAgencies.add(UtilsJson.getListAgencies(response.getBody().asString()));
        agencyFullObject.addAll(AgenciesHelper.createFullListAgencies(dataAgencies));
    }

    @Test
    @Order(2)
    @Epic(value = "Агенства недвижимости")
    @DisplayName("Получение АН, c корректным id")
    public void getAgenciesById() throws UnsupportedEncodingException {
        Long correctAgencyId = AgenciesHelper.getCorrectAgencyId(agencyFullObject);
        Response response = ResponseAgency.getAgencyById(AGENCIES_API, correctAgencyId, cookies, authToken);
        assertTrue(AssertionsUtils.checkStatusCode200(response.statusCode()));
        Agency agencyResponse = AgenciesHelper.createAgencyResponse(response.getBody().asString());
        assertTrue(AssertionsUtils.checkAgencyIdAndAgencyResponseId(correctAgencyId, agencyResponse));
    }

    @Test
    @Order(3)
    @Epic(value = "Агенства недвижимости")
    @DisplayName("Получение АН с некорректным id")
    public void getAgenciesByIncorrectId() throws UnsupportedEncodingException {
        Long incorrectAgencyId = AgenciesHelper.getIncorrectAgencyId(agencyFullObject);
        Response response = ResponseAgency.getAgencyById(AGENCIES_API, incorrectAgencyId, cookies, authToken);
        assertTrue(AssertionsUtils.checkStatusCode404(response.statusCode()));
    }

    @Test
    @Order(4)
    @DisplayName("Корректное изменение данных существующего агенства")
    @Epic(value = "Агенства недвижимости")
    public void updateCorrectAgencyById() throws UnsupportedEncodingException {
        Agency newDataAgency = AgenciesHelper.createAgency();
        String url = AgenciesHelper.getCorrectAgencyAdr(agencyFullObject, AGENCIES_API);
        Response response = ResponseAgency.putResponseUpdateAgency(newDataAgency, url, cookies, authToken);
        Agency responseAgency = AgenciesHelper.createAgencyResponse(response.getBody().asString());
        assertTrue(AssertionsUtils.checkAgenciesUpdate(responseAgency, newDataAgency));
        assertTrue(AssertionsUtils.checkStatusCode200(response.statusCode()));
    }

    @Test
    @Order(5)
    @DisplayName("Создание АН с пустыми значениями")
    @Epic(value = "Агенства недвижимости")
    public void updateIncorrectAgencyById() throws UnsupportedEncodingException {
        Agency newDataAgency = AgenciesHelper.createEmptyNameAgency();
        Response response = ResponseAgency.postCreateAgency(AGENCIES_API, cookies,authToken, newDataAgency);
        assertTrue(AssertionsUtils.checkStatusCode422(response.statusCode()));
    }

    @Test
    @Order(6)
    @DisplayName("Изменение статуса АН")
    @Epic(value = "Агенства недвижимости")
    public void updateAgencyStatus() throws UnsupportedEncodingException {
        Long correctAgencyId = AgenciesHelper.getCorrectAgencyId(agencyFullObject);
        Response response = ResponseAgency.getAgencyById(AGENCIES_API, correctAgencyId, cookies, authToken);
        assertTrue(AssertionsUtils.checkStatusCode200(response.statusCode()));
        Agency agencyResponse = AgenciesHelper.createAgencyResponse(response.getBody().asString());
        Response responseStatus = ResponseAgency.putUpdateStatusAgency(
                AGENCIES_API,
                cookies,
                authToken,
                AgenciesHelper.updateAgencyStatus(agencyResponse),
                agencyResponse.getId());
        assertTrue(AssertionsUtils.checkStatusCode200(responseStatus.statusCode()));
        Agency agencyResponseStatus = AgenciesHelper.createAgencyResponse(response.getBody().asString());
        assertTrue(AssertionsUtils.checkUpdateStatusAgency(agencyResponse, agencyResponseStatus));
    }

    @Test
    @Order(7)
    @DisplayName("Изменение статуса несуществующего АН")
    @Epic(value = "Агенства недвижимости")
    public void updateIncorrectAgencyStatus() throws UnsupportedEncodingException {
        Long incorrectAgencyId = AgenciesHelper.getIncorrectAgencyId(agencyFullObject);
        Agency newDataAgency = AgenciesHelper.createAgency();
        Response response = ResponseAgency.putUpdateStatusAgency(AGENCIES_API, cookies, authToken, newDataAgency, incorrectAgencyId);
        assertTrue(AssertionsUtils.checkStatusCode404(response.statusCode()));
    }

    @Test
    @Order(8)
    @Epic(value = "Агенства недвижимости")
    @DisplayName("Создание АН с полями, которое уже существуют")
    public void createDuplicateAgencies() throws UnsupportedEncodingException {
        Response response = ResponseAgency.postCreateAgency(AGENCIES_API, cookies, authToken, agency);
        assertTrue(AssertionsUtils.checkStatusCode302(response.statusCode()));
    }

    @Test
    @Order(9)
    @Epic(value = "Агенства недвижимости")
    @DisplayName("Создание нового агенства")
    public void createAgencies() throws UnsupportedEncodingException {
        agency = AgenciesHelper.createAgency();
        Response response = ResponseAgency.postCreateAgency(AGENCIES_API, cookies,authToken, agency);
        assertTrue(AssertionsUtils.checkStatusCode200(response.statusCode()));
        Agency agencyResponse = AgenciesHelper.createAgencyResponse(response.getBody().asString());
        assertTrue(AssertionsUtils.checkAgenciesUpdate(agencyResponse, agency));
    }
}
