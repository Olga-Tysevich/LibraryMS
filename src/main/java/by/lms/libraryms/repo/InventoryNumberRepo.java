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
    /**
     * Method for finding the last number with a given prefix.
     * Filter by prefix. Sort by size of the numbers list.
     * Take only one (first) result or not yet linked inventory number if it exists.
     *
     * @param prefix The prefix of inventory number.
     * @return next inventory number
     */
    InventoryNumber findLastNumber(@NonNull InventoryPrefixEnum prefix);

    Optional<InventoryNumber> find(@NonNull InventoryNumber example);

    Optional<InventoryNumber> findById(@NonNull ObjectId id);

    /**
     * It can be applied once for an unrelated inventory number or for writing off an inventory number.
     */
    @NonNull
    InventoryNumber save(@NonNull InventoryNumber inventoryNumber);

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
