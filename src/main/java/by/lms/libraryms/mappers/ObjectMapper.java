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

public interface ObjectMapper<Entity extends AbstractDomainClass, DTO extends AbstractDTO,
        SR extends SearchReq, SRD extends SearchReqDTO> extends SearchMapper {

    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapLocalDateTimeToInstant")
    })
    Entity toEntity(DTO dto);

    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapInstantToLocalDateTime")
    })
    DTO toDTO(Entity entity);

    List<DTO> toDTOList(List<Entity> entities);

    @Mappings({
            @Mapping(target = "createdAtFrom", source = "createdAtFrom", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "createdAtTo", source = "createdAtTo", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAtFrom", source = "updatedAtFrom", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAtTo", source = "updatedAtTo", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "direction", source = "direction", qualifiedByName = "mapStringToDirection"),
            @Mapping(target = "orderBy", source = "orderBy", qualifiedByName = "mapStringToOrder")
    })
    SR toSearchReq(SRD searchReqDTO);

    @Mappings({
            @Mapping(target = "createdAt", source = "createdAtFrom", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAt", source = "updatedAtFrom", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "direction", source = "direction", qualifiedByName = "mapStringToDirection"),
            @Mapping(target = "orderBy", source = "orderBy", qualifiedByName = "mapStringToOrder")
    })
    DTO searchReqToDTO(SRD searchReqDTO);

    @Mappings({
            @Mapping(target = "objectClass", expression = "java(entity.getClass().getSimpleName())"),
            @Mapping(target = "createdAt", source = "entity.createdAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "updatedAt", source = "deletedAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "deletedAt", source = "deletedAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "object", expression = "java(toDTO(entity)")
    })
    ObjectChangedDTO<DTO> toObjectChangedDTO(Entity entity, Instant deletedAt);

    ListForPageDTO<DTO> toListForPageDTO(ListForPageResp<Entity> list);

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
