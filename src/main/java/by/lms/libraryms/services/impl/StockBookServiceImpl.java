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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;

@Service
public class StockBookServiceImpl extends AbstractServiceImpl<StockBook, StockBookDTO,
        StockBookSearchReq, StockBookSearchReqDTO,
        StockBookRepo, StockBookSearch,
        StockBookMapper> implements StockBookService {
    private final InventoryBookService inventoryBookService;
    private final BookService bookService;
    private final InventoryNumberService inventoryNumberService;

    public StockBookServiceImpl(StockBookRepo repository,
                                StockBookSearch searchRepo,
                                StockBookMapper mapper,
                                InventoryBookService inventoryBookService,
                                BookService bookService, InventoryNumberService inventoryNumberService) {
        super(repository, searchRepo, mapper);
        this.inventoryBookService = inventoryBookService;
        this.bookService = bookService;
        this.inventoryNumberService = inventoryNumberService;
    }

    @Override
    @Transactional
    public ObjectChangedDTO<StockBookDTO> add(StockBookDTO dto) {
        BookDTO bookDTO = bookService.findById(dto.getBookId());
        InventoryBookDTO inventoryBookDTO = buildInventoryBookDTO(bookDTO, dto.getDateOfReceipt());
        inventoryBookService.add(inventoryBookDTO);
        return super.add(dto);
    }

    @Override
    public ObjectChangedDTO<StockBookDTO> update(StockBookDTO dto) {
        if (Objects.isNull(dto.getId())) {
            throw  new IllegalArgumentException(Constants.EMPTY_ID_MESSAGE);
        }

        InventoryBookDTO inventoryBookDTO = buildInventoryBookDTO(bookDTO, dto.getDateOfReceipt());
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

    private InventoryBookDTO buildInventoryBookDTO(BookDTO bookDTO, LocalDate dateOfReceipt) {
        return InventoryBookDTO.builder()
                .book(bookDTO)
                .dateOfReceipt(dateOfReceipt)
                .build();
    }
}
