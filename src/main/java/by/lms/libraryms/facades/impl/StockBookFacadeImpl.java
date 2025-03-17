package by.lms.libraryms.facades.impl;

import by.lms.libraryms.domain.StockBook;
import by.lms.libraryms.dto.req.StockBookDTO;
import by.lms.libraryms.dto.req.StockBookSearchReqDTO;
import by.lms.libraryms.facades.StockBookFacade;
import by.lms.libraryms.mappers.StockBookMapper;
import by.lms.libraryms.services.NotificationService;
import by.lms.libraryms.services.StockBookService;
import by.lms.libraryms.services.messages.StockBookMessageService;
import by.lms.libraryms.services.searchobjects.StockBookSearchReq;
import org.springframework.stereotype.Component;

@Component
public class StockBookFacadeImpl extends AbstractFacadeImpl<StockBook, StockBookDTO,
        StockBookSearchReq, StockBookSearchReqDTO,
        StockBookService, StockBookMessageService,
        StockBookMapper> implements StockBookFacade {

    public StockBookFacadeImpl(StockBookService service,
                               NotificationService<StockBookDTO> notificationService,
                               StockBookMessageService messageService) {
        super(service, notificationService, messageService);
    }
}
