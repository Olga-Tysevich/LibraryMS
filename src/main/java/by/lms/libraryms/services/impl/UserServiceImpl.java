package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.ConfirmationCode;
import by.lms.libraryms.domain.auth.User;
import by.lms.libraryms.dto.common.UserDTO;
import by.lms.libraryms.dto.req.CreateUserDTO;
import by.lms.libraryms.dto.req.UserSearchReqDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.exceptions.ChangingObjectException;
import by.lms.libraryms.mappers.UserMapper;
import by.lms.libraryms.repo.UserRepo;
import by.lms.libraryms.repo.search.UserSearch;
import by.lms.libraryms.services.ConfirmationCodeService;
import by.lms.libraryms.services.NotificationService;
import by.lms.libraryms.services.UserService;
import by.lms.libraryms.services.messages.ConfirmationMessageService;
import by.lms.libraryms.services.messages.Message;
import by.lms.libraryms.services.searchobjects.UserSearchReq;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User, UserDTO,
        UserSearchReq, UserSearchReqDTO,
        UserRepo, UserSearch,
        UserMapper> implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationCodeService confirmationCodeService;
    private final NotificationService<UserDTO> notificationService;
    private final ConfirmationMessageService confirmationMessageService;

    public UserServiceImpl(UserRepo repository,
                           UserSearch searchRepo,
                           UserMapper mapper, PasswordEncoder passwordEncoder,
                           ConfirmationCodeService confirmationCodeService,
                           @Qualifier("emailNotificationService") NotificationService<UserDTO> notificationService,
                           ConfirmationMessageService confirmationMessageService) {
        super(repository, searchRepo, mapper);
        this.passwordEncoder = passwordEncoder;
        this.confirmationCodeService = confirmationCodeService;
        this.notificationService = notificationService;
        this.confirmationMessageService = confirmationMessageService;
    }

    @Override
    public UserDTO findByEmail(String email) {
        return getRepository()
                .findByEmail(email)
                .map(getMapper()::toDTO)
                .orElseThrow();
    }

    @Override
    @Transactional
    public ObjectChangedDTO<UserDTO> activateByCode(String confirmationCode) {
        ConfirmationCode code = confirmationCodeService.validateConfirmationCode(confirmationCode);
        User user = getRepository().findById(String.valueOf(code.getUserId())).orElseThrow();
        if (!user.isConfirmed()) {
            user.setConfirmed(true);
            getRepository().save(user);
        }
        return getMapper().toObjectChangedDTO(user, null);
    }

    @Override
    public ObjectChangedDTO<UserDTO> activate(String id) {
        return null;
    }

    @Override
    public ObjectChangedDTO<UserDTO> add(UserDTO dto) {
        CreateUserDTO userDTO = (CreateUserDTO) dto;
        char[] password = userDTO.getPassword();
        User user = getMapper().toEntity(userDTO);
        encodePassword(password, user);

        ObjectChangedDTO<UserDTO> result = Optional.of(user)
                .map(getRepository()::save)
                .map(entity -> getMapper().toObjectChangedDTO(entity, null))
                .orElseThrow(ChangingObjectException::new);

        sendEmailConfirmationMessage(result.getObject());
        return result;
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

    private void sendEmailConfirmationMessage(UserDTO user) {
        ConfirmationCode code = confirmationCodeService.createConfirmationCode(user.getId());
        Message message = confirmationMessageService.createEmailConfirmationMessage(user.getEmail(), code);
        notificationService.sendMessage(message);
    }
}
