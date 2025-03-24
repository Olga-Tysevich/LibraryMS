package by.lms.libraryms.exceptions;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException(String email) {
        super("The passwords do not match for " + email);
    }
}
