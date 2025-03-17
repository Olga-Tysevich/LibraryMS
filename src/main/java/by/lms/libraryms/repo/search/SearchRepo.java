package by.lms.libraryms.repo.search;

import by.lms.libraryms.domain.AbstractDomainClass;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import by.lms.libraryms.services.searchobjects.SearchReq;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Interface for deleting entities by search request
 * @param <Entity> the entity class
 * @param <SR> the search request class
 */
public interface SearchRepo<Entity extends AbstractDomainClass, SR extends SearchReq> {
    long delete(@NotNull SR searchReq);
    List<Entity> find(@NotNull SR searchReq);
    ListForPageResp<Entity> findList(@NotNull SR searchReq);
}
