package by.lms.libraryms.facades.impl;

import by.lms.libraryms.conf.i18n.LocaleConfig;
import by.lms.libraryms.domain.auth.User;
import by.lms.libraryms.dto.common.UserDTO;
import by.lms.libraryms.dto.req.ChangePasswordDTO;
import by.lms.libraryms.dto.req.UserSearchReqDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.facades.UserFacade;
import by.lms.libraryms.mappers.UserMapper;
import by.lms.libraryms.services.NotificationService;
import by.lms.libraryms.services.UserService;
import by.lms.libraryms.services.messages.UserMessageService;
import by.lms.libraryms.services.searchobjects.UserSearchReq;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class UserFacadeImpl extends AbstractFacadeImpl<User, UserDTO,
        UserSearchReq, UserSearchReqDTO,
        UserService, UserMessageService,
        UserMapper> implements UserFacade {

    public UserFacadeImpl(UserService service,
                          @Qualifier("telegramNotificationService") NotificationService<UserDTO> notificationService,
                          UserMessageService messageService) {
        super(service, notificationService, messageService);
    }

    @Override
    public ObjectChangedDTO<UserDTO> add(UserDTO dto) {
        return super.add(dto);
    }

    @Override
    public ObjectChangedDTO<UserDTO> update(UserDTO dto) {
        throw new UnsupportedOperationException("User update is prohibited! User id: " + dto.getId());
    }

    @Override
    public ObjectChangedDTO<UserDTO> confirmEmail(String code) {
        return getService().activateByCode(code);
    }

    @Override
    public void sendEmailConfirmationCode(String id) {
        getService().sendActivationCode(id);
    }

    @Override
    public ObjectChangedDTO<UserDTO> changePassword(ChangePasswordDTO changePasswordDTO) {
        return getService().changePassword(changePasswordDTO);
    }

    @Override
    public ObjectChangedDTO<UserDTO> changeLocale(String language, String region) {
        return getService().changeLocale(language, region);
    }

    @Override
    public Set<String> getAvailableLocales() {
        return new HashSet<>(LocaleConfig.LOCALES);
    }
}
