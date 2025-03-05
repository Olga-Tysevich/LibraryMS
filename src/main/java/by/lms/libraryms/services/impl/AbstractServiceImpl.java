package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.AbstractDomainClass;
import by.lms.libraryms.exceptions.ChangingObjectException;
import by.lms.libraryms.exceptions.ObjectNotFound;
import by.lms.libraryms.repo.search.SearchRepo;
import by.lms.libraryms.services.AbstractService;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import by.lms.libraryms.services.searchobjects.SearchReq;
import by.lms.libraryms.utils.ParamsManager;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Objects;

import static by.lms.libraryms.utils.Constants.OBJECTS_NOT_FOUND;

@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractServiceImpl<Entity extends AbstractDomainClass, SR extends SearchReq,
        Repo extends MongoRepository<Entity, String>, SRepo extends SearchRepo<Entity, SR>>
        implements AbstractService<Entity, SR> {
    private Repo repository;
    private SRepo searchRepo;


    @Override
    public Entity add(Entity entity) {
        return repository.save(entity);
    }

    @Override
    public Entity delete(SR searchReq) {
        Entity author = find(searchReq);
        boolean isDeleted = searchRepo.delete(searchReq);
        if (!isDeleted) {
            throw new ChangingObjectException();
        }
        return author;
    }

    @Override
    public Entity update(Entity author) {
        return repository.save(author);
    }

    @Override
    public Entity get(SR searchReq) {
        return find(searchReq);
    }

    @Override
    public ListForPageResp<Entity> getAll(SR searchReq) {
        return searchRepo.findList(searchReq);
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

    protected abstract Class<Entity> clazz();
}
