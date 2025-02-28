package by.lms.libraryms.repo;

import by.lms.libraryms.domain.StockBook;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StockBookRepo extends MongoRepository<StockBook, String> {
    List<StockBook> findByBookId(@NotNull ObjectId bookId);
    List<StockBook> findByAmount(int amount);
}
