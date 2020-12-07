package helpers.users;

import entity.users.Users;
import helpers.utils.DataGeneratorEntity;
import helpers.utils.DataGeneratorUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import services.hibernate.users.UserDBService;

import java.util.ArrayList;
import java.util.List;

public class UsersDatabaseHelper {

    private static final UserDBService service = new UserDBService();
    private static DataGeneratorUtils dataGeneratorUtils = new DataGeneratorUtils();

    @Step("Получение пользователя из DB по id")
    public static Users getUserByIdFromDatabase(Long id){
        Users user = service.findUser(id);
        Allure.addAttachment("Юзер из DB", user.toString());
        return user;
    }

    @Step("Получение случайного пользователя из DB")
    public static Users getRandUserFromDatabase(){
        List<Users> list = getUsesCollection();
        Users user = list.get(dataGeneratorUtils.getRandIndexCollection(list));
        Allure.addAttachment("Случайный юзер из DB", user.toString());
        return user;
    }

    @Step("Получение списка всех юзеров из DB")
    public static List<Users> getUsesCollection(){
        List<Users> list = service.findAll();
        Allure.addAttachment("Списох всех юзеров", list.toString());
        return list;
    }

    @Step("Получение списка всех юзеров из DB которые привязаны к конкретному агенству")
    public static List<Users> getUsesCollectionAgencyId(Long id){
        List<Users> list = service.findAllAgencyId(id);
        Allure.addAttachment("Списох всех юзеров", list.toString());
        return list;
    }

    @Step("Получение случайного юзера, с существующим номером телефона из DB")
    public static Users getExistPhoneNumberDatabase() {
        List<Users> list = getUsesCollection();
        for(int i = 0; i < list.size(); i++){
            Users user = list.get(dataGeneratorUtils.getRandIndexCollection(list));
            if(user.getPhone() != null){
                return user;
            }
        }
        return new Users();
    }

    @Step("Генерация коллекции новых юзеров в database размером в: {sizeCollections}")
    public static void generateCollectionsNewUser(int sizeCollections){
        List<Users> users = new ArrayList<>();
        for(int i = 0; i <= sizeCollections; i ++){
            users.add(DataGeneratorEntity.generateNewUser());
        }
        service.generateUsersCollections(users);
    }

    @Step("Удаление юзера из DB")
    public static void deleteUserFromDatabase(Users user){
        service.deleteUser(user);
        Allure.addAttachment("Пользователь удален из DB: ", user.toString());
    }

    @Step("Получение списка всех юзеров из базы данных и сортировка их с помощью компаратора")
    public static List<Users> usersSortFromDatabase(String sortedCompare){
        List<Users> listUsersFromDatabase = getUsesCollection();
        if(sortedCompare.equals("compareToFirstName")){
            listUsersFromDatabase.sort(Users::compareToFirstName);
        }
        if(sortedCompare.equals("compareToLastName")){
            listUsersFromDatabase.sort(Users::compareToLastName);
        }
        if(sortedCompare.equals("compareToFirstNameAndLastName")){
            listUsersFromDatabase.sort(Users::compareToFirstNameLastName);
        }
        return listUsersFromDatabase;
    }

    /** удаление всех юзеров из базы кроме админа */
    public static void deleteAllUsersDatabase(){
        List<Users> list = service.findAll();
        List<Users> saveList = new ArrayList<>();
        for(Users o : list){
            if(o.getId().equals(1L)){
                continue;
            }
            saveList.add(o);
        }
        service.deleteUsersCollections(saveList);
    }
}
