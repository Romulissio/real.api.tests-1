package helpers;

import config.ErrorMessages;
import entity.Phone;
import entity.agencies.Agency;
import entity.users.DataUser;
import entity.users.Users;
import io.qameta.allure.Step;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AssertionsHelper {

    @Step("Сравнение двух объектов")
    public static void objectComparison(Object o1, Object o2){
        assertEquals(o1, o2, "Сравниваемые объекты не равны");
    }

    @Step("Сравнение двух коллекций")
    public static void  comparisonCollections(List<?> list1, List<?> list2){
        assertEquals(list1, list2, "Сравниваемые коллекции не равны");
    }

    @Step("Проверка, что все поля объекта не являются null")
    public static void notNullFieldObj(Phone o) {
        assertNotNull(o.getCode(), "Поле код - не должно быть пустым");
        assertNotNull(o.getPhone(), "Поле телефон - не должно быть пустым");
    }

    @Step("Проверка, новый юзер или нет")
    public static boolean checkRegisterUser(DataUser dataUser) {
        return dataUser.getRegistered();

    }

    @Step("Проверка есть ли message от сервера, о повтороной отправке кода")
    public static void compareMessageCode(String message) {
        assertTrue(message.contains(ErrorMessages.MESSAGE_SMS_CODE_TIMEOUT));
    }

    @Step("Проверка есть ли message от сервера, о неверном формате номера")
    public static void compareMessagePhoneNumber(String message) {
        assertTrue(message.contains(ErrorMessages.MESSAGE_PHONE_TYPE_ERROR));
    }

    @Step("Проверка есть ли message от сервера, о колличестве цифр кода")
    public static void compareMessageSizeCode(String message) {
        assertTrue(message.contains(ErrorMessages.MESSAGE_SMS_CODE_SIZE));
    }
    @Step("Проверка есть ли message от сервера, о обязательном заполнении кода")

    public static void compareMessageRequiredCode(String message) {
        assertTrue(message.contains(ErrorMessages.MESSAGE_SMS_CODE_REQUIRED));
    }
    @Step("Проверка сортировки по указанному полю: {compareField}")
    public static void compareUsersFields(List<Users> o1, List<Users> o2, String compareField) {
        if(compareField.equals("last_name")){
            for (int i = 0; i < o1.size(); i++) {
                System.out.println("Сранвиваем : " + o1.get(i).getLast_name() + " --- " + o2.get(i).getLast_name());
                assertEquals(o1.get(i).getLast_name(), o2.get(i).getLast_name());
            }
        }
        if(compareField.equals("first_name")){
            for (int i = 0; i < o1.size(); i++) {
                System.out.println("Сранвиваем : " + o1.get(i).getFirst_name() + " --- " + o2.get(i).getFirst_name());
                assertEquals(o1.get(i).getFirst_name(), o2.get(i).getFirst_name());
            }
        }
        if(compareField.equals("first_name,last_name")){
            for (int i = 0; i < o1.size(); i++) {
                System.out.println("Сранвиваемимя : " + o1.get(i).getFirst_name() + " --- " + o2.get(i).getFirst_name() + "|||||" + "Сранвиваем фамилию : " + o1.get(i).getLast_name() + " --- " + o2.get(i).getLast_name());
                assertEquals(o1.get(i).getFirst_name(), o2.get(i).getFirst_name());
            }
        }
    }

    @Step("Проверка сортировки по указанному полю: {compareField}")
    public static void compareAgencyFields(List<Agency> o1, List<Agency> o2, String compareField) {
        if(compareField.equals("name")){
            for (int i = 0; i < o1.size(); i++) {
                System.out.println("Сранвиваем : " + o1.get(i).getName() + " --- " + o2.get(i).getName());
                assertEquals(o1.get(i).getName(), o2.get(i).getName());
            }
        }
        if(compareField.equals("inn")){
            for (int i = 0; i < o1.size(); i++) {
                System.out.println("Сранвиваем : " + o1.get(i).getInn() + " --- " + o2.get(i).getInn());
                assertEquals(o1.get(i).getInn(), o2.get(i).getInn());
            }
        }
        if(compareField.equals("active")){
            for (int i = 0; i < o1.size(); i++) {
                System.out.println("Сранвиваем : " + o1.get(i).isActive() + " --- " + o2.get(i).isActive());
                assertEquals(o1.get(i).isActive(), o2.get(i).isActive());
            }
        }
        if(compareField.equals("created_at")){
            for (int i = 0; i < o1.size(); i++) {
                Timestamp t1 = o1.get(i).getCreated_at();
                Timestamp t2 = o2.get(i).getCreated_at();
                //костыль +3 часа ко времени
                Long l1 = t1.getTime() + 10800000;
                Long l2 = t2.getTime();
                System.out.println("Сранвиваем : " + l1 + " --- " + l2);
                assertEquals(l1, l2);
            }
        }
    }
}
