package by.lms.libraryms.repo;

import by.lms.libraryms.domain.BookLending;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Repository
public interface BookLendingRepo extends MongoRepository<BookLending, String> {
    List<BookLending> findByReaderId(@NotNull ObjectId readerId);
    List<BookLending> findByInventoryBookId(@NotNull ObjectId inventoryBookId);
    List<BookLending> findByReaderIdAndActualReturnDate(@NotNull ObjectId readerId, LocalDate actualReturnDate);
    List<BookLending> findByReaderIdAndRequiredReturnDate(@NotNull ObjectId readerId, LocalDate requiredReturnDate);
    List<BookLending> findByLibrarianId(@NotNull ObjectId librarianId);
    List<BookLending> findByRequiredReturnDate(LocalDate requiredReturnDate);
    List<BookLending> findByActualReturnDate(LocalDate actualReturnDate);
    List<BookLending> findByPeriodOfDelay(Period periodOfDelay);
    List<BookLending> findByPeriodOfDelayAndReaderId(Period periodOfDelay, @NotNull ObjectId readerId);
    Page<BookLending> findByRequiredReturnDateIsNotNullAndActualReturnDateIsNull(Pageable pageable);
}
