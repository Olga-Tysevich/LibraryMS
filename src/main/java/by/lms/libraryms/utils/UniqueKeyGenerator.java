package by.lms.libraryms.utils;

import by.lms.libraryms.domain.Book;
import by.lms.libraryms.dto.req.BookDTO;
import lombok.experimental.UtilityClass;
import org.bson.types.ObjectId;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;

@UtilityClass
public final class UniqueKeyGenerator {

    public String generateUniqueKey(Book book) {
        return generateHash(book.getTitle() + "_" + book.getYear() + "_" +
                book.getAuthorIds().stream().map(ObjectId::toString).sorted().collect(Collectors.joining("-")) + "_" +
                book.getGenreIds().stream().map(ObjectId::toString).sorted().collect(Collectors.joining("-")));
    }

    public String generateUniqueKey(BookDTO book) {
        return generateHash(book.getTitle() + "_" + book.getYear() + "_" +
                book.getAuthorIds().stream().sorted().collect(Collectors.joining("-")) + "_" +
                book.getGenreIds().stream().sorted().collect(Collectors.joining("-")));
    }


    private String generateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            //TODO добавить логи
            throw new RuntimeException("Error while generating hash", e);
        }
    }
}
