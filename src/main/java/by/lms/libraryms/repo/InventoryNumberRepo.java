package by.lms.libraryms.repo;

import by.lms.libraryms.domain.InventoryNumber;
import by.lms.libraryms.domain.InventoryPrefixEnum;
import by.lms.libraryms.repo.search.SearchRepo;
import by.lms.libraryms.services.searchobjects.InventoryNumberSearchReq;
import by.lms.libraryms.utils.Constants;
import org.springframework.lang.NonNull;


/**
 * Operations to change inventory numbers are prohibited.
 */
public interface InventoryNumberRepo extends SearchRepo<InventoryNumber, InventoryNumberSearchReq> {
    InventoryNumber createNewNumber(InventoryPrefixEnum prefix);

    InventoryNumber find(InventoryNumber example);

    /**
     * The save operation is supported only for inventory numbers being written off and can be applied once.
     */
    @NonNull
    <S extends InventoryNumber> S save(@NonNull S entity);

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
