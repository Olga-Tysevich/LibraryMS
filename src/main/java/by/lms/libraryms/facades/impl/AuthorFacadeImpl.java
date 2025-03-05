package by.lms.libraryms.facades.impl;

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

import java.util.Map;

@Component
@AllArgsConstructor
public class AuthorFacadeImpl extends AbstractFacadeImpl<Author, AuthorDTO,
        AuthorSearchReq, AuthorSearchReqDTO,
        AuthorService, AuthorMapper>
        implements AuthorFacade {

    @Override
    protected Map<MessageTypeEnum, String> getMessages() {
        return getMessageConf().getAuthorMap();
    }

    @Override
    protected Object[] getArgs(AuthorDTO dto) {
        return new Object[]{dto.getName(), dto.getSurname()};
    }

}
