package services;

import config.ConfigProperties;
import entity.User;
import io.restassured.response.Response;
import services.responses.ResponseAuth;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Response response = ResponseAuth.getAuthTokenAndCookies(baseUrl + GET_AUTH_COOKIE_URL);
        assertTrue(AssertionsUtils.checkStatusCode204(response.statusCode()));
        return response.getCookies();
    }

    /**
     * Получение токена для логина
     */
    public static String getApiTokenLoginEmail(Map<String, String> cookies) throws UnsupportedEncodingException {
        Response response = ResponseAuth.getAuthToken(cookies, user, baseUrl + POST_API_LOGIN_EMAIL_TOKEN);
        assertTrue(AssertionsUtils.checkStatusCode200(response.statusCode()));
        return UtilsJson.getTokenFromJson(response.getBody().asString());
    }
}
