package by.lms.libraryms.controller;

import by.lms.libraryms.domain.User;
import by.lms.libraryms.facades.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

//    private final UserFacade userFacade;
    private final LocaleResolver localeResolver;

    // Получение доступных локалей
    @GetMapping("/locales")
    public ResponseEntity<List<String>> getAvailableLocales() {
        List<String> availableLocales = Arrays.asList("en", "ru", "fr", "de", "es"); // Пример доступных локалей
        return ResponseEntity.ok(availableLocales);
    }

    // Метод для изменения локали
    @PostMapping("/change-locale")
    public ResponseEntity<String> changeLocale(@RequestParam String username, @RequestParam String locale) {
//        User user = userFacade.changeUserLocale(username, locale); // Сохраняем выбранную локаль в БД
//        return ResponseEntity.ok("Locale updated to: " + user.getLocale());
        return null;
    }
}
