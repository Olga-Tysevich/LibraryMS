package by.lms.libraryms.mappers;

import org.mapstruct.*;
import org.springframework.data.domain.Sort;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SearchMapper {

    @Named("mapLocalDateTimeToInstant")
    static Instant mapLocalDateTimeToInstant(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.toInstant(ZoneOffset.UTC) : null;
    }

    @Named("mapStringToDirection")
    static Sort.Direction mapStringToDirection(String direction) {
        return direction != null ? Sort.Direction.valueOf(direction.toUpperCase()) : null;
    }

    @Named("mapStringToOrder")
    static Sort.Order mapStringToOrder(String orderBy) {
        return orderBy != null ? new Sort.Order(Sort.Direction.ASC, orderBy) : null;
    }
}
