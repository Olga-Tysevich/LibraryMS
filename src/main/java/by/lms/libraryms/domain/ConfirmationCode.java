package by.lms.libraryms.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Builder
@Document(collection = "confirmation_codes")
@CompoundIndexes({
        @CompoundIndex(name = "unique_confirmation_code_index", def = "{'code': 1, 'userId' : 1}", unique = true)
})
public class ConfirmationCode extends AbstractDomainClass {
    private String code;
    private ObjectId userId;
    private Instant expiresAt;
}
