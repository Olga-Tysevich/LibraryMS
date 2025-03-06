package by.lms.libraryms.repo;

import by.lms.libraryms.domain.Book;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface BookRepo extends MongoRepository<Book, String> {
    List<Book> findByAuthorIds(@NotNull Set<ObjectId> authorIds);
    List<Book> findByGenreIds(@NotNull Set<ObjectId> genreId);
    List<Book> findByTitle(String title);
    List<Book> findByTitleAndGenreIds(@NotNull String title, @NotNull Set<ObjectId> genreId);
    List<Book> findByTitleAndYear(@NotNull String title, @NotNull Integer year);
    List<Book> findByYear(@NotNull Integer year);
    List<Book> findByYearBetween(@NotNull Integer minYear, @NotNull Integer maxYear);
}
