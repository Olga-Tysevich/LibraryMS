package by.lms.libraryms.facades;

import by.lms.libraryms.domain.auth.User;
import by.lms.libraryms.dto.common.UserDTO;
import by.lms.libraryms.dto.req.UserSearchReqDTO;
import by.lms.libraryms.services.UserService;
import by.lms.libraryms.services.searchobjects.UserSearchReq;

public interface UserFacade extends AbstractFacade<User, UserDTO,
        UserSearchReq, UserSearchReqDTO,
        UserService,  {

}
