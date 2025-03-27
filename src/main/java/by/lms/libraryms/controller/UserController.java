package by.lms.libraryms.controller;

import by.lms.libraryms.dto.common.UserDTO;
import by.lms.libraryms.dto.req.ChangePasswordDTO;
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
        ObjectChangedDTO<UserDTO> user = userFacade.confirmEmail(code);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/account/email/confirm/send/{id}")
    public ResponseEntity<?> sendEmailConfirmationCode(@PathVariable("id") String id) {
        userFacade.sendEmailConfirmationCode(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/account/password/change")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        ObjectChangedDTO<UserDTO> user = userFacade.changePassword(changePasswordDTO);
        return ResponseEntity.ok(user);
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
