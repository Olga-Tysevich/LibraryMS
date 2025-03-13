package by.lms.libraryms.utils;

public interface Constants {
    int DEFAULT_PAGE_SIZE = 20;

    //Telegram
    String TELEGRAM_PATH_TEMPLATE = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

    //Log messages
    String EMPTY_ID_MESSAGE = "ID cannot be null or empty";
    String EMPTY_OBJECT_CLASS_MESSAGE = "Object class cannot be null or empty";
    String EMPTY_NAME_MESSAGE = "Name cannot be null or empty";
    String EMPTY_TITLE_MESSAGE = "Title cannot be null or empty";
    String EMPTY_SURNAME_MESSAGE = "Surname cannot be null or empty";
    String EMPTY_YEAR_MESSAGE = "Year cannot be null or empty";
    String EMPTY_AUTHOR_MESSAGE = "At least one author must be specified";
    String EMPTY_GENRE_MESSAGE = "At least one genre must be specified";
    String EMPTY_INVENTORY_NUMBER_MESSAGE = "At least one number must be specified, format XXXX";
    String OBJECT_LIST_IS_NULL_MESSAGE = "List of objects cannot be null";
    String DATE_IS_NULL_MESSAGE = "Date cannot be null";
    String SHA_256_PATTERN = "^[a-f0-9]{64}$";
    String DELETE_OPERATION_NOT_SUPPORTED = "Delete operation is not supported";
    String SAVE_OPERATION_NOT_SUPPORTED = "Save operation is not supported";
    String INVALID_QUANTITY_MESSAGE = "The quantity must be greater than zero!";

    //Exceptions
    String OBJECTS_NOT_FOUND = "%s not found!Search params:%s";
}
