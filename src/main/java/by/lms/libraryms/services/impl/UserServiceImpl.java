package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.auth.User;
import by.lms.libraryms.dto.common.UserDTO;
import by.lms.libraryms.dto.req.UserSearchReqDTO;
import by.lms.libraryms.mappers.UserMapper;
import by.lms.libraryms.repo.UserRepo;
import by.lms.libraryms.repo.search.UserSearch;
import by.lms.libraryms.services.UserService;
import by.lms.libraryms.services.searchobjects.UserSearchReq;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User, UserDTO,
        UserSearchReq, UserSearchReqDTO,
        UserRepo, UserSearch,
        UserMapper> implements UserService {

    public UserServiceImpl(UserRepo repository,
                           UserSearch searchRepo,
                           UserMapper mapper) {
        super(repository, searchRepo, mapper);
    }

    @Override
    public UserDTO findByEmail(String email) {
        return getRepository()
                .findByEmail(email)
                .map(getMapper()::toDTO)
                .orElseThrow();
    }

    @Override
    protected Class<User> clazz() {
        return User.class;
    }
}
