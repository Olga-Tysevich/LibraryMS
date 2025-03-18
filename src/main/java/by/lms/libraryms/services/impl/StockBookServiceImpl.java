package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.StockBook;
import by.lms.libraryms.dto.req.*;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.dto.resp.ObjectListChangedDTO;
import by.lms.libraryms.exceptions.ChangingObjectException;
import by.lms.libraryms.mappers.StockBookMapper;
import by.lms.libraryms.repo.StockBookRepo;
import by.lms.libraryms.repo.search.StockBookSearch;
import by.lms.libraryms.services.BookService;
import by.lms.libraryms.services.InventoryBookService;
import by.lms.libraryms.services.StockBookService;
import by.lms.libraryms.services.searchobjects.StockBookSearchReq;
import by.lms.libraryms.utils.Constants;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

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
                                BookService bookService) {
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
        for (int i = 0; i < dto.getQuantity(); i++) {
            InventoryBookDTO inventoryBookDTO = getMapper().toInventoryBookDTO(bookDTO, dto.getDateOfReceipt());
            inventoryBookService.add(inventoryBookDTO);
        }

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
    @Transactional
    public ObjectListChangedDTO<StockBookDTO> delete(StockBookSearchReqDTO searchReqDTO) {
        Set<String> inventoryBookIds = getRepository().findAllById(searchReqDTO.getIds()).stream()
                .map(StockBook::getBookId)
                .map(ObjectId::toString)
                .collect(Collectors.toSet());

        if (inventoryBookIds.isEmpty()) throw new IllegalArgumentException(Constants.EMPTY_ID_MESSAGE);

        InventoryBookSearchReqDTO inventoryBooksForDelete = InventoryBookSearchReqDTO.builder()
                .ids(inventoryBookIds)
                .build();

        ObjectListChangedDTO<InventoryBookDTO> unbindingBooks = inventoryBookService.delete(inventoryBooksForDelete);
        List<ObjectId> unbindingBookIds = unbindingBooks.getObjects().stream()
                .map(ObjectChangedDTO::getObject)
                .map(InventoryBookDTO::getBook)
                .map(BookDTO::getId)
                .map(ObjectId::new)
                .toList();

        if (!unbindingBookIds.isEmpty()) {
            getRepository().deleteAllByBookIdIn(unbindingBookIds);
        }

        if (inventoryBookIds.size() != unbindingBookIds.size()) {
            Set<ObjectId> nonRemovedInventoryBookIds = inventoryBookIds.stream()
                    .filter(id -> unbindingBookIds.contains(new ObjectId(id)))
                    .map(ObjectId::new)
                    .collect(Collectors.toSet());
            List<StockBookDTO>  nonRemovedStockBooks = getRepository().findAllByBookIdIn(nonRemovedInventoryBookIds).stream()
                    .map(getMapper()::toDTO)
                    .toList();

            throw new ChangingObjectException("Failed to delete stock books: " + nonRemovedStockBooks);
        }

        List<ObjectChangedDTO<StockBookDTO>> result = getRepository().findAllByIdIn(searchReqDTO.getIds()).stream()
                .map(e -> getMapper().toObjectChangedDTO(e, Instant.now()))
                .toList();

        return new ObjectListChangedDTO<>(result);
    }

    @Override
    protected Class<StockBook> clazz() {
        return StockBook.class;
    }

}
