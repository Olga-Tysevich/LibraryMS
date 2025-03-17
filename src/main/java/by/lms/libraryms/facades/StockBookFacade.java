package by.lms.libraryms.facades;


import by.lms.libraryms.domain.StockBook;
import by.lms.libraryms.dto.req.StockBookDTO;
import by.lms.libraryms.dto.req.StockBookSearchReqDTO;
import by.lms.libraryms.mappers.StockBookMapper;
import by.lms.libraryms.services.StockBookService;
import by.lms.libraryms.services.messages.StockBookMessageService;
import by.lms.libraryms.services.searchobjects.StockBookSearchReq;

public interface StockBookFacade extends AbstractFacade<StockBook, StockBookDTO,
        StockBookSearchReq, StockBookSearchReqDTO,
        StockBookService, StockBookMessageService,
        StockBookMapper> {

}
