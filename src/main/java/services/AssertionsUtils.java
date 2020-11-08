package services;

import entity.agencies.Agency;
import io.qameta.allure.Step;

public class AssertionsUtils {

    @Step("Сравнение ответа от сервера - 200")
    public static Boolean checkStatusCode200(Integer statusCode){
        return statusCode == 200;
    }

    @Step("Сравнение ответа от сервера - 204")
    public static Boolean checkStatusCode204(Integer statusCode){
        return statusCode == 204;
    }

    @Step("Сравнение ответа от сервера - 302")
    public static boolean checkStatusCode302(int statusCode) {
        return statusCode == 302;
    }

    @Step("Сравнение ответа от сервера - 404")
    public static Boolean checkStatusCode404(Integer statusCode){
        return statusCode == 404;
    }

    @Step("Сравнение ответа от сервера - 422")
    public static boolean checkStatusCode422(int statusCode) {
        return statusCode == 422;
    }

    @Step("Сравнение объектов Agency, которые были отправлены на сервер и которые получили от него")
    public static boolean checkAgenciesUpdate(Agency responseAgency, Agency updateAgency) {
        return responseAgency.getName().equals(updateAgency.getName())
                && responseAgency.getInn().equals(updateAgency.getInn());
    }

    @Step("Сравнение id двух объектов АН")
    public static boolean checkAgencyIdAndAgencyResponseId(Long id, Agency agencyResponse) {
        return id.equals(agencyResponse.getId());
    }

    @Step("Сравнение статуса двух объектов АН")
    public static boolean checkUpdateStatusAgency(Agency agencyResponse, Agency agencyResponseStatus) {
        return agencyResponse.isActive() != agencyResponseStatus.isActive();
    }
}
