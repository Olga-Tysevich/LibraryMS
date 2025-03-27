package by.lms.libraryms.repo;

import by.lms.libraryms.domain.ConfirmationCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface ConfirmationCodeRepo extends MongoRepository<ConfirmationCode, String> {
    void deleteAllByExpiresAtLessThan(Instant expiryDate);
}
