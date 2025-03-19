package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.AbstractDomainClass;
import by.lms.libraryms.dto.AbstractDTO;
import by.lms.libraryms.dto.SearchReqDTO;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.dto.resp.ObjectListChangedDTO;
import by.lms.libraryms.exceptions.ChangingObjectException;
import by.lms.libraryms.exceptions.ObjectDoesNotExistException;
import by.lms.libraryms.mappers.ObjectMapper;
import by.lms.libraryms.repo.search.SearchRepo;
import by.lms.libraryms.services.AbstractService;
import by.lms.libraryms.services.searchobjects.SearchReq;
import by.lms.libraryms.utils.Constants;
import by.lms.libraryms.utils.ParamsManager;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

import static by.lms.libraryms.utils.Constants.OBJECTS_NOT_FOUND;

@RequiredArgsConstructor
public abstract class AbstractServiceImpl<
        Entity extends AbstractDomainClass, DTO extends AbstractDTO,
        SR extends SearchReq, SRD extends SearchReqDTO,
        Repo extends MongoRepository<Entity, String>, SRepo extends SearchRepo<Entity, SR>,
        Mapper extends ObjectMapper<Entity, DTO, SR, SRD>
        >
        implements AbstractService<Entity, DTO, SR, SRD, Mapper> {

    @Getter(AccessLevel.PACKAGE)
    private final Repo repository;

    @Override
    public DTO findById(String id) {
        Entity entity = repository.findById(id).orElseThrow(ObjectDoesNotExistException::new);
        return mapper.toDTO(entity);
    }

    @Getter(AccessLevel.PACKAGE)
    private final SRepo searchRepo;
    @Getter(AccessLevel.PACKAGE)
    private final Mapper mapper;


    @Transactional
    @Override
    public ObjectChangedDTO<DTO> add(@NotNull DTO dto) {
        return save(dto);
    }

    @Transactional
    @Override
    public ObjectListChangedDTO<DTO> delete(SRD searchReqDTO) {
        if (Objects.isNull(searchReqDTO.getIds())
                || searchReqDTO.getIds().isEmpty()) throw new IllegalArgumentException(Constants.EMPTY_ID_MESSAGE);

        SR searchReq = mapper.toSearchReq(searchReqDTO);
        List<Entity> result = find(searchReq);
        long isDeleted = searchRepo.delete(searchReq);

        if (result.size() != isDeleted) {
            List<Entity> notDeleted = find(searchReq);
            throw new ChangingObjectException("Failed to delete objects: " + notDeleted);
        }

        List<ObjectChangedDTO<DTO>> list = result.stream()
                .map(entity -> mapper.toObjectChangedDTO(entity, Instant.now()))
                .toList();

        return new ObjectListChangedDTO<>(list);
    }

    @Transactional
    @Override
    public ObjectChangedDTO<DTO> update(DTO dto) {
        return save(dto);
    }

    @Override
    public DTO get(SRD searchReqDTO) {
        SR searchReq = mapper.toSearchReq(searchReqDTO);
        return mapper.toDTO(find(searchReq).getFirst());
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

    private List<Entity> find(SR searchReq) {
        List<Entity> result = searchRepo.find(searchReq);

        if (Objects.isNull(result)) {
            throw new ObjectDoesNotExistException(
                    String.format(OBJECTS_NOT_FOUND,
                            clazz().getSimpleName(),
                            ParamsManager.getParamsAsString(searchReq)
                    )
            );
        }

        return result;
    }

    @Override
    public List<DTO> findAllByIds(Set<String> idList) {
        return mapper.toDTOList(repository.findAllById(idList));
    }

    protected abstract Class<Entity> clazz();
}
