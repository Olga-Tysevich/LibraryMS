package by.lms.libraryms.repo;

import by.lms.libraryms.domain.BookIssue;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookIssueRepo extends MongoRepository<BookIssue, Integer> {
    List<BookIssue> findByUserId(@NotNull ObjectId userId);
    List<BookIssue> findByInventoryBookId(@NotNull ObjectId inventoryBookId);
    List<BookIssue> findByUserIdAndReturnDate(@NotNull ObjectId userId, LocalDate returnDate);
    List<BookIssue> findByLibrarianId(@NotNull ObjectId librarianId);
    List<BookIssue> findByReturnDate(LocalDate returnDate);
    List<BookIssue> findByPeriodOfDelay(int periodOfDelay);
    List<BookIssue> findByPeriodOfDelayAndUserId(int periodOfDelay, @NotNull ObjectId userId);

}
