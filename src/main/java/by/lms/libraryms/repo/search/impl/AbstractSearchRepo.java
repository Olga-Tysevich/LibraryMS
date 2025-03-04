package by.lms.libraryms.repo.search.impl;

import by.lms.libraryms.repo.search.SearchRepo;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import by.lms.libraryms.services.searchobjects.SearchReq;
import by.lms.libraryms.utils.Constants;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Objects;

public abstract class AbstractSearchRepo<T, R extends SearchReq> implements SearchRepo<T, R> {

    protected ListForPageResp<T> findList(@NotNull MongoTemplate mongoTemplate,
                                          @NotNull Query query,
                                          @NotNull Class<T> entityClass,
                                          R searchReq) {
        List<T> results = mongoTemplate.find(query, entityClass);
        long totalElements = mongoTemplate.count(query.limit(0).skip(0), entityClass);

        int totalPages = (int) Math.ceil((double) totalElements / searchReq.getPageSize());
        if (totalPages == 0) totalPages = 1;

        int nextPageIndex = searchReq.getPageNum() + 1 < totalPages ? searchReq.getPageNum() + 1 : 0;
        int previousPageIndex = searchReq.getPageNum() > 0 ? searchReq.getPageNum() - 1 : totalPages - 1;

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

        if (Objects.nonNull(request.getId())) {
            query.addCriteria(Criteria.where("id").is(request.getId()));
        }

        if (Objects.nonNull(request.getCreatedAt())) {
            query.addCriteria(Criteria.where("createdAt").gte(request.getCreatedAt()));
        }

        if (Objects.nonNull(request.getUpdatedAt())) {
            query.addCriteria(Criteria.where("updatedAt").gte(request.getUpdatedAt()));
        }

        if (Objects.nonNull(request.getPageNum())) {
            int pageSize = (request.getPageSize() != null ? request.getPageSize() : Constants.DEFAULT_PAGE_SIZE);
            int skip = (request.getPageNum() - 1) * pageSize;
            query.skip(skip);
            query.limit(pageSize);
        }

        if (Objects.nonNull(request.getOrderBy()) && Objects.nonNull(request.getDirection())) {
            query.with(Sort.by(request.getDirection(), request.getOrderBy().getProperty()));
        }

        return query;
    }

}
