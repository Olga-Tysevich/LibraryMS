package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.InventoryNumber;
import by.lms.libraryms.domain.InventoryPrefixEnum;
import by.lms.libraryms.repo.InventoryNumberRepo;
import by.lms.libraryms.services.InventoryNumberService;
import by.lms.libraryms.services.searchobjects.InventoryNumberSearchReq;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InventoryNumberServiceImpl implements InventoryNumberService {
    private final InventoryNumberRepo inventoryNumberRepo;
    @Value("${inventory.number.permissibleError}")
    private int permissibleError;

    @Transactional
    @Override
    public InventoryNumber add(InventoryPrefixEnum prefix) {
        return inventoryNumberRepo.createNewNumber(prefix);
    }

    @Override
    public InventoryNumber dispose(InventoryNumber number) {
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

    //TODO сделать маппер для преобразования, принимать в виде стринга весь номер и потом разбивать его в объект и искать по нему
    @Override
    public String get(String id) {
        List<InventoryNumber> result = inventoryNumberRepo.find(
                InventoryNumberSearchReq.builder()
                        .id(List.of(id))
                        .build()
        );
        return !result.isEmpty() ? result.getFirst().number() : null;
    }


    //TODO сделать маппер для преобразования
    @Override
    public ListForPageResp<String> getAll(InventoryNumberSearchReq searchReq) {
        ListForPageResp<InventoryNumber> numbersForPage = inventoryNumberRepo.findList(searchReq);
        return null;
    }
}
