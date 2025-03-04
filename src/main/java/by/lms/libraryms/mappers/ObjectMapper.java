package by.lms.libraryms.mappers;


import by.lms.libraryms.domain.AbstractDomainClass;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

public interface ObjectMapper<T extends AbstractDomainClass> {
    @Mappings({
            @Mapping(target = "objectClass", expression = "java(entity.getClass().getSimpleName())"),
            @Mapping(target = "createdAt", source = "entity.createdAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "updatedAt", source = "entity.updatedAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "deletedAt", source = "deletedAt", qualifiedByName = "mapInstantToLocalDateTime")
    })
    ObjectChangedDTO toObjectChangedDTO(T entity, Instant deletedAt);

    @Named("mapInstantToLocalDateTime")
    static LocalDateTime mapInstantToLocalDateTime(Instant instant) {
        if (instant == null) return null;

        ZoneId zoneId = ZoneId.of(getTimeZoneByLocale(LocaleContextHolder.getLocale()));
        return LocalDateTime.ofInstant(instant, zoneId);
    }

    static String getTimeZoneByLocale(Locale locale) {
        // TODO заменить на свой источник
        if (Locale.FRANCE.equals(locale)) return "Europe/Paris";
        if (Locale.GERMANY.equals(locale)) return "Europe/Berlin";
        if (Locale.forLanguageTag("ru-RU").equals(locale)) return "Europe/Moscow";
        return "UTC";
    }

}
