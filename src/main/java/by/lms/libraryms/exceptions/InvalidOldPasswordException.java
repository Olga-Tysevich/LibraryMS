package by.lms.libraryms.exceptions;

public class InvalidOldPasswordException extends RuntimeException {
    public InvalidOldPasswordException(String email) {
        super("Invalid old password for email: " + email);
    }
}
