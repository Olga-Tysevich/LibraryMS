package by.lms.libraryms.utils;

public interface Constants {
    int DEFAULT_PAGE_SIZE = 20;
    String ID_CLAIM = "id";
    String ROLE_CLAIM = "role";
    String REFRESH_TOKEN_KEY = "refresh-token";
    String TOKEN_TYPE = "Bearer ";
    String TOKEN_HEADER = "Authorization";
    String FORBIDDEN_KEY = "Forbidden";
    String[] IGNORE_URLS = {"/swagger-ui/**", "/swagger-resources/*", "/v3/api-docs/**"};

    //Telegram
    String TELEGRAM_PATH_TEMPLATE = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

    //Regex
    String USERNAME_REGEX = "^(?![_.])([a-zA-Z0-9._]+)(?<![_.])$";
    String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    String PHONE_REGEX = "^(\\+\\d{1,3}[- ]?)?\\(?\\d{1,4}\\)?[- ]?\\d{1,4}[- ]?\\d{1,4}[- ]?\\d{1,4}$";
    String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{6,}$";

    //Log messages
    String FORBIDDEN_MESSAGE = "You do not have permission to access this resource";
    String EMPTY_TOKEN_MESSAGE = "Token cannot be null or empty";
    String EMPTY_ID_MESSAGE = "ID cannot be null or empty";
    String EMPTY_LOCALE_MESSAGE = "Locale cannot be null or empty";
    String EMPTY_OBJECT_CLASS_MESSAGE = "Object class cannot be null or empty";
    String EMPTY_NAME_MESSAGE = "Name cannot be null or empty";
    String EMPTY_TITLE_MESSAGE = "Title cannot be null or empty";
    String EMPTY_FIRSTNAME_MESSAGE = "Firstname cannot be null or empty";
    String EMPTY_SURNAME_MESSAGE = "Surname cannot be null or empty";
    String EMPTY_YEAR_MESSAGE = "Year cannot be null or empty";
    String EMPTY_AUTHOR_MESSAGE = "At least one author must be specified";
    String EMPTY_GENRE_MESSAGE = "At least one genre must be specified";
    String EMPTY_INVENTORY_NUMBER_MESSAGE = "At least one number must be specified, format XXXX";
    String EMPTY_ADDER_ID_SET_MESSAGE = "At least one address id must be specified";
    String EMPTY_ROLE_SET_MESSAGE = "At least one role must be specified";
    String OBJECT_LIST_IS_NULL_MESSAGE = "List of objects cannot be null";
    String DATE_IS_NULL_MESSAGE = "Date cannot be null";
    String SHA_256_PATTERN = "^[a-f0-9]{64}$";
    String DELETE_OPERATION_NOT_SUPPORTED = "Delete operation is not supported";
    String SAVE_OPERATION_NOT_SUPPORTED = "Save operation is not supported";
    String INVALID_QUANTITY_MESSAGE = "The quantity must be greater than zero!";
    String INVALID_USERNAME_MESSAGE = "Username must consist of letters (a–z), digits (0–9), dots (.), " +
            "and underscores (_), but cannot start or end with a dot or an underscore";
    String INVALID_EMAIL_MESSAGE = "Invalid email address format";
    String UNSUPPORTED_DTO_MESSAGE = "This method does not support this type of dto!";
    String PASSWORDS_DO_NOT_MATCH_MESSAGE = "Passwords do not match!";
    String INVALID_PASSWORD_MESSAGE = """
            Be at least 6 characters long
            Contain at least one digit (0-9)
            Contain at least one special character (!@#$%^&*)
            Contain at least one lowercase letter (a-z)
            Contain at least one uppercase letter (A-Z)""";
    String INVALID_PHONE_NUMBER_SET_MESSAGE = "One or more phone numbers are invalid.";
    String USER_IS_NOT_AUTHORIZED_MESSAGE = "User is not authorized!";
    String TOKEN_WAS_STOLEN_MESSAGE = "Token was stolen!";
    String INVALID_REFRESH_TOKEN = "Invalid refresh token!";

    //Exceptions
    String OBJECTS_NOT_FOUND = "%s not found!Search params:%s";
}
