package by.lms.libraryms.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "users")
public class User extends AbstractDomainClass {
    @Indexed(unique = true)
    private String username;
    @Indexed(unique = true)
    private String email;
    private String password;
    @NotBlank(message = "{validation.object.name.empty}")
    private String firstName;
    @NotBlank(message = "{validation.object.surname.empty}")
    private String lastName;
    private Set<String> phone;
    private Set<ObjectId> addressIds;
    private Set<RoleEnum> roles;
    @NotBlank(message = "{validation.user.locale.empty}")
    private String locale;
}
