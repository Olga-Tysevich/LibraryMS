package by.lms.libraryms.services;

import by.lms.libraryms.domain.auth.User;
import by.lms.libraryms.dto.common.UserDTO;
import by.lms.libraryms.dto.req.ChangePasswordDTO;
import by.lms.libraryms.dto.req.UserSearchReqDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.mappers.UserMapper;
import by.lms.libraryms.services.searchobjects.UserSearchReq;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

//TODO сделать сброс пароля
public interface UserService extends AbstractService<User, UserDTO,
        UserSearchReq, UserSearchReqDTO,
        UserMapper> {

    UserDTO findByEmail(@NotEmpty String email);

    ObjectChangedDTO<UserDTO> activateByCode(@NotEmpty String confirmationCode);

    void sendActivationCode(@NotEmpty String id);

    ObjectChangedDTO<UserDTO> changePassword(@NotNull ChangePasswordDTO changePasswordDTO);
}
