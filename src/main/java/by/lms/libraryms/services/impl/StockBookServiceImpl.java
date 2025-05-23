package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.StockBook;
import by.lms.libraryms.dto.req.*;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.dto.resp.ObjectListChangedDTO;
import by.lms.libraryms.exceptions.ChangingObjectException;
import by.lms.libraryms.exceptions.ObjectDoesNotExistException;
import by.lms.libraryms.mappers.ObjectMapper;
import by.lms.libraryms.mappers.StockBookMapper;
import by.lms.libraryms.repo.StockBookRepo;
import by.lms.libraryms.repo.search.StockBookSearch;
import by.lms.libraryms.services.BookService;
import by.lms.libraryms.services.InventoryBookService;
import by.lms.libraryms.services.StockBookService;
import by.lms.libraryms.services.searchobjects.StockBookSearchReq;
import by.lms.libraryms.utils.Constants;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockBookServiceImpl extends AbstractServiceImpl<StockBook, StockBookDTO,
        StockBookSearchReq, StockBookSearchReqDTO,
        StockBookRepo, StockBookSearch,
        StockBookMapper> implements StockBookService {
    private final InventoryBookService inventoryBookService;
    private final BookService bookService;
    @Value("${inventory.number.permissibleError}")
    private int permissibleError;

    public StockBookServiceImpl(StockBookRepo repository,
                                StockBookSearch searchRepo,
                                StockBookMapper mapper,
                                InventoryBookService inventoryBookService,
                                BookService bookService) {
        super(repository, searchRepo, mapper);
        this.inventoryBookService = inventoryBookService;
        this.bookService = bookService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    @Override
    @Transactional
    public ObjectChangedDTO<StockBookDTO> add(StockBookDTO dto) {
        Set<String> savedInventoryBookIds = addInventoryBooks(dto);
        dto.setInventoryBookIds(savedInventoryBookIds);
        return super.add(dto);
    }

    //TODO переделать когда будет добавлен приход книг
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    @Override
    @Transactional
    public ObjectChangedDTO<StockBookDTO> update(StockBookDTO dto) {
        if (Objects.isNull(dto.getId())) throw new IllegalArgumentException(Constants.EMPTY_ID_MESSAGE);

        StockBook currentStockBook = getRepository().findById(dto.getId()).orElseThrow();


        List<InventoryBookDTO> currentInventoryBookSet = inventoryBookService.findAllByIds(
                        ObjectMapper.mapObjectIdSetToStringSet(currentStockBook.getInventoryBookIds())
                );

        List<String> currentInventoryBookIdsSet = currentInventoryBookSet.stream()
                .map(InventoryBookDTO::getId)
                .collect(Collectors.toList());

        if (!currentInventoryBookSet.getFirst().getBook().getId().equals(dto.getBookId())) {
            deleteInventoryBooks(currentInventoryBookIdsSet);
            Set<String> savedInventoryBookIds = addInventoryBooks(dto);
            dto.setInventoryBookIds(savedInventoryBookIds);

        } else if (currentInventoryBookSet.size() < dto.getQuantity()) {
            int quantity = dto.getQuantity();
            int quantityForCreate = dto.getQuantity() - currentInventoryBookSet.size();
            dto.setQuantity(quantityForCreate + currentInventoryBookSet.size());
            Set<String> savedInventoryBookIds = addInventoryBooks(dto);
            savedInventoryBookIds.addAll(currentInventoryBookIdsSet);
            dto.setQuantity(quantity);
            dto.setInventoryBookIds(savedInventoryBookIds);

        } else if (currentInventoryBookSet.size() > dto.getQuantity()) {
            int position = currentInventoryBookSet.size() - 1 - dto.getQuantity();
            List<String> forDelete = currentInventoryBookIdsSet.subList(currentInventoryBookIdsSet.size() - 1, position);
            deleteInventoryBooks(forDelete);
            currentInventoryBookIdsSet.removeAll(forDelete);
            dto.setInventoryBookIds(new HashSet<>(currentInventoryBookIdsSet));
        }
        return super.update(dto);
    }

    //TODO переделать когда будет добален приход книг
    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    @Override
    @Transactional
    public ObjectListChangedDTO<StockBookDTO> delete(StockBookSearchReqDTO searchReqDTO) {
        List<StockBook> stockBook = getSearchRepo().find(
                StockBookSearchReq.builder()
                        .ids(ObjectMapper.mapStringSetToObjectIdSet(searchReqDTO.getIds()))
                        .build()
        );
        if (stockBook.isEmpty()) throw new ObjectDoesNotExistException(StockBook.class.getSimpleName());

        List<ObjectChangedDTO<StockBookDTO>> result = new ArrayList<>();
        for (StockBook s : stockBook) {
            Set<String> forDelete = ObjectMapper.mapObjectIdSetToStringSet(s.getInventoryBookIds());

            Set<String> inventoryBookIds = inventoryBookService.findAllByIds(forDelete).stream()
                    .map(InventoryBookDTO::getId)
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
                s.setQuantity(unbindingBookIds.size());
                unbindingBookIds.forEach(s.getInventoryBookIds()::remove);
                getRepository().save(s);
            }

            if (inventoryBookIds.size() != unbindingBookIds.size()) {
                Set<ObjectId> nonRemovedInventoryBookIds = inventoryBookIds.stream()
                        .filter(id -> unbindingBookIds.contains(new ObjectId(id)))
                        .map(ObjectId::new)
                        .collect(Collectors.toSet());

                throw new ChangingObjectException("Failed to delete inventory books with ids: " + nonRemovedInventoryBookIds);
            }

            result.addAll(getRepository()
                    .findAllByIdIn(searchReqDTO.getIds())
                    .stream()
                    .map(e -> getMapper().toObjectChangedDTO(e, Instant.now()))
                    .toList());
        }

        return new ObjectListChangedDTO<>(result);
    }

    @Override
    protected Class<StockBook> clazz() {
        return StockBook.class;
    }

    private void deleteInventoryBooks(List<String> currentInventoryBookIdsSet) {
        ObjectListChangedDTO<InventoryBookDTO> unbindObjects = inventoryBookService.delete(buildReq(new HashSet<>(currentInventoryBookIdsSet)));
        int unbindInventoryBooksQuantity = unbindObjects.getObjects().size();
        if (unbindInventoryBooksQuantity < currentInventoryBookIdsSet.size())
            throw new ChangingObjectException("Not all inventory books were deleted. The following inventory books were deleted:: "
                    + unbindObjects.getObjects());
    }

    private Set<String> addInventoryBooks(StockBookDTO dto) {

        checkIfChangeIsPossible(dto);

        if (Objects.isNull(dto.getBookId())) {
            throw new IllegalArgumentException(Constants.EMPTY_ID_MESSAGE);
        }
        BookDTO bookDTO = bookService.findById(dto.getBookId());
        Set<String> savedInventoryBookIds = new HashSet<>(dto.getQuantity());
        for (int i = 0; i < dto.getQuantity(); i++) {
            InventoryBookDTO inventoryBookDTO = getMapper().toInventoryBookDTO(bookDTO, dto.getDateOfReceipt());
            try {
                ObjectChangedDTO<InventoryBookDTO> result = inventoryBookService.add(inventoryBookDTO);
                savedInventoryBookIds.add(result.getObject().getId());
            } catch (ChangingObjectException e) {
                InventoryBookSearchReqDTO forDelete = buildReq(savedInventoryBookIds);
                inventoryBookService.delete(forDelete);
                throw e;
            }
        }
        return savedInventoryBookIds;
    }

    private InventoryBookSearchReqDTO buildReq(Set<String> inventoryBookIds) {
        return InventoryBookSearchReqDTO.builder()
                .ids(inventoryBookIds)
                .build();
    }

    private void checkIfChangeIsPossible(StockBookDTO dto) {
        if (Objects.nonNull(dto.getId())) {
            StockBook stockBook = getRepository().findById(dto.getId()).orElseThrow();
            LocalDate updatedAt = LocalDate.ofInstant(stockBook.getUpdatedAt(), ZoneId.systemDefault());
            LocalDate today = LocalDate.now();

            if (updatedAt.isBefore(today.minusDays(permissibleError))) {
                throw new IllegalArgumentException(String.format(
                        "Updating stock book : %s is not possible! The allowed date for making changes has expired %s!",
                        stockBook, updatedAt.plusDays(permissibleError)));
            }
        }
    }

}
