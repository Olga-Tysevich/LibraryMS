package by.lms.libraryms.exceptions;

public class ConfirmationCodeExpired extends RuntimeException {
    public ConfirmationCodeExpired(String code, String userId) {
        super("Confirmation code expired. Code: " + code + ", User ID: " + userId);
    }
}
