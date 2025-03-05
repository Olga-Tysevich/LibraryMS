package by.lms.libraryms.mappers;


import by.lms.libraryms.domain.AbstractDomainClass;
import by.lms.libraryms.dto.AbstractDTO;
import by.lms.libraryms.dto.SearchReqDTO;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import by.lms.libraryms.services.searchobjects.SearchReq;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;

public interface ObjectMapper<E extends AbstractDomainClass, D extends AbstractDTO,
        SR extends SearchReq, SRD extends SearchReqDTO> extends SearchMapper {

    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapLocalDateTimeToInstant")
    })
    E toEntity(D dto);

    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapInstantToLocalDateTime")
    })
    D toDTO(E entity);

    List<D> toDTOList(List<E> entities);

    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "direction", source = "direction", qualifiedByName = "mapStringToDirection"),
            @Mapping(target = "orderBy", source = "orderBy", qualifiedByName = "mapStringToOrder")
    })
    SR toSearchReq(SRD dto);

    @Mappings({
            @Mapping(target = "objectClass", expression = "java(entity.getClass().getSimpleName())"),
            @Mapping(target = "createdAt", source = "entity.createdAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "updatedAt", source = "entity.updatedAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "deletedAt", source = "deletedAt", qualifiedByName = "mapInstantToLocalDateTime")
    })
    ObjectChangedDTO toObjectChangedDTO(E entity, Instant deletedAt);

    ListForPageDTO<D> toListForPageDTO(ListForPageResp<E> list);

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
