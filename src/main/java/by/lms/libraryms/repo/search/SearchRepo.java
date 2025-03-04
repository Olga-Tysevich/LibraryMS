package by.lms.libraryms.repo.search;

import by.lms.libraryms.services.searchobjects.ListForPageResp;
import by.lms.libraryms.services.searchobjects.SearchReq;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Interface for deleting entities by search request
 * @param <T> the entity class
 * @param <R> the search request class
 */
public interface SearchRepo<T, R extends SearchReq> {
    boolean delete(@NotNull R searchReq);
    List<T> find(@NotNull R searchReq);
    ListForPageResp<T> findList(@NotNull R searchReq);
}
