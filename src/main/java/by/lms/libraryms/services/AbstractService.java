package by.lms.libraryms.services;

import by.lms.libraryms.domain.AbstractDomainClass;
import by.lms.libraryms.dto.AbstractDTO;
import by.lms.libraryms.dto.SearchReqDTO;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.mappers.ObjectMapper;
import by.lms.libraryms.services.searchobjects.SearchReq;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

public interface AbstractService<Entity extends AbstractDomainClass, DTO extends AbstractDTO,
        SR extends SearchReq, SRD extends SearchReqDTO,
        Mapper extends ObjectMapper<Entity, DTO, SR, SRD>> {

    DTO findById(String id);

    ObjectChangedDTO<DTO> add(@NotNull DTO dto);

    ObjectChangedDTO<DTO> update(@NotNull DTO dto);

    ObjectChangedDTO<DTO> delete(@NotNull SRD searchReqDTO);

    DTO get(@NotNull SRD searchReqDTO);

    ListForPageDTO<DTO> getAll(@NotNull SRD searchReqDTO);

    List<DTO> getAllByIds(@NotNull Set<String> idList);
}
