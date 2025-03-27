package by.lms.libraryms.services.messages;

import by.lms.libraryms.domain.ConfirmationCode;

public interface ConfirmationMessageService {
    Message createEmailConfirmationMessage(String email, ConfirmationCode confirmationCode);
}
