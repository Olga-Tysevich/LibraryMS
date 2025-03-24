package by.lms.libraryms.controller;

import by.lms.libraryms.dto.common.UserDTO;
import by.lms.libraryms.dto.req.CreateUserDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.facades.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserFacade userFacade;
    private final LocaleResolver localeResolver;

    @PostMapping("/add/employee")
    public ResponseEntity<?> add(@RequestBody CreateUserDTO createUserDTO) {
        ObjectChangedDTO<UserDTO> result = userFacade.add(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/account/email/confirm/{code}")
    public ResponseEntity<?> confirmEmail(@PathVariable("code") String code) {
        String userId = userFacade.confirmEmail(code);
        return ResponseEntity.ok(isConfirmed);
    }


    // Получение доступных локалей
    @GetMapping("account/locales")
    public ResponseEntity<?> getAvailableLocales() {
        List<String> availableLocales = Arrays.asList("en", "ru", "fr", "de", "es"); // Пример доступных локалей
        return ResponseEntity.ok(availableLocales);
    }

    // Метод для изменения локали
    @PostMapping("account/change-locale")
    public ResponseEntity<?> changeLocale(@RequestParam String id, @RequestParam String locale) {
//        User user = userFacade.changeUserLocale(username, locale); // Сохраняем выбранную локаль в БД
//        return ResponseEntity.ok("Locale updated to: " + user.getLocale());
        return null;
    }
}
