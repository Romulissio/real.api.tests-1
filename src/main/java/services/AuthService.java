package services;

import config.ConfigProperties;
import entity.User;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class AuthService {
    private static String baseUrl;
    private static final String GET_AUTH_COOKIE_URL = "/api/csrf-cookie";
    private static final String POST_API_LOGIN_EMAIL_TOKEN = "/api/login/email/token";

    private static User user = new User();

    /**
     * Получение базового URL
     */
    public static String getBaseUrl() throws IOException {
        baseUrl = ConfigProperties.getValueProperties("base.path.host");
        user = User.builder()
                .email(ConfigProperties.getValueProperties("email"))
                .password(ConfigProperties.getValueProperties("password")).build();
        return baseUrl;
    }

    /**
     * Получение куков
     */
    public static Map<String, String> getCookiesAuth(){
        Response response = get(baseUrl + GET_AUTH_COOKIE_URL);
        Assertions.assertEquals(response.statusCode(), 204);
        return new HashMap<String, String>(response.getCookies());
    }

    /**
     * Получение токена для логина
     */
    public static String getApiTokenLoginEmail(Map<String, String> cookies) throws UnsupportedEncodingException {
        Response response = given()
                .contentType(ContentType.JSON)
                .header(new Header("X-XSRF-TOKEN", UtilsData.decodeCookieToken(cookies)))
                .body(user)
                .cookies(cookies)
                .post(baseUrl + POST_API_LOGIN_EMAIL_TOKEN);
        Assertions.assertEquals(response.statusCode(), 200);
        return UtilsJson.getTokenFromJson(response.getBody().asString());
    }
}
