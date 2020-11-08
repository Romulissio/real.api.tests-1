package helpers;

import entity.agencies.Agency;
import entity.agencies.DataAgency;
import entity.agencies.LinksMetaAgencies;
import io.qameta.allure.Step;
import services.UtilsData;
import services.UtilsJson;
import java.util.*;

public class AgenciesHelper {

    public static Set<String> getAgenciesList(String responseJsonString) {
        DataAgency dataAgency = UtilsJson.getListAgencies(responseJsonString);
        List<LinksMetaAgencies> listLink = dataAgency.getMeta().getLinks();
        Set<String> notNullLink = new HashSet<>();
        for(LinksMetaAgencies o : listLink){
            if(o.getUrl() != null){
                notNullLink.add(o.getUrl());
            }
        }
        return notNullLink;
    }

    @Step("Создание объекта Agency из ответа сервера")
    public static Agency createAgencyResponse(String responseJsonString){
        return UtilsJson.getAgency(responseJsonString);
    }

    @Step("Создание объекта АН")
    public static Agency createAgency() {
        return Agency
                .builder()
                    .name(UUID.randomUUID().toString())
                    .inn(UtilsData.gerRandomBigNumber())
                .build();
    }

    @Step("Получение случайного существующего АН")
    public static Long getCorrectAgencyId(List<Agency> agencies) {
        int index =  UtilsData.getRandomAgencyListIndex(agencies.size() - 1);
        return agencies.get(index).getId();
    }
    @Step("Получение несуществующего АН")
    public static Long getIncorrectAgencyId(List<Agency> agencies) {
        agencies.sort(Agency::compareData);
        return agencies.get(agencies.size() - 1).getId() + 1;
    }

    @Step("Получение списка АН на конкретной странице")
    public static List<Agency> createFullListAgencies(List<DataAgency> dataAgencies) {
        List<Agency> list = new ArrayList<>();
        for(DataAgency o : dataAgencies){
            list.addAll(o.getData());
        }
        return list;
    }

    @Step("Формирование корректного адреса для запроса")
    public static String getCorrectAgencyAdr(List<Agency> agencyFullObject, String agenciesApi) {
        return agenciesApi + "/" + getCorrectAgencyId(agencyFullObject);
    }

    @Step("Создание объекта АН c пустым полем name")
    public static Agency createEmptyNameAgency() {
        return Agency
                .builder()
                .build();
    }

    @Step("Изменение статуса активности АН на полярный (true::false)")
    public static Agency updateAgencyStatus(Agency agencyResponse) {
        if(agencyResponse.isActive()){
            agencyResponse.setActive(false);
        }
        if(!agencyResponse.isActive()){
            agencyResponse.setActive(true);
        }
        return agencyResponse;

    }
}
