package by.lms.libraryms.repo;

import by.lms.libraryms.domain.auth.RefreshToken;
import by.lms.libraryms.utils.Constants;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepo extends MongoRepository<RefreshToken, String> {

    RefreshToken findByTokenValue(@NotNull(message = Constants.EMPTY_TOKEN_MESSAGE) String tokenValue);

    RefreshToken findByUserId(@NotNull(message = Constants.EMPTY_ID_MESSAGE) ObjectId userId);

    void deleteByUserId(@NotNull(message = Constants.EMPTY_ID_MESSAGE) ObjectId userId);

}
