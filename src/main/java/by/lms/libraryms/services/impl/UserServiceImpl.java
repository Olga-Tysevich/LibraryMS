package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.ConfirmationCode;
import by.lms.libraryms.domain.RoleEnum;
import by.lms.libraryms.domain.auth.User;
import by.lms.libraryms.dto.common.UserDTO;
import by.lms.libraryms.dto.req.ChangePasswordDTO;
import by.lms.libraryms.dto.req.CreateUserDTO;
import by.lms.libraryms.dto.req.UserSearchReqDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.exceptions.ActionProhibitedException;
import by.lms.libraryms.exceptions.ChangingObjectException;
import by.lms.libraryms.exceptions.InvalidOldPasswordException;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Locale;
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
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public UserDTO findByEmail(String email) {
        return getRepository()
                .findByEmail(email)
                .map(getMapper()::toDTO)
                .orElseThrow();
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    public ObjectChangedDTO<UserDTO> add(UserDTO dto) {
        CreateUserDTO userDTO = (CreateUserDTO) dto;
        User user = getMapper().toEntity(userDTO);
        setPassword(userDTO, user);

        ObjectChangedDTO<UserDTO> result = Optional.of(user)
                .map(getRepository()::save)
                .map(entity -> getMapper().toObjectChangedDTO(entity, null))
                .orElseThrow(ChangingObjectException::new);

        UserDTO userResult = result.getObject();
        sendEmailConfirmationMessage(userResult.getId(), userResult.getEmail());
        return result;
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
    public void sendActivationCode(String id) {
        User user = getRepository().findById(id).orElseThrow();
        sendEmailConfirmationMessage(user.getId(), user.getEmail());
    }

    @Override
    public ObjectChangedDTO<UserDTO> changePassword(ChangePasswordDTO changePasswordDTO) {
        User currentUser = getCurrentAuthenticatedUser();

        validatePermissionToChangeObject(currentUser, changePasswordDTO.getUserId());

        User user = getUserById(changePasswordDTO.getUserId());

        validateUserIsConfirmed(user);

        String oldPassword = new String(changePasswordDTO.getOldPassword());

        validateOldPassword(user, oldPassword);
        validateNewPasswordIsDifferent(user, oldPassword);

        setPassword(changePasswordDTO, user);
        getRepository().save(user);

        return getMapper().toObjectChangedDTO(user, null);
    }

    @Override
    public ObjectChangedDTO<UserDTO> changeLocale(String language, String region) {
        User user = getCurrentAuthenticatedUser();

        if (!user.isConfirmed())
            throw new ChangingObjectException("User with id: " + user.getId() + " is not confirmed!");

        Locale locale = new Locale.Builder()
                .setLanguage(language)
                .setRegion(region)
                .build();

        user.setLocale(locale);

        return getMapper().toObjectChangedDTO(user, null);
    }

    @Override
    public Locale getCurrentLocale(String email) {
        return getRepository()
                .findByEmail(email)
                .orElseThrow()
                .getLocale();
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

    private void setPassword(ChangePasswordDTO dto, User user) {
        try {
            setEncodePassword(dto.getPassword(), user);
        } finally {
            Arrays.fill(dto.getPassword(), '\0');
            Arrays.fill(dto.getPasswordConfirmation(), '\0');
            Arrays.fill(dto.getOldPassword(), '\0');
        }
    }

    private void setPassword(CreateUserDTO dto, User user) {
        try {
            setEncodePassword(dto.getPassword(), user);
        } finally {
            Arrays.fill(dto.getPassword(), '\0');
            Arrays.fill(dto.getPasswordConfirmation(), '\0');
        }
    }

    private void setEncodePassword(char[] password, User user) {
        String encodedPassword = passwordEncoder.encode(new String(password));
        user.setPassword(encodedPassword);
    }

    private void sendEmailConfirmationMessage(String userId, String email) {
        ConfirmationCode code = confirmationCodeService.createConfirmationCode(userId);
        Message message = confirmationMessageService.createEmailConfirmationMessage(email, code);
        notificationService.sendMessage(message);
    }

    private User getCurrentAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getRepository().findByEmail(username).orElseThrow();
    }

    private void validatePermissionToChangeObject(User currentUser, String targetUserId) {
        if (!currentUser.getId().equals(targetUserId) && !currentUser.hasRole(RoleEnum.ROLE_ADMIN))
            throw new ActionProhibitedException("Password change for user id: " + targetUserId
                    + " is prohibited! Current user id: " + currentUser.getId());
    }

    private User getUserById(String userId) {
        return getRepository().findById(userId).orElseThrow();
    }

    private void validateUserIsConfirmed(User user) {
        if (!user.isConfirmed())
            throw new ChangingObjectException("User with id: " + user.getId() + " is not confirmed!");
    }

    private void validateOldPassword(User user, String oldPassword) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword()))
            throw new InvalidOldPasswordException(user.getEmail());
    }

    private void validateNewPasswordIsDifferent(User user, String newPassword) {
        if (passwordEncoder.matches(newPassword, user.getPassword()))
            throw new ChangingObjectException("New password must be different from the old one.");
    }
}
