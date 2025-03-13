package by.lms.libraryms.services;

import by.lms.libraryms.domain.StockBook;
import by.lms.libraryms.dto.req.StockBookDTO;
import by.lms.libraryms.dto.req.StockBookSearchReqDTO;
import by.lms.libraryms.mappers.StockBookMapper;
import by.lms.libraryms.services.searchobjects.StockBookSearchReq;

public interface StockBookService extends AbstractService<StockBook, StockBookDTO,
        StockBookSearchReq, StockBookSearchReqDTO,
        StockBookMapper> {
}
