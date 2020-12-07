package helpers.agencies;

import config.Endpoints;
import entity.agencies.Agency;
import entity.agencies.DataAgency;
import entity.utils.LinksMetaData;
import helpers.utils.DataGeneratorUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import services.hibernate.agencies.AgencyDBService;
import services.responses.RequestSpec;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static helpers.agencies.AgencyDatabaseHelper.getRandAgency;
import static io.restassured.RestAssured.given;


public class AgenciesHelper {

    private static final AgencyDBService agencyDBService = new AgencyDBService();
    private static DataGeneratorUtils dataGeneratorUtils = new DataGeneratorUtils();

    @Step("Проверка статуса у двух объектов АН")
    public static boolean checkStatuses(Agency agencyRequest, Agency agencyFromDB) {
        System.out.println(agencyRequest.toString());
        System.out.println(agencyFromDB.toString());
        return agencyRequest.isActive() == agencyFromDB.isActive();
    }

    /** получение уникальных ссылок на пэйджи со всеми агенствами */
    public static List<String> getLinksAgencies(List<LinksMetaData> listLink){
        Set <String> set = new HashSet<>();
        for(LinksMetaData o : listLink){
            if(o.getUrl() != null){
                set.add(o.getUrl());
            }
        }
        List<String> sortedList = new ArrayList<>(set);
        Collections.sort(sortedList);
        return sortedList;
    }

    @Step("Изменение данных у существующего агенства")
    public static Agency updateAgency(Agency agency) {
        agency.setName((UUID.randomUUID().toString()));
        agency.setInn(gerRandomBigNumber());
        return agency;
    }

    @Step("Изменение статуса активности АН на полярный true::false")
    public static Agency updateAgencyStatus(Agency agency) {
        if(agency.isActive()){
            agency.setActive(false);
        }
        if(!agency.isActive()){
            agency.setActive(true);
        }
        return agency;
    }

    /** Декодирование токена */
    public static String decodeCookieToken(Map<String, String> cookie) throws UnsupportedEncodingException {
        return URLDecoder.decode((cookie.get("XSRF-TOKEN")), "UTF-8" );
    }

    public static String decodeStr(String rawString) {
        byte[] bytes = rawString.getBytes(StandardCharsets.UTF_8);
        return new String(bytes, StandardCharsets.UTF_8);

    }

    /** Создание инн для агенства */
    public static Long gerRandomBigNumber(){
        return (long) (10000000 + Math.random()*10000);
    }

    /** Получение ссылок на страницы АН */
    public static List<String> getAgencyList(RequestSpecification requestSpecAuth) {
        DataAgency responseDataAgency = given().spec(requestSpecAuth).when().get(Endpoints.API_AGENCIES).as(DataAgency.class);
        return getLinksAgencies(responseDataAgency.getMeta().getLinks());
    }

    @Step("Получение списка всех агенств")
    public static List<Agency> getAgenciesCollections(RequestSpecification requestSpecAuth) {
        List<Agency> agency = new ArrayList<>();
        for(String str : getAgencyList(requestSpecAuth)){
            DataAgency dataAgency = requestPageAgency(requestSpecAuth, str);
            agency.addAll(dataAgency.getData());
        }
        return agency;
    }

    /** Запрос на сервер и получение агенств с одной страницы */
    public static DataAgency requestPageAgency(RequestSpecification requestSpecAuth, String url){
        return given().spec(requestSpecAuth)
                .when().get(url)
                .then().spec(RequestSpec.checkingResponseFromServer200()).extract().body().as(DataAgency.class);
    }

    /** Запрос на сервер и получение агенств с одной страницы */
    public static Agency getRandOrNullAgency(){
        int i = (int) (Math.random() * 2);
        if(i > 0) {
            return getRandAgency();
        }
        return null;
    }



    /** Отдача коллекции всех отсортированныйх юзеров через API */
    public static List<Agency> getSortedCollectionAgencyFromApi(RequestSpecification requestSpecAuth, String sortField){
        List<String> pages = getAgencyPages(requestSpecAuth);
        List<String> sortPages = updateSortedLink(pages,sortField);

        return getAgencyFromPage(sortPages, requestSpecAuth);
    }

    /** Отдача пэйджей с юзерами */
    public static List<String> getAgencyPages(RequestSpecification requestSpecAuth){
        Response response = given().spec(requestSpecAuth).when().get(Endpoints.API_AGENCIES);
        DataAgency dataAgency = response.as(DataAgency.class);
        return getLinksAgencies(dataAgency.getMeta().getLinks());
    }

    /** Добавление параметра сортировки в адрес запрашиваемой страницы */
    private static List<String> updateSortedLink(List<String> pages, String fieldSort){
        List<String> sortedParam = new ArrayList<>();
        for(String str : pages){
            sortedParam.add(str + "&sort=" + fieldSort);
        }
        return sortedParam;
    }

    @Step("Получение всех АН через API")
    public static List<Agency> getAgencyFromPage(List<String> pages, RequestSpecification requestSpecAuth) {
        List<Agency> agencies = new ArrayList<>();
        for(String str : pages){
            DataAgency dataAgency = requestPages(requestSpecAuth, str);
            agencies.addAll(dataAgency.getData());
        }
        return agencies;
    }

    @Step("Запрос на сервер по адресу: {url}")
    public static DataAgency requestPages(RequestSpecification requestSpecAuth, String url){
        DataAgency agency = given().spec(requestSpecAuth)
                .when().get(url)
                .then().spec(RequestSpec.checkingResponseFromServer200()).extract().body().as(DataAgency.class);
        Allure.addAttachment("Полученные данные от сервера", agency.toString());
        return agency;
    }


    public AgenciesHelper() {
    }
}
