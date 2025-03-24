package by.lms.libraryms.facades.impl;

import by.lms.libraryms.domain.Genre;
import by.lms.libraryms.dto.req.GenreDTO;
import by.lms.libraryms.dto.req.GenreSearchReqDTO;
import by.lms.libraryms.facades.GenreFacade;
import by.lms.libraryms.mappers.GenreMapper;
import by.lms.libraryms.services.GenreService;
import by.lms.libraryms.services.NotificationService;
import by.lms.libraryms.services.messages.GenreMessageService;
import by.lms.libraryms.services.searchobjects.GenreSearchReq;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class GenreFacadeImpl extends AbstractFacadeImpl<Genre, GenreDTO,
        GenreSearchReq, GenreSearchReqDTO,
        GenreService, GenreMessageService,
        GenreMapper> implements GenreFacade{

    public GenreFacadeImpl(GenreService service,
                           @Qualifier("telegramNotificationService") NotificationService<GenreDTO> notificationService,
                           GenreMessageService messageService) {
        super(service, notificationService, messageService);
    }

}
