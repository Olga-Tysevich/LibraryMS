package by.lms.libraryms.utils;

public interface Constants {
    int DEFAULT_PAGE_SIZE = 20;

    //Telegram
    String TELEGRAM_PATH_TEMPLATE = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

    //Log messages
    String EMPTY_ID_MESSAGE = "ID cannot be null or empty";
    String EMPTY_OBJECT_CLASS_MESSAGE = "Object class cannot be null or empty";
    String EMPTY_NAME_MESSAGE = "Name cannot be null or empty";
    String EMPTY_SURNAME_MESSAGE = "Surname cannot be null or empty";
    String OBJECT_LIST_IS_NULL_MESSAGE = "List of objects cannot be null";
    String DATE_IS_NULL_MESSAGE = "Date cannot be null";

    //Exceptions
    String OBJECTS_NOT_FOUND = "%s not found!Search params:%s";
}
