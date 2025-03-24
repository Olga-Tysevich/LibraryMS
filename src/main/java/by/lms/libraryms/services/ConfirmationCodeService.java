package by.lms.libraryms.services;

import by.lms.libraryms.domain.ConfirmationCode;

public interface ConfirmationCodeService {
    ConfirmationCode createConfirmationCode(String userId);
}
