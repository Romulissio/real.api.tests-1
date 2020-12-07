package config;

public final class Endpoints {

    /** host*/
    public static final String BASE_URI = "http://127.0.0.1:81";

    /** Agencies*/
    public static final String API_AGENCIES = "/api/agencies";
    public static final String API_AGENCIES_ID = "/api/agencies/{agency_id}";
    public static final String API_AGENCIES_ID_STATUS = "/api/agencies/{agency_id}/status";

    /** Login*/
    public static final String API_CSRF_COOKIE = "/api/csrf-cookie";
    public static final String API_LOGIN_EMAIL_TOKEN = "/api/login/email/token";
    public static final String API_LOGIN_EMAIL = "/api/login/email";

    /** sms-login-register*/
    public static final String API_LOGIN_SMS = "/api/login/sms/";
    public static final String API_LOGIN_SMS_CHECK = "/api/login/sms/check";

    /** Users*/
    public static final String API_ME = "/api/me";
    public static final String API_USERS = "/api/users";
    public static final String API_USERS_ID = "/api/users/{user_id}";
    public static final String API_USERS_SET_ACTIVE = "/api/users/set_active";

    /** Blog */
    public static final String API_BLOG = "/api/blog";

    /** Blog tag*/
    public static final String API_TAGS = "/api/tags";
    public static final String API_TAGS_ID = "/api/tags/{tag_id}";

    /** Users*/
    public static final String API_IMAGES = "/api/images";

    private Endpoints() {
    }
}
