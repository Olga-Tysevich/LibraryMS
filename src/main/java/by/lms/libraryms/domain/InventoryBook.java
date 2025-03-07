package by.lms.libraryms.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Document(collection = "inventory_books")
public class InventoryBook extends AbstractDomainClass {
    @NotNull
    private ObjectId bookId;
    @NotNull
    @Indexed(unique = true)
    private ObjectId inventoryNumberId;
    private Set<BookIOrder> bookIOrders;
    private boolean available = true;
}
