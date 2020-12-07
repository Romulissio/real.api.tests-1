package api.tests.users.collections;

import api.tests.BaseTest;
import config.Comparators;
import entity.users.Users;
import helpers.AssertionsHelper;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;

import static helpers.users.UsersDatabaseHelper.*;
import static helpers.users.UsersHelper.*;

@Feature("Пользователи")
@DisplayName("Набор проверок сортировки при получении от сервера")
public class UsersCollectionsSortedTest extends BaseTest {

    @Test
    @DisplayName("Проверка сортировки пользователей по полю: first_name")
    void sortedFirstName(){
        List<Users> listUsersFromDatabase = usersSortFromDatabase(Comparators.SORT_FIRST_NAME);
        List<Users> listFromUsers = getSortedCollectionUsersFromApi(requestSpecAuth,"first_name");
        AssertionsHelper.compareUsersFields(listUsersFromDatabase, listFromUsers, "first_name");
    }

    @Test
    @DisplayName("Проверка сортировки пользователей по полю: last_name")
    void sortedLastName(){
        List<Users> listUsersFromDatabase = usersSortFromDatabase(Comparators.SORT_LAST_NAME);
        List<Users> listFromUsers = getSortedCollectionUsersFromApi(requestSpecAuth,"last_name");
        AssertionsHelper.compareUsersFields(listUsersFromDatabase, listFromUsers, "last_name");
    }

    @Test
    @DisplayName("Проверка сортировки пользователей по полю: first_name,last_name")
    void sortedFirstNameAndLastName(){
        List<Users> listUsersFromDatabase = usersSortFromDatabase(Comparators.SORT_FIRST_NAME_AND_LAST_NAME);
        List<Users> listFromUsers = getSortedCollectionUsersFromApi(requestSpecAuth,"first_name,last_name");
        AssertionsHelper.compareUsersFields(listUsersFromDatabase, listFromUsers, "first_name,last_name");
    }
}
