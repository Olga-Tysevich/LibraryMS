package by.lms.libraryms.services.messages.impl;

import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.dto.req.AuthorDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.services.messages.MessageService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@AllArgsConstructor
public class AuthorMessageService extends AbstractMessageServiceImpl<AuthorDTO>
        implements MessageService<AuthorDTO> {

    @Override
    public String createMessage(MessageTypeEnum typeEnum, ObjectChangedDTO<AuthorDTO> dto) {
        String pattern = messageConf().getAuthorMap().getOrDefault(typeEnum, "");
        AuthorDTO authorDTO = dto.getObject();
        Object[] args = new Object[2];
        if (Objects.nonNull(authorDTO)) {
            args[0] = StringUtils.defaultIfBlank(authorDTO.getName(), "");
            args[1] = StringUtils.defaultIfBlank(authorDTO.getSurname(), "");
        }
        return super.createMessage(pattern, dto.getUpdatedAt(), args);
    }

}
