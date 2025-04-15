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

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserFacade userFacade;

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

    @GetMapping("/account/locale/change/{language_code}/{region_code}")
    public ResponseEntity<?> changeLocale(@PathVariable("language_code") String language,
                                            @PathVariable("region_code") String region) {
        ObjectChangedDTO<UserDTO> user = userFacade.changeLocale(language, region);
        return ResponseEntity.ok(user);
    }

    @GetMapping("account/locales")
    public ResponseEntity<?> getAvailableLocales() {
        return ResponseEntity.ok(userFacade.getAvailableLocales());
    }

}
