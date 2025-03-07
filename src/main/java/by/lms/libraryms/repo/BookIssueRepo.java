package by.lms.libraryms.repo;

import by.lms.libraryms.domain.BookIOrder;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookIssueRepo extends MongoRepository<BookIOrder, Integer> {
    List<BookIOrder> findByUserId(@NotNull ObjectId userId);
    List<BookIOrder> findByInventoryBookId(@NotNull ObjectId inventoryBookId);
    List<BookIOrder> findByUserIdAndReturnDate(@NotNull ObjectId userId, LocalDate returnDate);
    List<BookIOrder> findByLibrarianId(@NotNull ObjectId librarianId);
    List<BookIOrder> findByReturnDate(LocalDate returnDate);
    List<BookIOrder> findByPeriodOfDelay(int periodOfDelay);
    List<BookIOrder> findByPeriodOfDelayAndUserId(int periodOfDelay, @NotNull ObjectId userId);

}
