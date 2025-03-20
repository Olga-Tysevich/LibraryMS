package by.lms.libraryms.dto.req;

import by.lms.libraryms.conf.validation.PasswordMatches;
import by.lms.libraryms.dto.common.UserDTO;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static by.lms.libraryms.utils.Constants.*;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches(message = PASSWORDS_DO_NOT_MATCH_MESSAGE)
public class CreateUserDTO extends UserDTO {
    @Pattern(regexp = PASSWORD_REGEX, message = INVALID_PASSWORD_MESSAGE)
    private String password;
    private String passwordConfirmation;
}
