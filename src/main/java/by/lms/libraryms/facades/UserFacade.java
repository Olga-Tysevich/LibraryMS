package by.lms.libraryms.facades;

import by.lms.libraryms.domain.auth.User;
import by.lms.libraryms.dto.common.UserDTO;
import by.lms.libraryms.dto.req.ChangePasswordDTO;
import by.lms.libraryms.dto.req.UserSearchReqDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.mappers.UserMapper;
import by.lms.libraryms.services.UserService;
import by.lms.libraryms.services.messages.UserMessageService;
import by.lms.libraryms.services.searchobjects.UserSearchReq;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Locale;
import java.util.Set;

public interface UserFacade extends AbstractFacade<User, UserDTO,
        UserSearchReq, UserSearchReqDTO,
        UserService, UserMessageService,
        UserMapper> {
    /**
     * User email verification method.
     *
     * @param code Code sent to the user's email.
     * @return ObjectChangedDTO<UserDTO> in case of successful confirmation.
     */
    ObjectChangedDTO<UserDTO> confirmEmail(@NotBlank String code);

    void sendEmailConfirmationCode(@NotBlank String id);

    ObjectChangedDTO<UserDTO> changePassword(@NotNull ChangePasswordDTO changePasswordDTO);
    ObjectChangedDTO<UserDTO> changeLocale(@NotBlank String language,@NotBlank String region);

    Set<String> getAvailableLocales();

}
