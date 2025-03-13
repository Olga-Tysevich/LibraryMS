package by.lms.libraryms.mappers;

import by.lms.libraryms.domain.InventoryNumber;
import by.lms.libraryms.domain.StockBook;
import by.lms.libraryms.dto.req.StockBookDTO;
import by.lms.libraryms.dto.req.StockBookSearchReqDTO;
import by.lms.libraryms.services.searchobjects.StockBookSearchReq;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.mapstruct.*;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface StockBookMapper extends ObjectMapper<StockBook, StockBookDTO,
        StockBookSearchReq, StockBookSearchReqDTO>  {

    @Override
    @Mappings({
            @Mapping(target = "createdAtFrom", source = "createdAtFrom", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "createdAtTo", source = "createdAtTo", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAtFrom", source = "updatedAtFrom", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAtTo", source = "updatedAtTo", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "direction", source = "direction", qualifiedByName = "mapStringToDirection"),
            @Mapping(target = "orderBy", source = "orderBy", qualifiedByName = "mapStringToOrder"),
            @Mapping(target = "authorIds", source = "authorIds", qualifiedByName = "mapStringSetToObjectIdSet"),
            @Mapping(target = "genreIds", source = "genreIds", qualifiedByName = "mapStringSetToObjectIdSet"),
            @Mapping(target = "bookIds", source = "bookIds", qualifiedByName = "mapStringSetToObjectIdSet"),
            @Mapping(target = "numberOfBooks", source = "numberOfBooks", qualifiedByName = "mapStringIntegerMapToObjectIdIntegerMap"),
            @Mapping(target = "inventoryNumbers", ignore = true),
            @Mapping(target = "dateOfReceiptFrom", source = "dateOfReceiptFrom", qualifiedByName = "mapLocalDateToInstant"),
            @Mapping(target = "dateOfReceiptTo", source = "dateOfReceiptTo", qualifiedByName = "mapLocalDateToInstant")
    })
    StockBookSearchReq toSearchReq(StockBookSearchReqDTO searchReqDTO);

    @AfterMapping
    default void mapInventoryNumbers(@MappingTarget StockBookSearchReq target, @NotNull StockBookSearchReqDTO source,
                                     @Context InventoryNumberMapper inventoryNumberMapper) {
        if (source.getInventoryNumbers() == null || source.getInventoryNumbers().isEmpty()) {
            target.setInventoryNumbers(Collections.emptySet());
            return;
        }
        Set<InventoryNumber> inventoryIds = source.getInventoryNumbers().stream()
                .map(inventoryNumberMapper::toInventoryNumber)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        target.setInventoryNumbers(inventoryIds);
    }

    @Named("mapStringIntegerMapToObjectIdIntegerMap")
    default Map<ObjectId, Integer> mapStringIntegerMapToObjectIdIntegerMap(Map<String, Integer> map) {
        if (Objects.isNull(map)) {
            return null;
        }
        return map.entrySet().stream()
                .collect(Collectors.toMap(entry -> new ObjectId(entry.getKey()), Map.Entry::getValue));
    }

}

