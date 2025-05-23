package by.lms.libraryms.domain;

import by.lms.libraryms.utils.Constants;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Document(collection = "stock")
public class StockBook extends AbstractDomainClass {
    @NotNull(message = Constants.EMPTY_ID_MESSAGE)
    private ObjectId bookId;
    @Positive(message = Constants.INVALID_QUANTITY_MESSAGE)
    private int quantity;
    @NotNull(message = Constants.DATE_IS_NULL_MESSAGE)
    private Instant dateOfReceipt;
    @NotEmpty
    private Set<ObjectId> inventoryBookIds;
}
