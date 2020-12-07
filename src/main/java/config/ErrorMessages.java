package config;

public final class ErrorMessages {

    /** message*/
    public static final String MESSAGE_SMS_CODE_TIMEOUT = "Проверочный код отправлен меньше минуты назад";
    public static final String MESSAGE_SMS_CODE_SIZE = "The code must be 4 digits";
    public static final String MESSAGE_SMS_CODE_REQUIRED = "The code field is required";
    public static final String MESSAGE_PHONE_TYPE_ERROR = "validation.phone";

    private ErrorMessages() {
    }
}
