package helpers.agencies;

import entity.agencies.Agency;
import entity.users.Users;
import helpers.users.UsersDatabaseHelper;
import helpers.utils.DataGeneratorEntity;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import services.hibernate.agencies.AgencyDBService;
import services.hibernate.users.UserDBService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AgencyDatabaseHelper {

    private static final AgencyDBService agencyDBService = new AgencyDBService();
    protected static final DataGeneratorEntity generate = new DataGeneratorEntity();

    @Step("Получение списка всех АН из базы данных и сортировка их с помощью компаратора")
    public static List<Agency> agenciesSortFromDatabase(String sortedCompare){
        List<Agency> listAgencyFromDatabase = getAgenciesCollection();
        if(sortedCompare.equals("compareToName")){
            listAgencyFromDatabase.sort(Agency::compareToName);
        }
        if(sortedCompare.equals("compareToInn")){
            listAgencyFromDatabase.sort(Agency::compareToInn);
        }
        if(sortedCompare.equals("compareToActive")){
            listAgencyFromDatabase.sort(Agency::compareToActive);
        }
        if(sortedCompare.equals("compareToCreatedAt")){
            listAgencyFromDatabase.sort(Agency::compareToCreatedAt);
        }
        return listAgencyFromDatabase;
    }

    @Step("Получение агенства из базы по полю ID")
    public static Agency agencyByIdFromDatabase(Long id){
        AgencyDBService agencyDBService = new AgencyDBService();
        return agencyDBService.findAgency(id);
    }

    @Step("Получение агенства из базы по полю INN")
    public static Agency agencyByInnFromDatabase(Long inn){
        AgencyDBService agencyDBService = new AgencyDBService();
        return agencyDBService.findAgencyInn(inn);
    }

    @Step("Создание нового объекта АН для отправки на сервер, с полями NAME и INN которые уже существуют в BD")
    public static Agency createDuplicateAgencyBuilder(){
        Agency agency = getRandAgency();
        Agency cloneFieldAgency = new Agency();
        if(agency.getName() != null){
            cloneFieldAgency.setName(agency.getName());
        }
        if(agency.getInn() != null){
            cloneFieldAgency.setInn(agency.getInn());
        }
        Allure.addAttachment("Дубилкат существующего АН", cloneFieldAgency.toString());
        return cloneFieldAgency;
    }

    @Step("Получение ID которого не существует в БД")
    public static Long getIncorrectAgencyId() {
        List<Agency> agencies = getAgenciesCollection();
        agencies.sort(Agency::compareData);
        Long id = agencies.get(agencies.size() - 1).getId() + 1;
        Allure.addAttachment("Несуществующий ID", id.toString());
        return id;
    }

    @Step("Получение списка всех АН из DB")
    public static List<Agency> getAgenciesCollection(){
        List<Agency> list = agencyDBService.findAll();
        Allure.addAttachment("Списох всех агенств", list.toString());

        return list;
    }

    @Step("Получение случайного существующего АН из DB")
    public static Agency getRandAgency(){
        List<Agency> list = agencyDBService.findAll();
        Random rnd = new Random();
        int i = rnd.nextInt(list.size());
        Agency agency = (Agency)(list.toArray() [i]);
        Allure.addAttachment("Случайное АН из DB", agency.toString());
        return agency;
    }

    /** Создание одного агенства */
    private static Agency createOneAgency(){
        Agency agency = generate.generateNewAgency();
        AgencyDBService service = new AgencyDBService();
        service.saveAgency(agency);
        return agency;
    }

    /** Удаление одного конекретного агенства и удаление его из таблицы юзеров */
    private static void deleteOneAgency(Long id){
        List<Users> usersList = UsersDatabaseHelper.getUsesCollectionAgencyId(id);
        for(Users u : usersList){
            u.setAgency_id(null);
        }
        UserDBService service = new UserDBService();
        service.updateUserCollections(usersList);
        AgencyDBService service1 = new AgencyDBService();
        service1.deleteAgencyById(id);
    }

    /** Удаление всех агенств */
    public static void deleteAllAgency(){
        List<Users> usersList = UsersDatabaseHelper.getUsesCollection();
        for(Users us : usersList){
            us.setAgency_id(null);
        }
        UserDBService service = new UserDBService();
        service.updateUserCollections(usersList);
        AgencyDBService service1 = new AgencyDBService();
        List<Agency> agencies = service1.findAll();
        service1.deleteAllAgency(agencies);
    }

    /** Добавление нескольких агеенств */
    public static void addAgencyCollections(int sizeCollections){
        List<Agency> agencyList = new ArrayList<>();
        for(int i = 0; i < sizeCollections; i ++){
            Agency agency = generate.generateNewAgency();
            agencyList.add(agency);
        }
        AgencyDBService service1 = new AgencyDBService();
        service1.addCollectionsAgency(agencyList);
    }

    public static void main(String[] args) {
        addAgencyCollections(2);
    }
}
