package by.lms.libraryms.services;

import by.lms.libraryms.domain.auth.User;
import by.lms.libraryms.dto.common.UserDTO;
import by.lms.libraryms.dto.req.ChangePasswordDTO;
import by.lms.libraryms.dto.req.UserSearchReqDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.mappers.UserMapper;
import by.lms.libraryms.services.searchobjects.UserSearchReq;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Locale;

//TODO сделать сброс пароля
public interface UserService extends AbstractService<User, UserDTO,
        UserSearchReq, UserSearchReqDTO,
        UserMapper> {

    UserDTO findByEmail(@NotBlank String email);

    ObjectChangedDTO<UserDTO> activateByCode(@NotBlank String confirmationCode);

    void sendActivationCode(@NotBlank String id);

    ObjectChangedDTO<UserDTO> changePassword(@NotNull ChangePasswordDTO changePasswordDTO);

    ObjectChangedDTO<UserDTO> changeLocale(@NotBlank String language, @NotBlank String region);

    Locale getCurrentLocale(@NotBlank String email);
}
