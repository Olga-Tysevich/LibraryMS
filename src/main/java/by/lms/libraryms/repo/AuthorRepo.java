package by.lms.libraryms.repo;

import by.lms.libraryms.domain.Author;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepo extends MongoRepository<Author, String> {
    List<Author> findByNameAndSurname(String name, String surname);
}
