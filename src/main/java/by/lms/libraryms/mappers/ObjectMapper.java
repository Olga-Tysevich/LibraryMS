package by.lms.libraryms.mappers;


import by.lms.libraryms.domain.AbstractDomainClass;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;

public interface ObjectMapper<T extends AbstractDomainClass> {
    @Mappings({
            @Mapping(target = "objectClass", expression = "java(entity.getClass().getSimpleName())"),
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "deletedAt", source = "deletedAt", qualifiedByName = "mapInstantToLocalDateTime")
    })
    ObjectChangedDTO toObjectChangedDTO(T entity, Instant deletedAt, @Context Locale locale);

    @Named("mapInstantToLocalDateTime")
    static LocalDateTime mapInstantToLocalDateTime(Instant instant, @Context Locale locale) {
        if (instant == null) return null;
        if (Objects.nonNull(locale)) {
            ZoneId zoneId = ZoneId.of(getTimeZoneByLocale(locale));
            return LocalDateTime.ofInstant(instant, zoneId);
        }
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    static String getTimeZoneByLocale(Locale locale) {
        // TODO заменить на свой источник
        if (Locale.FRANCE.equals(locale)) return "Europe/Paris";
        if (Locale.GERMANY.equals(locale)) return "Europe/Berlin";
        if (Locale.forLanguageTag("ru-RU").equals(locale)) return "Europe/Moscow";
        return "UTC";
    }

}
