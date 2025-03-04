package by.lms.libraryms.facades.impl;

import by.lms.libraryms.dto.req.AuthorDTO;
import by.lms.libraryms.dto.req.AuthorSearchReqDTO;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.facades.AuthorFacade;
import by.lms.libraryms.mappers.AuthorMapper;
import by.lms.libraryms.services.AuthorService;
import by.lms.libraryms.services.ReportService;
import by.lms.libraryms.services.ReportTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthorFacadeImpl implements AuthorFacade {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;
    private final ReportService<AuthorDTO> reportService;

    @Override
    public ObjectChangedDTO addAuthor(@NotNull AuthorDTO authorDTO) {
        return Optional.of(authorDTO)
                .map(authorMapper::toAuthor)
                .map(authorService::addAuthor)
                .map(a -> authorMapper.toObjectChangedDTO(a, null, LocaleContextHolder.getLocale()))
                .orElseGet(() -> {
                    reportService.createReport(ReportTypeEnum.EXCEPTION, authorDTO);
                    return null;
                });
    }

    @Override
    public ObjectChangedDTO updateAuthor(@NotNull AuthorDTO authorDTO) {
        return Optional.of(authorDTO)
                .map(authorMapper::toAuthor)
                .map(authorService::updateAuthor)
                .map(a -> authorMapper.toObjectChangedDTO(a, null, LocaleContextHolder.getLocale()))
                .orElseGet(() -> {
                    reportService.createReport(ReportTypeEnum.EXCEPTION, authorDTO);
                    return null;
                });
    }

    @Override
    public ObjectChangedDTO deleteAuthor(@NotNull AuthorSearchReqDTO searchReqDTO) {
        return Optional.of(searchReqDTO)
                .map(authorMapper::toSearchReq)
                .map(authorService::deleteAuthor)
                .map(a -> authorMapper.toObjectChangedDTO(a, null, LocaleContextHolder.getLocale()))
                .orElseGet(() -> {
                    reportService.createReport(ReportTypeEnum.EXCEPTION, buildAuthorForReport(searchReqDTO));
                    return null;
                });
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

}
