package by.lms.libraryms.dto.req;

import by.lms.libraryms.conf.validation.PasswordMatches;
import by.lms.libraryms.conf.validation.ValidPassword;
import by.lms.libraryms.utils.Constants;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import static by.lms.libraryms.utils.Constants.PASSWORDS_DO_NOT_MATCH_MESSAGE;

@EqualsAndHashCode
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches(message = PASSWORDS_DO_NOT_MATCH_MESSAGE)
public class ChangePasswordDTO {
    @NotBlank(message = Constants.EMPTY_ID_MESSAGE)
    private String userId;
    @ValidPassword
    private char[] password;
    private char[] passwordConfirmation;
    @ValidPassword
    private char[] oldPassword;

    @Override
    public String toString() {
        return "ChangePasswordDTO{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
