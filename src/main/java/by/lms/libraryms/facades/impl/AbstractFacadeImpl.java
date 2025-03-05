package by.lms.libraryms.facades.impl;

import by.lms.libraryms.conf.i18n.MessageConf;
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
import by.lms.libraryms.services.searchobjects.SearchReq;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractFacadeImpl<Entity extends AbstractDomainClass, DTO extends AbstractDTO,
        SR extends SearchReq, SRD extends SearchReqDTO,
        Service extends AbstractService<Entity, DTO, SR, SRD, Mapper>, Mapper extends ObjectMapper<Entity, DTO, SR, SRD>>
        implements AbstractFacade<Entity, DTO, SR, SRD, Service, Mapper> {
    private Mapper mapper;
    private Service service;
    @Getter
    private NotificationService<DTO> notificationService;
    @Getter
    private MessageConf messageConf;


    @Override
    public ObjectChangedDTO<DTO> add(@NotNull DTO dto) {
        ObjectChangedDTO<DTO> result = service.add(dto);
        if (Objects.nonNull(result)) sendMessage(MessageTypeEnum.ADD, result, dto);
        return result;
    }

    @Override
    public ObjectChangedDTO<DTO> update(@NotNull DTO dto) {
        ObjectChangedDTO<DTO> result = service.update(dto);
        if (Objects.nonNull(result)) sendMessage(MessageTypeEnum.UPDATE, result, dto);
        return result;
    }

    @Override
    public ObjectChangedDTO<DTO> delete(@NotNull SRD searchReqDTO) {
        ObjectChangedDTO<DTO> result = service.delete(searchReqDTO);
        if (Objects.nonNull(result)) sendMessage(MessageTypeEnum.DELETE, result, buildDTOForReport(searchReqDTO));
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

    protected abstract Map<MessageTypeEnum, String> getMessages();

    protected abstract Object[] getArgs(DTO dto);

    private DTO buildDTOForReport(SRD searchReqDTO) {
        return mapper.searchReqToDTO(searchReqDTO);
    }

    private String getMessagePattern(MessageTypeEnum type) {
        Map<MessageTypeEnum, String> messages = getMessages();
        return messages.getOrDefault(type, "");
    }

    private String createMessage(String pattern, LocalDateTime dateTime, Object... args) {
        return dateTime + " " + MessageFormat.format(pattern, args);
    }

    private void sendMessage(MessageTypeEnum type, ObjectChangedDTO<DTO> result, DTO dto) {
        if (Objects.nonNull(result)) {
            Object[] args = Objects.nonNull(dto) ? getArgs(dto) : new String[0];
            sendMessage(type, result.getUpdatedAt(), args);
        }
    }

    //TODO настроить библиотекаря
    private void sendMessage(MessageTypeEnum type, LocalDateTime updatedAt, Object[] args) {
        String message = createMessage(
                getMessagePattern(type),
                updatedAt,
                args
        );
        notificationService.sendMessage(message);
    }
}
