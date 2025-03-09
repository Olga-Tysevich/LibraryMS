package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.InventoryNumber;
import by.lms.libraryms.domain.InventoryPrefixEnum;
import by.lms.libraryms.dto.resp.InventoryNumberDTO;
import by.lms.libraryms.mappers.InventoryNumberMapper;
import by.lms.libraryms.repo.InventoryNumberRepo;
import by.lms.libraryms.services.InventoryNumberService;
import by.lms.libraryms.services.searchobjects.InventoryNumberSearchReq;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InventoryNumberServiceImpl implements InventoryNumberService {
    private final InventoryNumberRepo inventoryNumberRepo;
    private final InventoryNumberMapper mapper;
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

    @Override
    public InventoryNumberDTO get(String number) {
        InventoryNumber searchEl = mapper.toInventoryNumber(number);
        return mapper.toDTO(searchEl);
    }

    @Override
    public ListForPageResp<InventoryNumberDTO> getAll(InventoryNumberSearchReq searchReq) {
        ListForPageResp<InventoryNumber> numbersForPage = inventoryNumberRepo.findList(searchReq);
        return mapper.toListForPageResp(numbersForPage);
    }
}
