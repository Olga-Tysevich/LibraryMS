package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.InventoryBook;
import by.lms.libraryms.dto.req.InventoryBookDTO;
import by.lms.libraryms.dto.req.InventoryBookSearchReqDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.exceptions.BindingInventoryNumberException;
import by.lms.libraryms.exceptions.ChangingObjectException;
import by.lms.libraryms.exceptions.ObjectNotFound;
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

import java.time.LocalDate;
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
            inventoryNumberService.add(result);
            getRepository().save(result);
            return getMapper().toObjectChangedDTO(result, null);
        } catch (BindingInventoryNumberException e) {
            //TODO добавить лог
            System.out.println(e.getMessage());
            unbindInventoryNumber(result);
        } finally {
            lock.unlock();
        }

        return getMapper().toObjectChangedDTO(result, null);
    }

    @Override
    @Transactional
    public ObjectChangedDTO<InventoryBookDTO> update(InventoryBookDTO dto) {
        InventoryBook result = getRepository().findById(dto.getId()).orElseThrow(ObjectNotFound::new);

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
    public ObjectChangedDTO<InventoryBookDTO> delete(InventoryBookSearchReqDTO searchReqDTO) {
        return super.delete(searchReqDTO);
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
