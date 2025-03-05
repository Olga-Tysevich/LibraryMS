package by.lms.libraryms.repo.search.impl;

import by.lms.libraryms.repo.search.SearchRepo;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import by.lms.libraryms.services.searchobjects.SearchReq;
import by.lms.libraryms.utils.Constants;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public abstract class AbstractSearchRepo<T, R extends SearchReq> implements SearchRepo<T, R> {
    private final MongoTemplate mongoTemplate;

    protected ListForPageResp<T> findList(@NotNull MongoTemplate mongoTemplate,
                                          @NotNull Query query,
                                          @NotNull Class<T> entityClass,
                                          R searchReq) {
        List<T> results = mongoTemplate.find(query, entityClass);
        long totalElements = mongoTemplate.count(query.limit(0).skip(0), entityClass);

        checkRequest(searchReq);
        int totalPages = (int) Math.ceil((double) totalElements / searchReq.getPageSize());

        int nextPageIndex = searchReq.getPageNum() + 1 < totalPages ? searchReq.getPageNum() + 1 : 0;
        int totalPagesIndex = totalPages == 0 ? totalPages : 1;
        int previousPageIndex = searchReq.getPageNum() > 0 ? searchReq.getPageNum() - 1 : totalPagesIndex;

        return ListForPageResp.<T>builder()
                .objectsClass(entityClass.getSimpleName())
                .list(results)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .nextPageIndex(nextPageIndex)
                .previousPageIndex(previousPageIndex)
                .build();
    }


    protected Query query(@NotNull R request) {
        Query query = new Query();
        checkRequest(request);

        if (Objects.nonNull(request.getId())) {
            query.addCriteria(Criteria.where("id").is(request.getId()));
        }

        if (Objects.nonNull(request.getCreatedAtFrom())) {
            query.addCriteria(Criteria.where("createdAt").gte(request.getCreatedAtFrom()));
        }

        if (Objects.nonNull(request.getUpdatedAtFrom())) {
            query.addCriteria(Criteria.where("updatedAt").gte(request.getUpdatedAtFrom()));
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

    private void checkRequest(@NotNull R request) {
        request.setPageSize(Objects.requireNonNullElse(request.getPageSize(), Constants.DEFAULT_PAGE_SIZE));
        request.setPageNum(Objects.requireNonNullElse(request.getPageNum(), 0));
        request.setDirection(Objects.requireNonNullElse(request.getDirection(), Sort.Direction.ASC));
        request.setOrderBy(Objects.requireNonNullElse(request.getOrderBy(), Sort.Order.by("createdAt")));
    }

    protected MongoTemplate mongoTemplate() {
        return mongoTemplate;
    }

}
