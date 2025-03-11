package by.lms.libraryms.repo;

import by.lms.libraryms.domain.Inventory;
import by.lms.libraryms.domain.InventoryNumber;
import by.lms.libraryms.domain.InventoryPrefixEnum;
import by.lms.libraryms.exceptions.BindingInventoryNumberException;
import by.lms.libraryms.exceptions.UnbindInventoryNumberException;
import by.lms.libraryms.repo.search.SearchRepo;
import by.lms.libraryms.services.searchobjects.InventoryNumberSearchReq;
import by.lms.libraryms.utils.Constants;
import org.bson.types.ObjectId;
import org.springframework.lang.NonNull;

import java.util.Optional;


/**
 * Operations to change inventory numbers are prohibited.
 */
public interface InventoryNumberRepo extends SearchRepo<InventoryNumber, InventoryNumberSearchReq> {
    Inventory createNewNumber(@NonNull InventoryPrefixEnum prefix, @NonNull Inventory relatedObject) throws BindingInventoryNumberException;

    Optional<InventoryNumber> find(@NonNull InventoryNumber example);

    Optional<InventoryNumber> findById(@NonNull ObjectId id);

    /**
     * It can be applied once for an unrelated inventory number or for writing off an inventory number.
     */
    @NonNull
    InventoryNumber save(@NonNull InventoryNumber inventoryNumber);

    /**
     * You can only unbind on the day of binding.
     */
    void unbind(@NonNull Inventory relatedObject) throws UnbindInventoryNumberException;

    /**
     * Delete operation is not supported!
     */
    default void delete(@NonNull InventoryNumber inventoryNumber) {
        throw new UnsupportedOperationException(Constants.DELETE_OPERATION_NOT_SUPPORTED);
    }

    /**
     * Delete operation is not supported!
     */
    default void deleteAll() {
        throw new UnsupportedOperationException(Constants.DELETE_OPERATION_NOT_SUPPORTED);
    }

    /**
     * Delete operation is not supported!
     */
    default void deleteAll(@NonNull Iterable<? extends InventoryNumber> entities) {
        throw new UnsupportedOperationException(Constants.DELETE_OPERATION_NOT_SUPPORTED);
    }
}
