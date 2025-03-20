package by.lms.libraryms.domain.auth;

import by.lms.libraryms.domain.AbstractDomainClass;
import by.lms.libraryms.utils.Constants;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "refresh_tokens")
public class RefreshToken extends AbstractDomainClass {
    @NotNull(message = Constants.EMPTY_TOKEN_MESSAGE)
    private String tokenValue;
    @NotNull(message = Constants.EMPTY_ID_MESSAGE)
    private ObjectId userId;

}
