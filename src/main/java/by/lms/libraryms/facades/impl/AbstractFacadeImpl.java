package by.lms.libraryms.facades.impl;

import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.domain.AbstractDomainClass;
import by.lms.libraryms.dto.AbstractDTO;
import by.lms.libraryms.dto.SearchReqDTO;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.facades.AbstractFacade;
import by.lms.libraryms.mappers.ObjectMapper;
import by.lms.libraryms.services.AbstractService;
import by.lms.libraryms.services.NotificationService;
import by.lms.libraryms.services.messages.MessageService;
import by.lms.libraryms.services.searchobjects.SearchReq;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@RequiredArgsConstructor
public abstract class AbstractFacadeImpl<Entity extends AbstractDomainClass, DTO extends AbstractDTO,
        SR extends SearchReq, SRD extends SearchReqDTO,
        Service extends AbstractService<Entity, DTO, SR, SRD, Mapper>,
        MService extends MessageService<DTO>,
        Mapper extends ObjectMapper<Entity, DTO, SR, SRD>>
        implements AbstractFacade<Entity, DTO, SR, SRD, Service, MService,Mapper> {
    private final Service service;
    @Getter
    private final NotificationService<DTO> notificationService;
    @Getter
    private final MService messageService;


    @Override
    public ObjectChangedDTO<DTO> add(@NotNull DTO dto) {
        ObjectChangedDTO<DTO> result = service.add(dto);
        if (Objects.nonNull(result)) sendMessage(MessageTypeEnum.ADD, result);
        return result;
    }

    @Override
    public ObjectChangedDTO<DTO> update(@NotNull DTO dto) {
        ObjectChangedDTO<DTO> result = service.update(dto);
        if (Objects.nonNull(result)) sendMessage(MessageTypeEnum.UPDATE, result);
        return result;
    }

    @Override
    public ObjectChangedDTO<DTO> delete(@NotNull SRD searchReqDTO) {
        ObjectChangedDTO<DTO> result = service.delete(searchReqDTO);
        if (Objects.nonNull(result)) {
            result.setUpdatedAt(LocalDateTime.now());
            result.setDeletedAt(LocalDateTime.now());
            sendMessage(MessageTypeEnum.DELETE, result);
        }
        return result;
    }

    @Override
    public DTO get(@NotNull SRD searchReqDTO) {
        return service.get(searchReqDTO);
    }

    @Override
    public ListForPageDTO<DTO> getAll(@NotNull SRD searchReqDTO) {
        return service.getAll(searchReqDTO);
    }

    protected abstract String message(MessageTypeEnum type, ObjectChangedDTO<DTO> result);

    private void sendMessage(MessageTypeEnum type, ObjectChangedDTO<DTO> result) {
        if (Objects.nonNull(result)) {
            sendMessage(message(type, result));
        }
    }

    //TODO настроить библиотекаря
    private void sendMessage(String message) {
        notificationService.sendMessage(message);
    }
}
