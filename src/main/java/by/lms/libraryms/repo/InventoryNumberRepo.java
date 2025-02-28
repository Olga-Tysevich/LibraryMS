package by.lms.libraryms.repo;

import by.lms.libraryms.domain.InventoryNumber;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface InventoryNumberRepo extends MongoRepository<InventoryNumber, String> {
    List<InventoryNumber> findByPrefix(@NotNull Character prefix);
    List<InventoryNumber> findByIsDisposedOf(boolean disposed);
    List<InventoryNumber> findByDisposedDate(LocalDate disposedDate);
}
