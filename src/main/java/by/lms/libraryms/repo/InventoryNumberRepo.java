package by.lms.libraryms.repo;

import by.lms.libraryms.domain.InventoryNumber;
import by.lms.libraryms.domain.InventoryPrefixEnum;
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
    InventoryNumber createNewNumber(InventoryPrefixEnum prefix);

    Optional<InventoryNumber> find(InventoryNumber example);

    Optional<InventoryNumber> findById(ObjectId id);

    /**
     * It can be applied once for an unrelated inventory number or for writing off an inventory number.
     */
    @NonNull
    InventoryNumber save(@NonNull InventoryNumber entity);

    /**
     * Delete operation is not supported!
     */
    default void delete(@NonNull InventoryNumber entity) {
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
