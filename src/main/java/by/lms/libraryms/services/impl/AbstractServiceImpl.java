package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.AbstractDomainClass;
import by.lms.libraryms.dto.AbstractDTO;
import by.lms.libraryms.dto.SearchReqDTO;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.exceptions.ChangingObjectException;
import by.lms.libraryms.exceptions.ObjectNotFound;
import by.lms.libraryms.mappers.ObjectMapper;
import by.lms.libraryms.repo.search.SearchRepo;
import by.lms.libraryms.services.AbstractService;
import by.lms.libraryms.services.searchobjects.SearchReq;
import by.lms.libraryms.utils.ParamsManager;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import static by.lms.libraryms.utils.Constants.OBJECTS_NOT_FOUND;

@RequiredArgsConstructor
public abstract class AbstractServiceImpl<
        Entity extends AbstractDomainClass, DTO extends AbstractDTO,
        SR extends SearchReq, SRD extends SearchReqDTO,
        Repo extends MongoRepository<Entity, String>, SRepo extends SearchRepo<Entity, SR>,
        Mapper extends ObjectMapper<Entity, DTO, SR, SRD>
        >
        implements AbstractService<Entity, DTO, SR, SRD, Mapper> {

    private final Repo repository;
    private final SRepo searchRepo;
    private final Mapper mapper;


    @Override
    public ObjectChangedDTO<DTO> add(@NotNull DTO dto) {
        return save(dto);
    }

    @Override
    public ObjectChangedDTO<DTO> delete(SRD searchReqDTO) {
        SR searchReq = mapper.toSearchReq(searchReqDTO);
        Entity result = find(searchReq);
        boolean isDeleted = searchRepo.delete(searchReq);
        if (!isDeleted) {
            throw new ChangingObjectException();
        }
        return Optional.of(result)
                .map(entity -> mapper.toObjectChangedDTO(entity, Instant.now()))
                .orElseThrow(ChangingObjectException::new);
    }

    @Override
    public ObjectChangedDTO<DTO> update(DTO dto) {
        return save(dto);
    }

    @Override
    public DTO get(SRD searchReqDTO) {
        SR searchReq = mapper.toSearchReq(searchReqDTO);
        return mapper.toDTO(find(searchReq));
    }

    @Override
    public ListForPageDTO<DTO> getAll(SRD searchReqDTO) {
        SR searchReq = mapper.toSearchReq(searchReqDTO);
        return mapper.toListForPageDTO(searchRepo.findList(searchReq));
    }

    private ObjectChangedDTO<DTO> save(DTO dto) {
        return Optional.of(dto)
                .map(mapper::toEntity)
                .map(repository::save)
                .map(entity -> mapper.toObjectChangedDTO(entity, null))
                .orElseThrow(ChangingObjectException::new);
    }

    private Entity find(SR searchReq) {
        Entity result = searchRepo.find(searchReq).getFirst();

        if (Objects.isNull(result)) {
            throw new ObjectNotFound(
                    String.format(OBJECTS_NOT_FOUND,
                            clazz().getSimpleName(),
                            ParamsManager.getParamsAsString(searchReq)
                    )
            );
        }

        return result;
    }

    @Override
    public List<DTO> getAllByIds(Set<String> idList) {
        return mapper.toDTOList(repository.findAllById(idList));
    }

    protected abstract Class<Entity> clazz();
}
