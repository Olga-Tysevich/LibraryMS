package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.StockBook;
import by.lms.libraryms.dto.req.BookDTO;
import by.lms.libraryms.dto.req.InventoryBookDTO;
import by.lms.libraryms.dto.req.StockBookDTO;
import by.lms.libraryms.dto.req.StockBookSearchReqDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.mappers.StockBookMapper;
import by.lms.libraryms.repo.StockBookRepo;
import by.lms.libraryms.repo.search.StockBookSearch;
import by.lms.libraryms.services.BookService;
import by.lms.libraryms.services.InventoryBookService;
import by.lms.libraryms.services.InventoryNumberService;
import by.lms.libraryms.services.StockBookService;
import by.lms.libraryms.services.searchobjects.StockBookSearchReq;
import by.lms.libraryms.utils.Constants;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class StockBookServiceImpl extends AbstractServiceImpl<StockBook, StockBookDTO,
        StockBookSearchReq, StockBookSearchReqDTO,
        StockBookRepo, StockBookSearch,
        StockBookMapper> implements StockBookService {
    private final InventoryBookService inventoryBookService;
    private final BookService bookService;

    public StockBookServiceImpl(StockBookRepo repository,
                                StockBookSearch searchRepo,
                                StockBookMapper mapper,
                                InventoryBookService inventoryBookService,
                                BookService bookService, InventoryNumberService inventoryNumberService) {
        super(repository, searchRepo, mapper);
        this.inventoryBookService = inventoryBookService;
        this.bookService = bookService;
    }

    @Override
    @Transactional
    public ObjectChangedDTO<StockBookDTO> add(StockBookDTO dto) {
        if (Objects.isNull(dto.getBookId())) {
            throw new IllegalArgumentException(Constants.EMPTY_ID_MESSAGE);
        }
        BookDTO bookDTO = bookService.findById(dto.getBookId());
        InventoryBookDTO inventoryBookDTO = getMapper().toInventoryBookDTO(null, bookDTO, dto.getDateOfReceipt());
        inventoryBookService.add(inventoryBookDTO);
        return super.add(dto);
    }

    @Override
    @Transactional
    public ObjectChangedDTO<StockBookDTO> update(StockBookDTO dto) {
        if (Objects.isNull(dto.getId())) {
            throw new IllegalArgumentException(Constants.EMPTY_ID_MESSAGE);
        }
        BookDTO bookDTO = bookService.findById(dto.getBookId());
        InventoryBookDTO inventoryBookDTO = getMapper().toInventoryBookDTO(new ObjectId(dto.getId()), bookDTO, dto.getDateOfReceipt());
        inventoryBookService.update(inventoryBookDTO);
        return super.update(dto);
    }

    @Override
    public ObjectChangedDTO<StockBookDTO> delete(StockBookSearchReqDTO searchReqDTO) {
        return super.delete(searchReqDTO);
    }

    @Override
    protected Class<StockBook> clazz() {
        return StockBook.class;
    }

}
