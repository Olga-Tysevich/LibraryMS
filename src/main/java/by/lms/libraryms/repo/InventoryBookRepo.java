package by.lms.libraryms.repo;

import by.lms.libraryms.domain.InventoryBook;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryBookRepo extends MongoRepository<InventoryBook, String> {
    List<InventoryBook> findByBookId(@NotNull ObjectId bookId);
    Optional<InventoryBook> findInventoryBookByBookId(@NotNull ObjectId bookId);
    Optional<InventoryBook> findInventoryBookByInventoryNumberId(@NotNull ObjectId inventoryNumberId);
    List<InventoryBook> findByAvailable(boolean available);
}
