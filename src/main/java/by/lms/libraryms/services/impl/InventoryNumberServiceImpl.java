package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.Inventory;
import by.lms.libraryms.domain.InventoryNumber;
import by.lms.libraryms.domain.InventoryNumberElement;
import by.lms.libraryms.domain.InventoryPrefixEnum;
import by.lms.libraryms.dto.resp.InventoryNumberDTO;
import by.lms.libraryms.exceptions.BindingInventoryNumberException;
import by.lms.libraryms.exceptions.UnbindInventoryNumberException;
import by.lms.libraryms.mappers.InventoryNumberMapper;
import by.lms.libraryms.repo.InventoryNumberRepo;
import by.lms.libraryms.services.InventoryNumberService;
import by.lms.libraryms.services.searchobjects.InventoryNumberSearchReq;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InventoryNumberServiceImpl implements InventoryNumberService {
    private final InventoryNumberRepo inventoryNumberRepo;
    private final InventoryNumberMapper mapper;
    @Value("${inventory.number.permissibleError}")
    private int permissibleError;
    @Value("${inventory.number.firstVal}")
    private int firstVal;

    @Transactional
    @Override
    public Inventory add(@NonNull Inventory relatedObject) throws BindingInventoryNumberException {
        try {
            InventoryPrefixEnum prefix = InventoryPrefixEnum.MIN;
            InventoryNumber result = inventoryNumberRepo.findLastNumber(prefix);

            if (Objects.isNull(result)) {
                InventoryNumberElement number = new InventoryNumberElement(firstVal);
                result = new InventoryNumber(prefix, number);
            } else if (Objects.nonNull(result.getRelatedId())){
                result = createNewNumber(result);
            }

            result.setRelatedId(new ObjectId(relatedObject.getId()));
            relatedObject.setInventoryNumberId(new ObjectId(result.getId()));
            inventoryNumberRepo.save(result);
            return relatedObject;
        } catch (Exception e) {
            throw new BindingInventoryNumberException(relatedObject, e.getLocalizedMessage());
        }
    }

    @Transactional
    @Override
    public void unbind(@NonNull Inventory relatedObject) throws UnbindInventoryNumberException {
        try {
            InventoryNumber result = inventoryNumberRepo.findById(relatedObject.getInventoryNumberId()).orElseThrow();
            LocalDate updateDate = result.getUpdatedAt().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate now = LocalDate.now(ZoneId.systemDefault());

            if (!updateDate.equals(now)) {
                throw new UnbindInventoryNumberException("The unbinding period has expired! Inventory Number: " + result
                        + ", Related Id: " + result.getRelatedId());
            }

            ObjectId relatedId = new ObjectId(relatedObject.getId());
            if (!relatedId.equals(result.getRelatedId())) {
                throw new UnbindInventoryNumberException("The inventory number is linked to another object! Inventory Number: " + result
                        + ", related Id: " + result.getRelatedId() + ", the inventory number is linked to another object id: " + relatedId);
            }

            result.setRelated(false);
            result.setRelatedId(null);
            inventoryNumberRepo.save(result);
        } catch (Exception e) {
            throw new UnbindInventoryNumberException(e.getMessage());
        }
    }

    @Override
    public InventoryNumber dispose(@NonNull InventoryNumber number) {
        if (Objects.isNull(number.getDisposedDate())) {
            throw new IllegalArgumentException(String.format(
                    "Incorrect write-off of the number: %s!The date of write-off of the inventory number must be indicated!",
                    number));
        }
        LocalDate disposedDate = number.getDisposedDate();
        LocalDate today = LocalDate.now();

        if (disposedDate.isAfter(today) || disposedDate.isBefore(today.minusDays(permissibleError))) {
            throw new IllegalArgumentException(String.format(
                    "Incorrect write-off of the number: %s! The disposed date must be the current date or not more than %s days earlier than today!",
                    number, permissibleError));
        }

        number.setIsDisposedOf(true);
        return inventoryNumberRepo.save(number);
    }

    @Override
    public InventoryNumberDTO get(@NonNull String number) {
        InventoryNumber searchEl = mapper.toInventoryNumber(number);
        return mapper.toDTO(searchEl);
    }

    @Override
    public ListForPageResp<InventoryNumberDTO> getAll(@NonNull InventoryNumberSearchReq searchReq) {
        ListForPageResp<InventoryNumber> numbersForPage = inventoryNumberRepo.findList(searchReq);
        return mapper.toListForPageResp(numbersForPage);
    }

    private InventoryNumber createNewNumber(InventoryNumber lastNumber) {
        List<InventoryNumberElement> numbers = lastNumber.getNumbers();
        InventoryNumberElement nextNumber = new InventoryNumberElement(numbers.getLast().number() + 1);
        List<InventoryNumberElement> newNumbersList = new ArrayList<>(numbers);
        newNumbersList.removeLast();
        newNumbersList.addLast(nextNumber);
        InventoryNumberElement[] newNumbers = newNumbersList.toArray(new InventoryNumberElement[0]);
        return new InventoryNumber(lastNumber.getPrefix(), nextNumber, newNumbers);
    }
}
