package by.lms.libraryms.repo;

import by.lms.libraryms.domain.BookLending;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookLendingRepo extends MongoRepository<BookLending, String> {
    List<BookLending> findByUserId(@NotNull ObjectId userId);
    List<BookLending> findByInventoryBookId(@NotNull ObjectId inventoryBookId);
    List<BookLending> findByUserIdAndReturnDate(@NotNull ObjectId userId, LocalDate returnDate);
    List<BookLending> findByLibrarianId(@NotNull ObjectId librarianId);
    List<BookLending> findByReturnDate(LocalDate returnDate);
    List<BookLending> findByPeriodOfDelay(int periodOfDelay);
    List<BookLending> findByPeriodOfDelayAndUserId(int periodOfDelay, @NotNull ObjectId userId);

}
