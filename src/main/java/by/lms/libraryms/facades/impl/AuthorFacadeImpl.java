package by.lms.libraryms.facades.impl;

import by.lms.libraryms.conf.i18n.MessageConf;
import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.domain.Author;
import by.lms.libraryms.dto.req.AuthorDTO;
import by.lms.libraryms.dto.req.AuthorSearchReqDTO;
import by.lms.libraryms.facades.AuthorFacade;
import by.lms.libraryms.mappers.AuthorMapper;
import by.lms.libraryms.services.AuthorService;
import by.lms.libraryms.services.searchobjects.AuthorSearchReq;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class AuthorFacadeImpl extends AbstractFacadeImpl<Author, AuthorDTO,
        AuthorSearchReq, AuthorSearchReqDTO,
        AuthorService, AuthorMapper>
        implements AuthorFacade {


    @Override
    protected AuthorDTO buildDTOForReport(AuthorSearchReqDTO searchReqDTO) {
        return AuthorDTO.builder()
                .id(searchReqDTO.getId())
                .name(searchReqDTO.getName())
                .surname(searchReqDTO.getSurname())
                .build();
    }

    @Override
    protected String getMessagePattern(MessageTypeEnum type) {
        MessageConf conf = getMessageConf();
        switch (type) {
            case ADD -> {
                return conf.getAuthorCreatedMessage();
            }
            case UPDATE -> {
                return conf.getAuthorUpdatedMessage();
            }
            case DELETE -> {
                return conf.getAuthorDeletedMessage();
            }
            default -> {
                return "";
            }
        }
    }

    @Override
    protected String createMessage(String pattern, LocalDateTime dateTime, String... args) {
        return dateTime + " " + String.format(pattern, "", args[0], args[1]);
    }

    @Override
    protected String[] getArgs(AuthorDTO dto) {
        return new String[]{dto.getName(), dto.getSurname()};
    }

    @Override
    protected String[] getArgs(AuthorSearchReqDTO searchReqDTO) {
        return new String[]{searchReqDTO.getName(), searchReqDTO.getSurname()};
    }
}
