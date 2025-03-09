package by.lms.libraryms.repo;

import by.lms.libraryms.domain.BookOrder;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookIssueRepo extends MongoRepository<BookOrder, String> {
    List<BookOrder> findByUserId(@NotNull ObjectId userId);
    List<BookOrder> findByInventoryBookId(@NotNull ObjectId inventoryBookId);
    List<BookOrder> findByUserIdAndReturnDate(@NotNull ObjectId userId, LocalDate returnDate);
    List<BookOrder> findByLibrarianId(@NotNull ObjectId librarianId);
    List<BookOrder> findByReturnDate(LocalDate returnDate);
    List<BookOrder> findByPeriodOfDelay(int periodOfDelay);
    List<BookOrder> findByPeriodOfDelayAndUserId(int periodOfDelay, @NotNull ObjectId userId);

}
