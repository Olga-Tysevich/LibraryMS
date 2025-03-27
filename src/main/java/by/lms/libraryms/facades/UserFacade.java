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
    ObjectChangedDTO<UserDTO> confirmEmail(String code);

    void sendEmailConfirmationCode(String id);

    ObjectChangedDTO<UserDTO> changePassword(ChangePasswordDTO changePasswordDTO);

}
