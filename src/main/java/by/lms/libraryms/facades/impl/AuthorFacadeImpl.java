package by.lms.libraryms.facades.impl;

import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.domain.Author;
import by.lms.libraryms.dto.req.AuthorDTO;
import by.lms.libraryms.dto.req.AuthorSearchReqDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.facades.AuthorFacade;
import by.lms.libraryms.mappers.AuthorMapper;
import by.lms.libraryms.services.AuthorService;
import by.lms.libraryms.services.NotificationService;
import by.lms.libraryms.services.messages.AuthorMessageService;
import by.lms.libraryms.services.searchobjects.AuthorSearchReq;
import org.springframework.stereotype.Component;


@Component
public class AuthorFacadeImpl extends AbstractFacadeImpl<
        Author, AuthorDTO,
        AuthorSearchReq, AuthorSearchReqDTO,
        AuthorService, AuthorMessageService,
        AuthorMapper>
        implements AuthorFacade {


    public AuthorFacadeImpl(AuthorService service,
                            NotificationService<AuthorDTO> notificationService,
                            AuthorMessageService messageService) {
        super(service, notificationService, messageService);
    }

    @Override
    protected String message(MessageTypeEnum type, ObjectChangedDTO<AuthorDTO> result) {
                return getMessageService().createMessage(type, result);
    }

}
