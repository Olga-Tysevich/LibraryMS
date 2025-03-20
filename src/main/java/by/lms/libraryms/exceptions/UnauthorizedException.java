package by.lms.libraryms.exceptions;


import by.lms.libraryms.utils.Constants;


/**
 * This exception is thrown when there are authorization errors or insufficient access rights for the user.
 */
public class UnauthorizedException extends RuntimeException {

    /**
     * Constructs a new UnauthorizedException with a default error message.
     */
    public UnauthorizedException() {
        super(Constants.USER_IS_NOT_AUTHORIZED_MESSAGE);
    }

    /**
     * Constructs a new UnauthorizedException with a custom error message.
     *
     * @param message The custom error message to display.
     */
    public UnauthorizedException(String message) {
        super(message);
    }

    /**
     * Constructs a new UnauthorizedException with a custom error message and a cause.
     *
     * @param message The custom error message to display.
     * @param cause   The cause of the exception.
     */
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }


    /**
     * Constructs a new UnauthorizedException with a cause.
     *
     * @param cause The cause of the exception.
     */
    public UnauthorizedException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new UnauthorizedException with a custom error message, cause, enable suppression and writable stack trace.
     *
     * @param message            The custom error message to display.
     * @param cause              The cause of the exception.
     * @param enableSuppression  Whether suppression is enabled or disabled.
     * @param writableStackTrace Whether the stack trace should be writable or not.
     */
    public UnauthorizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}