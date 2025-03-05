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
import by.lms.libraryms.services.ReportTypeEnum;
import by.lms.libraryms.services.searchobjects.SearchReq;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractFacadeImpl<Entity extends AbstractDomainClass, DTO extends AbstractDTO,
        SR extends SearchReq, SRD extends SearchReqDTO,
        Service extends AbstractService<Entity, SR>, Mapper extends ObjectMapper<Entity, DTO, SR, SRD>>
        implements AbstractFacade<Entity, DTO, SR, SRD, Service, Mapper> {

    private Service service;
    private Mapper mapper;
    @Getter
    private NotificationService<DTO> notificationService;
    @Getter
    private MessageConf messageConf;


    @Override
    public ObjectChangedDTO add(@NotNull DTO dto) {
        ObjectChangedDTO result = Optional.of(dto)
                .map(mapper::toEntity)
                .map(service::add)
                .map(a -> mapper.toObjectChangedDTO(a, null))
                .orElseGet(() -> {
                    notificationService.createReport(ReportTypeEnum.EXCEPTION, dto);
                    return null;
                });

        sendMessage(MessageTypeEnum.ADD, result, dto);
        return result;
    }

    @Override
    public ObjectChangedDTO update(@NotNull DTO dto) {
        ObjectChangedDTO result = Optional.of(dto)
                .map(mapper::toEntity)
                .map(service::update)
                .map(a -> mapper.toObjectChangedDTO(a, null))
                .orElseGet(() -> {
                    notificationService.createReport(ReportTypeEnum.EXCEPTION, dto);
                    return null;
                });
        sendMessage(MessageTypeEnum.UPDATE, result, dto);
        return result;
    }

    @Override
    public ObjectChangedDTO delete(@NotNull SRD searchReqDTO) {
        ObjectChangedDTO result = Optional.of(searchReqDTO)
                .map(mapper::toSearchReq)
                .map(service::delete)
                .map(a -> mapper.toObjectChangedDTO(a, Instant.now()))
                .orElseGet(() -> {
                    notificationService.createReport(ReportTypeEnum.EXCEPTION, buildDTOForReport(searchReqDTO));
                    return null;
                });

        sendMessage(MessageTypeEnum.DELETE, result, searchReqDTO);

        return result;
    }

    @Override
    public DTO get(@NotNull SRD searchReqDTO) {
        return Optional.of(searchReqDTO)
                .map(mapper::toSearchReq)
                .map(service::get)
                .map(mapper::toDTO)
                .orElse(null);
    }

    @Override
    public ListForPageDTO<DTO> getAll(@NotNull SRD searchReqDTO) {
        return Optional.of(searchReqDTO)
                .map(mapper::toSearchReq)
                .map(service::getAll)
                .map(mapper::toListForPageDTO)
                .orElse(null);
    }

    protected abstract DTO buildDTOForReport(SRD searchReqDTO);

    protected abstract String getMessagePattern(MessageTypeEnum type);

    protected abstract String createMessage(String pattern, LocalDateTime dateTime, String... args);

    protected abstract String[] getArgs(DTO dto);

    protected abstract String[] getArgs(SRD searchReqDTO);

    private void sendMessage(MessageTypeEnum type, ObjectChangedDTO result, DTO dto) {
        if (Objects.nonNull(result)) {
            String[] args = Objects.nonNull(dto) ? getArgs(dto) :  new String[0];
            sendMessage(type, result.getUpdatedAt(), args);
        }
    }

    private void sendMessage(MessageTypeEnum type, ObjectChangedDTO result, SRD searchReqDTO) {
        if (Objects.nonNull(result)) {
            String[] args = Objects.nonNull(searchReqDTO) ? getArgs(searchReqDTO) :  new String[0];
            sendMessage(type, result.getUpdatedAt(), args);
        }
    }

    //TODO настроить библиотекаря
    private void sendMessage(MessageTypeEnum type, LocalDateTime updatedAt, String[] args) {
        String message = createMessage(
                getMessagePattern(type),
                updatedAt,
                args
        );
        notificationService.sendMessage(message);
    }
}
