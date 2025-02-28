package by.lms.libraryms.repo;

import by.lms.libraryms.domain.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GenreRepo extends MongoRepository<Genre, String> {
    Optional<Genre> findByName(String name);
}
