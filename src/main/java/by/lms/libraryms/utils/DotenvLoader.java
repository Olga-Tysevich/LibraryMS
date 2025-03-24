package by.lms.libraryms.utils;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.experimental.UtilityClass;

import java.util.Objects;

@UtilityClass
public class DotenvLoader {

    public void load() {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("JWT_ACCESS_KEY_SECRET", Objects.requireNonNull(dotenv.get("JWT_ACCESS_KEY_SECRET")));
        System.setProperty("JWT_ACCESS_KEY_EXPIRATION_TIME", Objects.requireNonNull(dotenv.get("JWT_ACCESS_KEY_EXPIRATION_TIME")));
        System.setProperty("JWT_REFRESH_KEY_SECRET", Objects.requireNonNull(dotenv.get("JWT_REFRESH_KEY_SECRET")));
        System.setProperty("JWT_REFRESH_KEY_EXPIRATION_TIME", Objects.requireNonNull(dotenv.get("JWT_REFRESH_KEY_EXPIRATION_TIME")));

        System.setProperty("ADMIN_URLS", Objects.requireNonNull(dotenv.get("ADMIN_URLS")));
        System.setProperty("USER_URLS", Objects.requireNonNull(dotenv.get("USER_URLS")));
        System.setProperty("READER_URLS", Objects.requireNonNull(dotenv.get("READER_URLS")));
        System.setProperty("LIBRARIAN_URLS", Objects.requireNonNull(dotenv.get("LIBRARIAN_URLS")));

        System.setProperty("LIBRARY_MS_GMAIL_USERNAME", Objects.requireNonNull(dotenv.get("LIBRARY_MS_GMAIL_USERNAME")));
        System.setProperty("LIBRARY_MS_GMAIL_PASSWORD", Objects.requireNonNull(dotenv.get("LIBRARY_MS_GMAIL_PASSWORD")));
    }

}
