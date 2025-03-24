package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.auth.User;
import by.lms.libraryms.dto.common.UserDTO;
import by.lms.libraryms.dto.req.CreateUserDTO;
import by.lms.libraryms.dto.req.UserSearchReqDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.exceptions.ChangingObjectException;
import by.lms.libraryms.mappers.UserMapper;
import by.lms.libraryms.repo.UserRepo;
import by.lms.libraryms.repo.search.UserSearch;
import by.lms.libraryms.services.UserService;
import by.lms.libraryms.services.searchobjects.UserSearchReq;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User, UserDTO,
        UserSearchReq, UserSearchReqDTO,
        UserRepo, UserSearch,
        UserMapper> implements UserService {
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo repository,
                           UserSearch searchRepo,
                           UserMapper mapper, PasswordEncoder passwordEncoder) {
        super(repository, searchRepo, mapper);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO findByEmail(String email) {
        return getRepository()
                .findByEmail(email)
                .map(getMapper()::toDTO)
                .orElseThrow();
    }

    @Override
    public ObjectChangedDTO<UserDTO> add(UserDTO dto) {
        CreateUserDTO userDTO = (CreateUserDTO) dto;
        char[] password = userDTO.getPassword();
        User user = getMapper().toEntity(userDTO);
        encodePassword(password, user);

        return Optional.of(user)
                .map(getRepository()::save)
                .map(entity -> getMapper().toObjectChangedDTO(entity, null))
                .orElseThrow(ChangingObjectException::new);
    }

    @Override
    public ObjectChangedDTO<UserDTO> update(UserDTO dto) {
        //TODO добавить лог
        throw new UnsupportedOperationException("Attempting to update user: " + dto.getId());
    }

    @Override
    protected Class<User> clazz() {
        return User.class;
    }

    private void encodePassword(char[] password, User user) {
        String encodedPassword = passwordEncoder.encode(new String(password));
        user.setPassword(encodedPassword);
        Arrays.fill(password, '\0');
    }
}
