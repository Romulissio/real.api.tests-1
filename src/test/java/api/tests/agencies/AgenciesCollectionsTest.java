package api.tests.agencies;

import api.tests.BaseTest;
import config.Comparators;
import config.Endpoints;
import entity.agencies.Agency;
import helpers.agencies.AgenciesHelper;
import helpers.AssertionsHelper;
import helpers.agencies.AgencyDatabaseHelper;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import services.responses.RequestSpec;
import java.util.List;

import static helpers.agencies.AgenciesHelper.*;
import static io.restassured.RestAssured.given;

@Feature("Агенства")
@DisplayName("Проверка работы с коллекцией всех АН")
public class AgenciesCollectionsTest extends BaseTest {

    @Test
    @DisplayName("Сравнение всех АН полученных через API с коллекцией из DB")
    void checkAgenciesListFromDatabaseAndResponse(){
        List<Agency> listAgencyFromResponse = getAgenciesCollections(requestSpecAuth);
        List<Agency> listAgencyFromDatabase = AgencyDatabaseHelper.getAgenciesCollection();
        AssertionsHelper.comparisonCollections(listAgencyFromDatabase, listAgencyFromResponse);
    }

    @Test
    @DisplayName("Проверка сортировки всех агенств по полю: name")
    void checkSortedNameAgencyCollection(){
        List<Agency> listAgencyFromDatabase = AgencyDatabaseHelper.agenciesSortFromDatabase(Comparators.AGENCY_SORT_NAME);
        List<Agency> listAgencyFromResponse = getSortedCollectionAgencyFromApi(requestSpecAuth, "name");
        AssertionsHelper.compareAgencyFields(listAgencyFromDatabase, listAgencyFromResponse, "name");
    }

    @Test
    @DisplayName("Проверка сортировки всех агенств по полю: inn")
    void checkSortedInnAgencyCollection(){
        List<Agency> listAgencyFromDatabase = AgencyDatabaseHelper.agenciesSortFromDatabase(Comparators.AGENCY_SORT_INN);
        List<Agency> listAgencyFromResponse = getSortedCollectionAgencyFromApi(requestSpecAuth, "inn");
        AssertionsHelper.compareAgencyFields(listAgencyFromDatabase, listAgencyFromResponse, "inn");
    }

    @Test
    @DisplayName("Проверка сортировки всех агенств по полю: active")
    void checkSortedActiveAgencyCollection(){
        List<Agency> listAgencyFromDatabase = AgencyDatabaseHelper.agenciesSortFromDatabase(Comparators.AGENCY_SORT_ACTIVE);
        List<Agency> listAgencyFromResponse = getSortedCollectionAgencyFromApi(requestSpecAuth, "active");
        AssertionsHelper.compareAgencyFields(listAgencyFromDatabase, listAgencyFromResponse, "active");

    }

    @Test
    @DisplayName("Проверка сортировки всех агенств по полю: created_at")
    void checkSortedActiveCreatedAtCollection(){
        List<Agency> listAgencyFromDatabase = AgencyDatabaseHelper.agenciesSortFromDatabase(Comparators.AGENCY_SORT_CREATED_AT);
        List<Agency> listAgencyFromResponse = getSortedCollectionAgencyFromApi(requestSpecAuth, "created_at");
        AssertionsHelper.compareAgencyFields(listAgencyFromDatabase, listAgencyFromResponse, "created_at");
    }

    @Test
    @DisplayName("Получение списка АН не аутентифицируемым пользователем")
    void getAgenciesListUnauthenticatedUser(){
        List<String> links = getAgencyList(requestSpecAuth);
        given().spec(requestSpec).when().get(Endpoints.API_AGENCIES).then().spec(RequestSpec.checkingResponseFromServer401());
        for(String str : links){
            given().spec(requestSpec).when().get(str).then().spec(RequestSpec.checkingResponseFromServer401());
        }
    }

    @Test
    @DisplayName("Получение АН по существующему ID в DB")
    void getAgenciesByCorrectId() {
        Agency randAgency = AgencyDatabaseHelper.getRandAgency();
        Agency responseAgency = given().spec(requestSpecAuth)
                .when().get(Endpoints.API_AGENCIES_ID, randAgency.getId())
                .then().spec(RequestSpec.checkingResponseFromServer200()).extract().as(Agency.class);
        AssertionsHelper.objectComparison(randAgency, responseAgency);
    }

    @Test
    @DisplayName("Получение АН по несуществующему ID в DB")
    void getAgenciesByIncorrectId() {
        Long incorrectId = AgencyDatabaseHelper.getIncorrectAgencyId();
        given().spec(requestSpecAuth)
                .when().get(Endpoints.API_AGENCIES_ID, incorrectId)
                .then().spec(RequestSpec.checkingResponseFromServer404());
    }

    @Test
    @DisplayName("Получение АН неаутентифицируемым пользователем")
    void getAgenciesByAuthId() {
        Agency randAgency = AgencyDatabaseHelper.getRandAgency();
        given().spec(requestSpecNotToken)
                .when().get(Endpoints.API_AGENCIES_ID, randAgency.getId())
                .then().spec(RequestSpec.checkingResponseFromServer401());
    }
}
