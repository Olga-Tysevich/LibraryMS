package by.lms.libraryms.repo.search.impl;

import by.lms.libraryms.domain.AbstractDomainClass;
import by.lms.libraryms.exceptions.ChangingObjectException;
import by.lms.libraryms.repo.search.SearchRepo;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import by.lms.libraryms.services.searchobjects.SearchReq;
import by.lms.libraryms.utils.Constants;
import com.mongodb.client.result.DeleteResult;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public abstract class AbstractSearchRepo<Entity extends AbstractDomainClass, SR extends SearchReq> implements SearchRepo<Entity, SR> {
    private final MongoTemplate mongoTemplate;

    //TODO доработать
    public long delete(@NotNull SR searchReq) {

        Query query = addParams(query(searchReq), searchReq);

        List<Entity> entities = mongoTemplate().find(query, clazz());
        List<Entity> impossibleToDelete = new ArrayList<>();
        List<ObjectId> forDelete = new ArrayList<>(entities.stream()
                .map(Entity::getId)
                .map(ObjectId::new)
                .toList());

        List<ObjectId> withReferences = new ArrayList<>(List.of(new ObjectId()));
        for (Entity entity : entities) {
            withReferences.set(0, new ObjectId(entity.getId()));
            if (hasReferences(withReferences)) {
                impossibleToDelete.add(entity);
                forDelete.remove(new ObjectId(entity.getId()));
            }
        }

        query = new Query(Criteria.where("_id").in(forDelete));
        DeleteResult result = mongoTemplate().remove(query, clazz());

        if (!impossibleToDelete.isEmpty()) {
            throw new ChangingObjectException("Impossible to delete objects: " + impossibleToDelete + ". Need to remove related objects!");
        }

        return result.getDeletedCount();
    }


    public List<Entity> find(@NotNull SR searchReq) {
        Query query = addParams(query(searchReq), searchReq);

        return mongoTemplate().find(query, clazz());
    }


    public ListForPageResp<Entity> findList(@NotNull SR searchReq) {
        Query query = addParams(query(searchReq), searchReq);

        return findList(mongoTemplate(), query, clazz(), searchReq);
    }

    protected ListForPageResp<Entity> findList(@NotNull MongoTemplate mongoTemplate,
                                               @NotNull Query query,
                                               @NotNull Class<Entity> entityClass,
                                               @NotNull SR searchReq) {
        List<Entity> results = mongoTemplate.find(query, entityClass);
        long totalElements = mongoTemplate.count(query.limit(0).skip(0), entityClass);

        checkRequest(searchReq);
        int totalPages = (int) Math.ceil((double) totalElements / searchReq.getPageSize());

        int nextPageIndex = searchReq.getPageNum() + 1 < totalPages ? searchReq.getPageNum() + 1 : 0;
        int totalPagesIndex = totalPages == 0 ? totalPages : 1;
        int previousPageIndex = searchReq.getPageNum() > 0 ? searchReq.getPageNum() - 1 : totalPagesIndex;

        return ListForPageResp.<Entity>builder()
                .objectsClass(entityClass.getSimpleName())
                .list(results)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .nextPageIndex(nextPageIndex)
                .previousPageIndex(previousPageIndex)
                .build();
    }


    protected Query query(@NotNull SR request) {
        Query query = new Query();
        checkRequest(request);

        if (Objects.nonNull(request.getIds())) {
            query.addCriteria(Criteria.where("id").is(request.getIds()));
        }

        if (Objects.nonNull(request.getCreatedAtFrom())) {
            if (Objects.nonNull(request.getCreatedAtTo())
                    && request.getCreatedAtTo().isAfter(request.getCreatedAtFrom())) {
                query.addCriteria(Criteria.where("createdAt").gte(request.getCreatedAtFrom())
                        .and("createdAt").lt(request.getCreatedAtTo()));
            } else {
                query.addCriteria(Criteria.where("createdAt").gte(request.getCreatedAtFrom()));
            }
        }

        if (Objects.nonNull(request.getUpdatedAtFrom())) {
            if (Objects.nonNull(request.getUpdatedAtTo())
                    && request.getCreatedAtTo().isAfter(request.getUpdatedAtFrom())) {
                query.addCriteria(Criteria.where("updatedAt").gte(request.getUpdatedAtFrom())
                        .and("updatedAt").lt(request.getUpdatedAtTo()));
            } else {
                query.addCriteria(Criteria.where("updatedAt").gte(request.getCreatedAtFrom()));
            }
        }

        if (Objects.nonNull(request.getPageNum())) {
            int skip = (request.getPageNum() - 1) * request.getPageSize();
            query.skip(skip);
            query.limit(request.getPageSize());
        }

        if (Objects.nonNull(request.getOrderBy()) && Objects.nonNull(request.getDirection())) {
            query.with(Sort.by(request.getDirection(), request.getOrderBy().getProperty()));
        }

        return query;
    }

    private void checkRequest(@NotNull SR request) {
        request.setPageSize(Objects.requireNonNullElse(request.getPageSize(), Constants.DEFAULT_PAGE_SIZE));
        request.setPageNum(Objects.requireNonNullElse(request.getPageNum(), 0));
        request.setDirection(Objects.requireNonNullElse(request.getDirection(), Sort.Direction.ASC));
        request.setOrderBy(Objects.requireNonNullElse(request.getOrderBy(), Sort.Order.by("createdAt")));
    }

    protected MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

    protected abstract Query addParams(@NotNull Query query, @NotNull SR searchReq);

    protected abstract Class<Entity> clazz();

    protected abstract boolean hasReferences(List<ObjectId> objectIds);

    protected boolean hasReferences(String relatedTableName, String relatedFieldName, List<ObjectId> objectId) {
        Criteria criteria = objectId.size() == 1? Criteria.where(relatedFieldName).is(objectId.getFirst()) :
                Criteria.where(relatedFieldName).in(objectId);
        return mongoTemplate().count(
                new Query(criteria),
                relatedTableName
        ) > 0;
    }
}
