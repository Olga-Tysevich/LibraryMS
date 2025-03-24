package by.lms.libraryms.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Document(collection = "confirmation_codes")
public class ConfirmationCode extends AbstractDomainClass{
    private String code;
    private ObjectId userId;
    private Instant expiresAt;
}
