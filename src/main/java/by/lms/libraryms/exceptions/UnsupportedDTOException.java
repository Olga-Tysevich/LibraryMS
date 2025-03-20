package by.lms.libraryms.exceptions;


import static by.lms.libraryms.utils.Constants.UNSUPPORTED_DTO_MESSAGE;

/**
 * This exception is thrown when there is a pass to a method that is not supported by the method.
 */
public class UnsupportedDTOException extends RuntimeException {

    /**
     * Constructs a new UnsupportedDTOException with a default error message.
     */
    public UnsupportedDTOException() {
        super(UNSUPPORTED_DTO_MESSAGE);
    }

    /**
     * Constructs a new UnsupportedDTOException with a custom error message.
     *
     * @param message The custom error message to display.
     */
    public UnsupportedDTOException(String message) {
        super(message);
    }

    /**
     * Constructs a new UnsupportedDTOException with a custom error message and a cause.
     *
     * @param message The custom error message to display.
     * @param cause   The cause of the exception.
     */
    public UnsupportedDTOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new UnsupportedDTOException with a cause.
     *
     * @param cause The cause of the exception.
     */
    public UnsupportedDTOException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new UnsupportedDTOException with a custom error message, cause, enable suppression and writable stack trace.
     *
     * @param message            The custom error message to display.
     * @param cause              The cause of the exception.
     * @param enableSuppression  Whether suppression is enabled or disabled.
     * @param writableStackTrace Whether the stack trace should be writable or not.
     */
    public UnsupportedDTOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}