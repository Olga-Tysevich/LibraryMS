package by.lms.libraryms.domain.auth;

import by.lms.libraryms.conf.validation.ValidPhoneSet;
import by.lms.libraryms.domain.AbstractDomainClass;
import by.lms.libraryms.domain.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

import static by.lms.libraryms.utils.Constants.*;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Document(collection = "users")
public class User extends AbstractDomainClass {
    @Pattern(regexp = USERNAME_REGEX, message = INVALID_USERNAME_MESSAGE)
    @Indexed(unique = true)
    private String username;
    @Pattern(regexp = EMAIL_REGEX, message = INVALID_EMAIL_MESSAGE)
    @Indexed(unique = true)
    private String email;
    private String password;
    @NotBlank(message = EMPTY_FIRSTNAME_MESSAGE)
    private String firstName;
    @NotBlank(message = EMPTY_SURNAME_MESSAGE)
    private String lastName;
    @ValidPhoneSet(message = INVALID_PHONE_NUMBER_SET)
    private Set<String> phone;
    @NotEmpty(message = EMPTY_ADDER_ID_SET_MESSAGE)
    private Set<ObjectId> addressIds;
    @NotEmpty(message = EMPTY_ROLE_SET_MESSAGE)
    private Set<RoleEnum> roles;
    @NotBlank(message = EMPTY_LOCALE_MESSAGE)
    private String locale;
    @Indexed(unique = true)
    private long telegramChatId;
}
