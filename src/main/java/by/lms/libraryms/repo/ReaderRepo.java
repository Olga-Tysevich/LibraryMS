package by.lms.libraryms.repo;

import by.lms.libraryms.domain.Reader;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ReaderRepo extends MongoRepository<Reader, String> {
    Optional<Reader> findByUserId(@NotNull ObjectId userId);
    List<Reader> findByInventoryBookIdsIn(Collection<Set<String>> inventoryBookIds);
    List<Reader> findByHasDebt(Boolean hasDebt);
}
