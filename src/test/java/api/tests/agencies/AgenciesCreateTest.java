package api.tests.agencies;

import api.tests.BaseTest;
import config.Endpoints;
import entity.agencies.Agency;
import helpers.agencies.AgenciesHelper;
import helpers.AssertionsHelper;
import helpers.utils.DataGeneratorEntity;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.responses.RequestSpec;

import static helpers.agencies.AgencyDatabaseHelper.*;
import static io.restassured.RestAssured.given;

@Feature("Агенства")
@DisplayName("Проверка создания нового АН")
public class AgenciesCreateTest extends BaseTest {

    @Test
    @DisplayName("Создание нового АН")
    void createAgency() {
        Agency agencyNew = generateAgency.generateNewAgency();
        Agency agencyResponse = given().spec(requestSpecAuth).body(agencyNew)
                .when().post(Endpoints.API_AGENCIES)
                .then().spec(RequestSpec.checkingResponseFromServer200()).extract().as(Agency.class);
        Agency newAgencyFromDatabase = agencyByInnFromDatabase(agencyNew.getInn());
        AssertionsHelper.objectComparison(agencyResponse, newAgencyFromDatabase);
    }

    @Test
    @DisplayName("Создание АН не аутентифицируемым пользователем")
    void createAgencyUnauthenticatedUser() {
        Agency agency = generateAgency.generateNewAgency();
        given().spec(requestSpec).body(agency)
                .when().post(Endpoints.API_AGENCIES)
                .then().spec(RequestSpec.checkingResponseFromServer401());
    }

    @Test
    @DisplayName("Создание новго АН с полем INN, которое уже записано в DB")
    void createDuplicateAgencies() {
        Agency duplicateAgency = createDuplicateAgencyBuilder();
        given().spec(requestSpecAuth).body(duplicateAgency)
                .when().post(Endpoints.API_AGENCIES)
                .then().spec(RequestSpec.checkingResponseFromServer422());
    }
}
