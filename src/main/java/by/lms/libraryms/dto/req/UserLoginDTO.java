package by.lms.libraryms.dto.req;

import by.lms.libraryms.utils.Constants;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data transfer object representing user login information.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginDTO {

    @NotBlank(message = Constants.INVALID_EMAIL_MESSAGE)
    private String email;

    @NotBlank(message = Constants.INVALID_PASSWORD_MESSAGE)
    private String password;

}