package by.lms.libraryms.facades.impl;

import by.lms.libraryms.conf.i18n.MessageConf;
import by.lms.libraryms.dto.req.AuthorDTO;
import by.lms.libraryms.dto.req.AuthorSearchReqDTO;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.facades.AuthorFacade;
import by.lms.libraryms.mappers.AuthorMapper;
import by.lms.libraryms.services.AuthorService;
import by.lms.libraryms.services.NotificationService;
import by.lms.libraryms.services.ReportTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorFacadeImpl implements AuthorFacade {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;
    private final NotificationService<AuthorDTO> notificationService;
    private final MessageConf messageConf;

    @Override
    public ObjectChangedDTO addAuthor(@NotNull AuthorDTO authorDTO) {
        ObjectChangedDTO result = Optional.of(authorDTO)
                .map(authorMapper::toAuthor)
                .map(authorService::addAuthor)
                .map(a -> authorMapper.toObjectChangedDTO(a, null, LocaleContextHolder.getLocale()))
                .orElseGet(() -> {
                    notificationService.createReport(ReportTypeEnum.EXCEPTION, authorDTO);
                    return null;
                });

        if (Objects.nonNull(result)) {
            String message = createMessage(
                    messageConf.getAuthorCreatedMessage(),
                    result.getCreatedAt(),
                    authorDTO.getName(),
                    authorDTO.getSurname()
            );
            notificationService.sendMessage(message);
        }
        return result;
    }

    @Override
    public ObjectChangedDTO updateAuthor(@NotNull AuthorDTO authorDTO) {
        ObjectChangedDTO result = Optional.of(authorDTO)
                .map(authorMapper::toAuthor)
                .map(authorService::updateAuthor)
                .map(a -> authorMapper.toObjectChangedDTO(a, null, LocaleContextHolder.getLocale()))
                .orElseGet(() -> {
                    notificationService.createReport(ReportTypeEnum.EXCEPTION, authorDTO);
                    return null;
                });
        if (Objects.nonNull(result)) {
            String message = createMessage(
                    messageConf.getAuthorUpdatedMessage(),
                    result.getUpdatedAt(),
                    authorDTO.getName(),
                    authorDTO.getSurname()
            );
            notificationService.sendMessage(message);
        }
        return result;
    }

    @Override
    public ObjectChangedDTO deleteAuthor(@NotNull AuthorSearchReqDTO searchReqDTO) {
        ObjectChangedDTO result = Optional.of(searchReqDTO)
                .map(authorMapper::toSearchReq)
                .map(authorService::deleteAuthor)
                .map(a -> authorMapper.toObjectChangedDTO(a, null, LocaleContextHolder.getLocale()))
                .orElseGet(() -> {
                    notificationService.createReport(ReportTypeEnum.EXCEPTION, buildAuthorForReport(searchReqDTO));
                    return null;
                });
        if (Objects.nonNull(result)) {
            String message = createMessage(
                    messageConf.getAuthorDeletedMessage(),
                    result.getDeletedAt(),
                    searchReqDTO.getName(),
                    searchReqDTO.getSurname()
            );
            notificationService.sendMessage(message);
        }
        return result;
    }

    @Override
    public AuthorDTO getAuthor(@NotNull AuthorSearchReqDTO searchReqDTO) {
        return Optional.of(searchReqDTO)
                .map(authorMapper::toSearchReq)
                .map(authorService::getAuthor)
                .map(authorMapper::toAuthorDTO)
                .orElse(null);
    }

    @Override
    public ListForPageDTO<AuthorDTO> getAuthors(@NotNull AuthorSearchReqDTO searchReqDTO) {
        return Optional.of(searchReqDTO)
                .map(authorMapper::toSearchReq)
                .map(authorService::getAuthors)
                .map(authorMapper::toListForPageDTO)
                .orElse(null);
    }

    private AuthorDTO buildAuthorForReport(AuthorSearchReqDTO searchReqDTO) {
        return AuthorDTO.builder()
                .id(searchReqDTO.getId())
                .name(searchReqDTO.getName())
                .surname(searchReqDTO.getSurname())
                .build();
    }

    private String createMessage(String pattern, LocalDateTime dateTime, String name, String surname) {
        return String.format(dateTime + " " + pattern, name, surname);
    }

}
