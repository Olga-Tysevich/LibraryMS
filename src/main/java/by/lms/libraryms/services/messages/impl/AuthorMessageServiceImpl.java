package by.lms.libraryms.services.messages.impl;

import by.lms.libraryms.conf.i18n.MessageConf;
import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.dto.req.AuthorDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.services.messages.AuthorMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
public class AuthorMessageServiceImpl extends AbstractMessageServiceImpl<AuthorDTO>
        implements AuthorMessageService {

    public AuthorMessageServiceImpl(MessageConf messageConf) {
        super(messageConf);
    }

    @Override
    public String createMessage(MessageTypeEnum typeEnum, ObjectChangedDTO<AuthorDTO> dto) {
        String pattern = messageConf().getAuthorMap().getOrDefault(typeEnum, "");
        AuthorDTO authorDTO = dto.getObject();
        Object[] args = new Object[3];
        if (Objects.nonNull(authorDTO)) {
            //TODO добавить библиотекаря
            args[0] = "";
            args[1] = StringUtils.defaultIfBlank(authorDTO.getName(), "");
            args[2] = StringUtils.defaultIfBlank(authorDTO.getSurname(), "");
        }
        return super.createMessage(pattern, dto.getUpdatedAt(), args);
    }

}
