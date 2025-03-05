package by.lms.libraryms.facades;

import by.lms.libraryms.domain.AbstractDomainClass;
import by.lms.libraryms.dto.AbstractDTO;
import by.lms.libraryms.dto.SearchReqDTO;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.mappers.ObjectMapper;
import by.lms.libraryms.services.AbstractService;
import by.lms.libraryms.services.searchobjects.SearchReq;
import jakarta.validation.constraints.NotNull;

public interface AbstractFacade<Entity extends AbstractDomainClass, DTO extends AbstractDTO,
        SR extends SearchReq, SRD extends SearchReqDTO,
        Service extends AbstractService<Entity, SR>, Mapper extends ObjectMapper<Entity, DTO, SR, SRD>> {

    ObjectChangedDTO add(@NotNull DTO dto);

    ObjectChangedDTO update(@NotNull DTO dto);

    ObjectChangedDTO delete(@NotNull SRD searchReq);

    DTO get(@NotNull SRD searchReqDTO);

    ListForPageDTO<DTO> getAll(@NotNull SRD searchReqDTO);
}
