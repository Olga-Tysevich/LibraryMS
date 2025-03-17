package by.lms.libraryms.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@EqualsAndHashCode(callSuper=true)
@ToString(callSuper = true)
@Data
@Document(collection = "readers")
public class Reader extends AbstractDomainClass {
    @NotNull
    private ObjectId userId;
    private Set<String> inventoryBookIds;
    private boolean hasDebt = false;
}
