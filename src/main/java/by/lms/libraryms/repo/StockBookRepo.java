package by.lms.libraryms.repo;

import by.lms.libraryms.domain.StockBook;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface StockBookRepo extends MongoRepository<StockBook, String> {
    List<StockBook> findByBookId(@NotNull ObjectId bookId);

    List<StockBook> findByQuantity(int amount);

    @Query("{ 'inventoryBookIds': { $in: ?0 } }")
    void deleteByInventoryBookIdsIn(@NotEmpty Collection<ObjectId> inventoryBookIds);

    List<StockBook> findAllByBookIdIn(@NotEmpty Collection<ObjectId> inventoryBookIds);

    List<StockBook> findAllByIdIn(@NotEmpty Collection<String> id);
}
