package by.lms.libraryms.mappers;

import by.lms.libraryms.domain.inventorynumber.InventoryNumber;
import by.lms.libraryms.domain.inventorynumber.InventoryNumberElement;
import by.lms.libraryms.domain.inventorynumber.InventoryPrefixEnum;
import by.lms.libraryms.dto.resp.InventoryNumberDTO;
import by.lms.libraryms.exceptions.InvalidInventoryNumberException;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import org.mapstruct.*;
import org.springframework.lang.NonNull;

import java.util.Arrays;
import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface InventoryNumberMapper {

    @Mappings(
            @Mapping(target = "number", expression = "java(inventoryNumber.number())")
    )
    InventoryNumberDTO toDTO(InventoryNumber inventoryNumber);

    ListForPageResp<InventoryNumberDTO> toListForPageResp(ListForPageResp<InventoryNumber> list);

    default InventoryNumber toInventoryNumber(@NonNull String number) {
       try {
           String prefixChar = String.valueOf(number.charAt(0));
           InventoryPrefixEnum prefix = InventoryPrefixEnum.valueOf(prefixChar);
           String[] numbersString = number.substring(2).split(String.valueOf(prefix.getDelimiter()));
           List<InventoryNumberElement> numbers = Arrays.stream(numbersString)
                   .map(Integer::valueOf)
                   .map(InventoryNumberElement::new)
                   .toList();
           return new InventoryNumber(prefix, numbers);

       } catch (IllegalArgumentException e) {
           throw new InvalidInventoryNumberException(number);
       }

    }
}
