package by.lms.libraryms.domain;

import by.lms.libraryms.domain.inventorynumber.Inventory;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Document(collection = "inventory_books")
@CompoundIndexes({
        @CompoundIndex(name = "unique_inventory_book_index", def = "{'bookId': 1, 'inventoryNumberId': 1}", unique = true)
})
public class InventoryBook extends AbstractDomainClass implements Inventory {
    @NotNull
    private ObjectId bookId;
    @NotNull
    @Indexed(unique = true)
    private ObjectId inventoryNumberId;
    private Set<ObjectId> bookOrderIds;
    private boolean isAvailable = true;
    private Instant dateOfReceipt;

    @Version
    private int version;
}
