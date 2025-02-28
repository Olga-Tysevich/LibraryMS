package by.lms.libraryms.repo;

import by.lms.libraryms.domain.Book;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface BookRepo extends MongoRepository<Book, String> {
    List<Book> findByAuthorIds(@NotNull Set<ObjectId> authorIds);
    List<Book> findByGenreId(@NotNull Set<ObjectId> genreId);
    List<Book> findByTitle(String title);
    List<Book> findByTitleAndGenreId(@NotNull String title, @NotNull Set<ObjectId> genreId);
    List<Book> findByTitleAndYear(@NotNull String title, @NotNull Integer year);
    List<Book> findByYear(@NotNull Integer year);
    List<Book> findByYearBetween(@NotNull Integer minYear, @NotNull Integer maxYear);
    List<Book> findByAvailableAndGenreIdIn(boolean available, Collection<@NotNull Set<ObjectId>> genreId);
    List<Book> findByAvailableAndAuthorIds(boolean available, @NotNull Set<ObjectId> authorIds);
    List<Book> findByAvailableAndYear(boolean available, @NotNull int year);
}
