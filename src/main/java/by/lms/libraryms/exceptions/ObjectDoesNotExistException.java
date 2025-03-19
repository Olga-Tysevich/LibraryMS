package by.lms.libraryms.exceptions;

public class ObjectDoesNotExistException extends RuntimeException {
    public ObjectDoesNotExistException(String message) {
        super(message);
    }

    public ObjectDoesNotExistException() {
    }
}
