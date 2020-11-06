package helpers;

import entity.agencies.Agency;
import entity.agencies.DataAgency;
import entity.agencies.LinksMetaAgencies;
import io.qameta.allure.Step;
import services.UtilsData;
import services.UtilsJson;
import java.util.*;

public class AgenciesHelper {

    public static DataAgency getAgenciesList(String responseJsonString) {
        return UtilsJson.getListAgencies(responseJsonString);
    }

    public static boolean chekAgencyResponse(Agency agencyResponse, Agency agency) {
        return agencyResponse != null
                && agency.getName().equals(agencyResponse.getName())
                && agency.getInn().equals(agencyResponse.getInn());
    }

    public static Agency createAgencyResponse(String responseJsonString){
        return UtilsJson.getAgency(responseJsonString);
    }

    @Step("Создание агенства програмно, с random инн и названием")
    public static Agency createAgency() {
        return Agency
                .builder()
                    .name(UUID.randomUUID().toString())
                    .inn(UtilsData.gerRandomBigNumber())
                .build();
    }

    public static Long getCorrectAgencyId(List<Agency> agencies) {
        int index =  UtilsData.getRandomAgencyListIndex(agencies.size() - 1);
        return agencies.get(index).getId();
    }
    @Step("Получение несуществующего id (граничное значение)")
    public static Long getIncorrectAgencyId(List<Agency> agencies) {
        agencies.sort(Agency::compareData);
        return agencies.get(agencies.size() - 1).getId() + 1;
    }

    @Step("Сравнение запрашиваемого id и id полученного агенства")
    public static boolean checkAgencyIdAndAgencyResponseId(Long id, Agency agencyResponse) {
        return id.equals(agencyResponse.getId());
    }

    @Step("Получение ссылок на все pages с агенствами")
    public static Set<String> getFullAgenciesList(DataAgency dataAgency) {
        List<LinksMetaAgencies> listLink = dataAgency.getMeta().getLinks();
        Set<String> notNullLink = new HashSet<String>();

        for(LinksMetaAgencies o : listLink){
            if(o.getUrl() != null){
                notNullLink.add(o.getUrl());
            }
        }
        return notNullLink;
    }

    @Step("Получение полного списка со всеми существующими агенствами")
    public static List<Agency> createFullListAgencies(List<DataAgency> dataAgencies) {
        List<Agency> list = new ArrayList<Agency>();
        for(DataAgency o : dataAgencies){
            list.addAll(o.getData());
        }
        return list;
    }

    public static boolean checkAgenciesUpdate(Agency agencyResponse, Agency updateAgency) {
        return agencyResponse.getName().equals(updateAgency.getName())
                && agencyResponse.getInn().equals(updateAgency.getInn());
    }
}
