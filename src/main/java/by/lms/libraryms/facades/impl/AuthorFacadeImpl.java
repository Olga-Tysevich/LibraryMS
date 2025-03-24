package by.lms.libraryms.facades.impl;

import by.lms.libraryms.domain.Author;
import by.lms.libraryms.dto.req.AuthorDTO;
import by.lms.libraryms.dto.req.AuthorSearchReqDTO;
import by.lms.libraryms.facades.AuthorFacade;
import by.lms.libraryms.mappers.AuthorMapper;
import by.lms.libraryms.services.AuthorService;
import by.lms.libraryms.services.NotificationService;
import by.lms.libraryms.services.messages.AuthorMessageService;
import by.lms.libraryms.services.searchobjects.AuthorSearchReq;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class AuthorFacadeImpl extends AbstractFacadeImpl<
        Author, AuthorDTO,
        AuthorSearchReq, AuthorSearchReqDTO,
        AuthorService, AuthorMessageService,
        AuthorMapper>
        implements AuthorFacade {


    public AuthorFacadeImpl(AuthorService service,
                            @Qualifier("telegramNotificationService") NotificationService<AuthorDTO> notificationService,
                            AuthorMessageService messageService) {
        super(service, notificationService, messageService);
    }
}
