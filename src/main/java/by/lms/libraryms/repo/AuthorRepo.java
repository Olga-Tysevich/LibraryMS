package by.lms.libraryms.repo;

import by.lms.libraryms.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AuthorRepo extends MongoRepository<Author, String> {
    List<Author> findByNameAndSurname(String name, String surname);
}
