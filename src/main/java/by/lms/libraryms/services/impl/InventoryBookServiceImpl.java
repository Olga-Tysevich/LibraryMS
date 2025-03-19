package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.InventoryBook;
import by.lms.libraryms.domain.inventorynumber.InventoryNumber;
import by.lms.libraryms.dto.req.InventoryBookDTO;
import by.lms.libraryms.dto.req.InventoryBookSearchReqDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.dto.resp.ObjectListChangedDTO;
import by.lms.libraryms.exceptions.BindingInventoryNumberException;
import by.lms.libraryms.exceptions.ChangingObjectException;
import by.lms.libraryms.exceptions.ObjectDoesNotExistException;
import by.lms.libraryms.exceptions.UnbindInventoryNumberException;
import by.lms.libraryms.mappers.InventoryBookMapper;
import by.lms.libraryms.repo.InventoryBookRepo;
import by.lms.libraryms.repo.search.InventoryBookSearch;
import by.lms.libraryms.services.InventoryBookService;
import by.lms.libraryms.services.InventoryNumberService;
import by.lms.libraryms.services.searchobjects.InventoryBookSearchReq;
import by.lms.libraryms.utils.Constants;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;


@Service
public class InventoryBookServiceImpl extends AbstractServiceImpl<InventoryBook, InventoryBookDTO,
        InventoryBookSearchReq, InventoryBookSearchReqDTO,
        InventoryBookRepo, InventoryBookSearch,
        InventoryBookMapper> implements InventoryBookService {
    @Value("${inventory.number.permissibleError}")
    private int permissibleError;
    private final InventoryNumberService inventoryNumberService;
    private final ReentrantLock lock = new ReentrantLock();

    public InventoryBookServiceImpl(InventoryBookRepo repository,
                                    InventoryBookSearch searchRepo,
                                    InventoryBookMapper mapper,
                                    InventoryNumberService inventoryNumberService) {
        super(repository, searchRepo, mapper);
        this.inventoryNumberService = inventoryNumberService;
    }

    //TODO добавить логику извлечения города из аккаунта библиотекаря и определения префикса по городу
    //TODO написать тест для многопоточки
    @Override
    @Transactional
    public ObjectChangedDTO<InventoryBookDTO> add(InventoryBookDTO dto) {
        lock.lock();

        InventoryBook result = getMapper().toEntity(dto);
        try {
            InventoryNumber inventoryNumber = inventoryNumberService.getLastNumber();
            result.setInventoryNumberId(new ObjectId(inventoryNumber.getId()));
            getRepository().save(result);
            inventoryNumberService.bind(result);
            return getMapper().toObjectChangedDTO(result, null);
        } catch (BindingInventoryNumberException e) {
            //TODO добавить лог
            System.out.println(e.getMessage());
            unbindInventoryNumber(result);
            getRepository().delete(result);
            throw new ChangingObjectException("Failed to create inventory book: " + dto + ". Cause: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    @Override
    @Transactional
    public ObjectChangedDTO<InventoryBookDTO> update(InventoryBookDTO dto) {
        InventoryBook result = getRepository().findById(dto.getId()).orElseThrow(ObjectDoesNotExistException::new);

        if (!result.getBookOrderIds().isEmpty())
            throw new ChangingObjectException("Unable to update previously checked out inventory book! " +
                    "Inventory book: " + result);

        LocalDate updatedAt = LocalDate.from(result.getUpdatedAt());
        LocalDate today = LocalDate.now();

        if (updatedAt.isBefore(today.minusDays(permissibleError))) {
            throw new IllegalArgumentException(String.format(
                    "Updating inventory book : %s is not possible! The allowed date for making changes has expired %s!",
                    result, updatedAt.plusDays(permissibleError)));
        }

        if (Objects.isNull(dto.getBook()) || Objects.isNull(dto.getBook().getId())) {
            throw new IllegalArgumentException(Constants.EMPTY_ID_MESSAGE);
        }

        result.setBookId(new ObjectId(dto.getId()));
        getRepository().save(result);
        return getMapper().toObjectChangedDTO(result, null);
    }

    @Override
    @Transactional
    public ObjectListChangedDTO<InventoryBookDTO> delete(InventoryBookSearchReqDTO searchReqDTO) {
        List<InventoryBook> result = getRepository().findAllById(searchReqDTO.getIds());
        if (result.isEmpty()) throw new ObjectDoesNotExistException();

        List<ObjectChangedDTO<InventoryBookDTO>> unbindingSuccessful = new ArrayList<>();
        List<ObjectChangedDTO<InventoryBookDTO>> unbindingFailed = new ArrayList<>();
        for (InventoryBook book : result) {

            try {
                if (!book.getBookOrderIds().isEmpty())
                    throw new ChangingObjectException("Unable to delete previously checked out inventory book! " +
                            "Inventory book: " + book);

                LocalDate updatedAt = LocalDate.from(book.getUpdatedAt());
                LocalDate today = LocalDate.now();
                if (updatedAt.isAfter(today)) {
                    throw new IllegalArgumentException(String.format(
                            "Deleting inventory book : %s is not possible! The allowed date for making changes has expired %s!",
                            result, updatedAt));
                }

                lock.lock();
                inventoryNumberService.unbind(book);
                unbindingSuccessful.add(getMapper().toObjectChangedDTO(book, Instant.now()));
            } catch (UnbindInventoryNumberException e) {
                //TODO добавить лог
                System.out.println(e.getMessage());
                unbindingFailed.add(getMapper().toObjectChangedDTO(book, null));
            } finally {
                lock.unlock();
            }
        }

        if (!unbindingFailed.isEmpty()) {
            //TODO добавить лог
            System.out.println("Failed to delete inventory numbers. You need to contact technical support! Inventory numbers: "
                    + unbindingFailed);
        }

        return new ObjectListChangedDTO<>(unbindingSuccessful);
    }

    @Override
    protected Class<InventoryBook> clazz() {
        return InventoryBook.class;
    }

    private void unbindInventoryNumber(InventoryBook inventoryBook) {
        try {
            inventoryNumberService.unbind(inventoryBook);
        } catch (UnbindInventoryNumberException e) {
            //TODO добавить лог
            System.out.println(e.getMessage());
            throw new ChangingObjectException("Failed to create inventory number for the item: " + inventoryBook
                    + ". You need to contact technical support!");
        }
    }

}
