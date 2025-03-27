package by.lms.libraryms.services;

import by.lms.libraryms.domain.ConfirmationCode;
import jakarta.validation.constraints.NotBlank;

public interface ConfirmationCodeService {

    ConfirmationCode createConfirmationCode(@NotBlank String userId);

    ConfirmationCode validateConfirmationCode(@NotBlank String confirmationCode);

}
