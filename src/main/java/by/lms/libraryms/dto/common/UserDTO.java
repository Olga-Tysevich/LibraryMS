package by.lms.libraryms.dto.common;

import by.lms.libraryms.conf.validation.ValidPhoneSet;
import by.lms.libraryms.dto.AbstractDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;

import java.util.Locale;
import java.util.Set;

import static by.lms.libraryms.utils.Constants.*;
import static by.lms.libraryms.utils.Constants.EMPTY_ROLE_SET_MESSAGE;

@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends AbstractDTO {
    @Pattern(regexp = USERNAME_REGEX, message = INVALID_USERNAME_MESSAGE)
    private String username;
    @Pattern(regexp = EMAIL_REGEX, message = INVALID_EMAIL_MESSAGE)
    private String email;
    @NotBlank(message = EMPTY_FIRSTNAME_MESSAGE)
    private String firstName;
    @NotBlank(message = EMPTY_SURNAME_MESSAGE)
    private String lastName;
    @ValidPhoneSet(message = INVALID_PHONE_NUMBER_SET_MESSAGE)
    private Set<String> phone;
    @NotEmpty(message = EMPTY_ADDER_ID_SET_MESSAGE)
    private Set<ObjectId> addressIds;
    @NotEmpty(message = EMPTY_ROLE_SET_MESSAGE)
    private Set<Integer> roleIds;
    @NotBlank(message = EMPTY_LOCALE_MESSAGE)
    private Locale locale;
    private boolean isConfirmed;
}
