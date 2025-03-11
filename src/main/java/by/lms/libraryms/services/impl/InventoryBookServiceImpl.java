package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.InventoryBook;
import by.lms.libraryms.dto.req.InventoryBookDTO;
import by.lms.libraryms.dto.req.InventoryBookSearchReqDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.exceptions.BindingInventoryNumberException;
import by.lms.libraryms.exceptions.ChangingObjectException;
import by.lms.libraryms.exceptions.UnbindInventoryNumberException;
import by.lms.libraryms.mappers.InventoryBookMapper;
import by.lms.libraryms.repo.InventoryBookRepo;
import by.lms.libraryms.repo.search.InventoryBookSearch;
import by.lms.libraryms.services.InventoryBookService;
import by.lms.libraryms.services.InventoryNumberService;
import by.lms.libraryms.services.searchobjects.InventoryBookSearchReq;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


@Service
public class InventoryBookServiceImpl extends AbstractServiceImpl<InventoryBook, InventoryBookDTO,
        InventoryBookSearchReq, InventoryBookSearchReqDTO,
        InventoryBookRepo, InventoryBookSearch,
        InventoryBookMapper> implements InventoryBookService {
    private final InventoryNumberService inventoryNumberService;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition lockCondition = lock.newCondition();
    private boolean isCreated = true;

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
        while (!isCreated) {
            try {
                lockCondition.await();
            } catch (InterruptedException e) {
                //TODO добавить лог
                System.out.println(e.getMessage());
            }
        }

        InventoryBook result = getMapper().toEntity(dto);
        try {
            isCreated = false;
            inventoryNumberService.add(result);
            getRepository().save(result);
            return getMapper().toObjectChangedDTO(result, null);
        } catch (BindingInventoryNumberException e) {
            //TODO добавить лог
            System.out.println(e.getMessage());
            unbindInventoryNumber(result);
        } finally {
            isCreated = true;
        }

        return getMapper().toObjectChangedDTO(result, null);
    }

    @Override
    public ObjectChangedDTO<InventoryBookDTO> update(InventoryBookDTO dto) {
        return super.update(dto);
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
            throw new ChangingObjectException("Failed to create inventory number for the item: " + inventoryBook);
        }
    }
}
