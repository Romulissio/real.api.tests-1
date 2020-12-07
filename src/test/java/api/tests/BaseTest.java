package api.tests;

import config.ConfigProperties;
import entity.AuthToken;
import entity.blogs.Blog;
import entity.users.Users;
import helpers.agencies.AgenciesHelper;
import helpers.agencies.AgencyDatabaseHelper;
import helpers.blogs.BlogTagDatabaseHelper;
import helpers.users.UsersDatabaseHelper;
import helpers.utils.DataGeneratorEntity;
import helpers.utils.DataGeneratorUtils;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.AuthenticationSpecification;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import config.Endpoints;
import services.responses.RequestSpec;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.authentication.FormAuthConfig.springSecurity;

public class BaseTest {

    private static String authToken = null;
    private static Map<String, String> cookies = new HashMap<>();
    protected static RequestSpecification requestSpecAuth;
    protected static RequestSpecification requestSpec;
    protected static RequestSpecification requestSpecNotToken;
    protected static Users user = new Users();
    protected static final DataGeneratorEntity generateAgency = new DataGeneratorEntity();

    /** получение user super admin, куки, токен o2auth */
    public static void getCookieAndTokenAuth() throws IOException {
        user = Users.builder()
                    .email(ConfigProperties.getValueProperties("email"))
                    .password(ConfigProperties.getValueProperties("password"))
                .build();
        cookies = given().basePath(basePath).get(Endpoints.API_CSRF_COOKIE).getCookies();
        requestSpec = RequestSpec.requestWithoutAuthorizationToken(cookies);
        //requestSpecNotToken = RequestSpec.requestNotXcsrfToken(cookies);
        authToken = given().spec(requestSpec).body(user).post(Endpoints.API_LOGIN_EMAIL_TOKEN).as(AuthToken.class).getToken();


    }

//    protected static void getOnlyToken() throws UnsupportedEncodingException {
//        RestAssured.baseURI = Endpoints.BASE_URI;
//        cookies = given().get(Endpoints.API_CSRF_COOKIE).getCookies();
//        requestSpec = RequestSpec.requestWithoutAuthorizationToken(cookies);
//        requestSpecNotToken = RequestSpec.requestNotXcsrfToken(cookies);
//    }

    /** Созадание объектов при глобальном запуске тестов*/
    private static void setupDatabaseEntity(){
        UsersDatabaseHelper.deleteAllUsersDatabase();
        AgencyDatabaseHelper.deleteAllAgency();
        AgencyDatabaseHelper.addAgencyCollections(100);
        UsersDatabaseHelper.generateCollectionsNewUser(100);
    }

    @BeforeAll
    @DisplayName("Получение куки и токена для логина")
    public static void getCookiesAndAuthToken() throws IOException {
        //setupDatabaseEntity();
        RestAssured.baseURI = Endpoints.BASE_URI;
        getCookieAndTokenAuth();
        requestSpecAuth = RequestSpec.requestAuthorizationToken(authToken, cookies);
    }
}
