package by.lms.libraryms.services;

import by.lms.libraryms.domain.AbstractDomainClass;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import by.lms.libraryms.services.searchobjects.SearchReq;
import jakarta.validation.constraints.NotNull;

public interface AbstractService<Entity extends AbstractDomainClass, SR extends SearchReq> {
    Entity add(@NotNull Entity entity);
    Entity update(@NotNull Entity entity);
    Entity delete(@NotNull SR searchReq);
    Entity get(@NotNull SR searchReq);
    ListForPageResp<Entity> getAll(@NotNull SR searchReq);
}
