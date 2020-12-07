package api.tests.agencies;

import api.tests.BaseTest;
import entity.agencies.Agency;
import entity.users.Users;
import helpers.agencies.AgenciesHelper;
import helpers.AssertionsHelper;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import config.Endpoints;
import services.responses.RequestSpec;

import static helpers.agencies.AgenciesHelper.*;
import static helpers.agencies.AgencyDatabaseHelper.*;
import static io.restassured.RestAssured.*;

@Feature("Агенства")
@DisplayName("Проверка изменения данных полей NAME, INN, STATUS в существующем АН")
public class AgencyUpdateTest extends BaseTest {

    @Test
    @DisplayName("Изменение статуса несуществующего АН")
    void updateIncorrectIdAgencyStatus() {
        Long incorrectId = getIncorrectAgencyId();
        Agency agency = generateAgency.generateNewAgency();
        AgenciesHelper.updateAgencyStatus(getRandAgency());
        given().spec(requestSpecAuth).body(agency)
                .when().put(Endpoints.API_AGENCIES_ID_STATUS, incorrectId)
                .then().spec(RequestSpec.checkingResponseFromServer404());
    }

    @Test
    @DisplayName("Изменение статуса существующего АН с некорректными данными")
    void updateIncorrectDataAgencyStatus() {
        Agency agency = getRandAgency();
        given().spec(requestSpecAuth).body(agency.getInn())
                .when().put(Endpoints.API_AGENCIES_ID_STATUS, agency.getId())
                .then().spec(RequestSpec.checkingResponseFromServer422());
    }

    @Test
    @DisplayName("Изменение статуса не аутентифицируемым пользователем")
    void updateStatusUnauthenticatedUser() {
        Agency agency = AgenciesHelper.updateAgencyStatus(getRandAgency());
        given().spec(requestSpec).body(agency)
                .when().put(Endpoints.API_AGENCIES_ID_STATUS, agency.getId())
                .then().spec(RequestSpec.checkingResponseFromServer401());
    }

    @Test
    @DisplayName("Изменение статуса АН")
    void updateAgencyStatus() {
        Agency agencyUpdatedStatus = AgenciesHelper.updateAgencyStatus(getRandAgency());
        given().spec(requestSpecAuth).body(agencyUpdatedStatus)
                .when().put(Endpoints.API_AGENCIES_ID_STATUS, agencyUpdatedStatus.getId())
                .then().spec(RequestSpec.checkingResponseFromServer200());
        AssertionsHelper.objectComparison(agencyUpdatedStatus, agencyByIdFromDatabase(agencyUpdatedStatus.getId()));
    }


    @Test
    @DisplayName("Корректное изменение данных существующего АН")
    void updateCorrectAgency() {
        Agency agencyUpdated = AgenciesHelper.updateAgency(getRandAgency());
        given().spec(requestSpecAuth).body(agencyUpdated)
                .when().put(Endpoints.API_AGENCIES_ID, agencyUpdated.getId())
                .then().spec(RequestSpec.checkingResponseFromServer200());
        AssertionsHelper.objectComparison(agencyUpdated, agencyByIdFromDatabase(agencyUpdated.getId()));
    }

    @Test
    @DisplayName("Корректное изменение данных существующего АН не аутентифицируемым пользователем")
    void updateCorrectAgencyUnauthenticatedUser() {
        Agency agency = getRandAgency();
        Agency agencyChecked = agencyByIdFromDatabase(agency.getId());
        Agency agencyUpdated = AgenciesHelper.updateAgency(agency);
        given().spec(requestSpec).body(agencyUpdated)
                .when().put(Endpoints.API_AGENCIES_ID, agencyUpdated.getId())
                .then().spec(RequestSpec.checkingResponseFromServer401());
        AssertionsHelper.objectComparison(agencyChecked, agencyByIdFromDatabase(agency.getId()));
    }

    @Test
    @DisplayName("Изменение данных АН некорректными данными")
    void updateIncorrectDataAgency() {
        Agency agency = getRandAgency();
        given().spec(requestSpecAuth).body(new Users())
                .when().put(Endpoints.API_AGENCIES_ID_STATUS, agency.getId())
                .then().spec(RequestSpec.checkingResponseFromServer422());
    }

    @Test
    @DisplayName("Изменение данных несуществующего АН")
    void updateIncorrectIdAgency() {
        Long incorrectId = getIncorrectAgencyId();
        Agency agency = generateAgency.generateNewAgency();
        given().spec(requestSpecAuth).body(agency)
                .when().put(Endpoints.API_AGENCIES_ID_STATUS, incorrectId)
                .then().spec(RequestSpec.checkingResponseFromServer404());
    }
}
