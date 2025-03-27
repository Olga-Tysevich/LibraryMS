package by.lms.libraryms.mappers;

import by.lms.libraryms.domain.RoleEnum;
import by.lms.libraryms.domain.auth.User;
import by.lms.libraryms.dto.common.UserDTO;
import by.lms.libraryms.dto.req.CreateUserDTO;
import by.lms.libraryms.dto.req.UserSearchReqDTO;
import by.lms.libraryms.services.searchobjects.UserSearchReq;
import org.mapstruct.*;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper extends ObjectMapper<User, UserDTO, UserSearchReq, UserSearchReqDTO> {

    @Override
    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "roles", source = "roleIds", qualifiedByName = "mapSetIntegerToRolesSet"),
            @Mapping(target = "telegramChatId", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "authorities", ignore = true),
            @Mapping(target = "confirmed", source = "confirmed")
    })
    User toEntity(UserDTO dto);

    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "roles", source = "roleIds", qualifiedByName = "mapSetIntegerToRolesSet"),
            @Mapping(target = "telegramChatId", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "authorities", ignore = true),
            @Mapping(target = "confirmed", ignore = true)
    })
    User toEntity(CreateUserDTO dto);

    @Mappings({
            @Mapping(target = "createdAt", source = "createdAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "updatedAt", source = "updatedAt", qualifiedByName = "mapInstantToLocalDateTime"),
            @Mapping(target = "roleIds", source = "roles", qualifiedByName = "mapRolesSetToIntegerSet"),
            @Mapping(target = "isConfirmed", source = "confirmed")
    })
    UserDTO toDTO(User entity);

    @Override
    @Mappings({
            @Mapping(target = "ids", source = "ids", qualifiedByName = "mapStringSetToObjectIdSet"),
            @Mapping(target = "createdAtFrom", source = "createdAtFrom", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "createdAtTo", source = "createdAtTo", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAtFrom", source = "updatedAtFrom", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "updatedAtTo", source = "updatedAtTo", qualifiedByName = "mapLocalDateTimeToInstant"),
            @Mapping(target = "direction", source = "direction", qualifiedByName = "mapStringToDirection"),
            @Mapping(target = "orderBy", source = "orderBy", qualifiedByName = "mapStringToOrder"),
            @Mapping(target = "fullNames", source = "fullNames", qualifiedByName = "mapSetFullNamesToMapFullNames"),
            @Mapping(target = "roles", source = "roles", qualifiedByName = "mapSetIntegerToRolesSet"),
            @Mapping(target = "addressIds", ignore = true)
    })
    UserSearchReq toSearchReq(UserSearchReqDTO searchReqDTO);

    @Named("mapSetFullNamesToMapFullNames")
    static Map<String, String> mapSetFullNamesToMapFullNames(Set<String> fullNames) {
        if (Objects.isNull(fullNames) || fullNames.isEmpty()) return null;
        Map<String, String> map = new HashMap<>(fullNames.size());
        String[] split;
        for (String fullName : fullNames) {
            split = fullName.split(" ");
            if (split.length == 2) {
                map.put(split[0], split[1]);
            } else if (split.length == 1){
                map.put(split[0], split[0]);
            }
        }
        return map;
    }

    @Named("mapSetIntegerToRolesSet")
    static EnumSet<RoleEnum> mapSetIntegerToRolesSet(Set<Integer> roles) {
        if (Objects.isNull(roles)) return null;
        return roles.stream().map(RoleEnum::fromId).collect(Collectors.toCollection(() -> EnumSet.noneOf(RoleEnum.class)));
    }

    @Named("mapRolesSetToIntegerSet")
    static Set<Integer> mapRolesSetToIntegerSet(EnumSet<RoleEnum> roles) {
        if (Objects.isNull(roles)) return null;
        return roles.stream().map(RoleEnum::getId).collect(Collectors.toSet());
    }
}
