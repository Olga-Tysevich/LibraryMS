package by.lms.libraryms.services.messages.impl;

import by.lms.libraryms.conf.i18n.MessageConf;
import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.dto.req.AuthorDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.services.messages.AuthorMessageService;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AuthorMessageServiceImpl extends AbstractMessageServiceImpl<AuthorDTO>
        implements AuthorMessageService {

    public AuthorMessageServiceImpl(MessageConf messageConf) {
        super(messageConf);
    }

    @Override
    protected void addSpecific(@NotNull MessageTypeEnum typeEnum,
                               @NotNull ObjectChangedDTO<AuthorDTO> dto,
                               @NotNull List<Object> args) {
        String pattern = messageConf().getAuthorMap().getOrDefault(typeEnum, "");
        AuthorDTO authorDTO = dto.getObject();
        args.add(StringUtils.defaultIfBlank(authorDTO.getName(), ""));
        args.add(StringUtils.defaultIfBlank(authorDTO.getSurname(), ""));
    }

    @Override
    protected String getPattern(MessageTypeEnum typeEnum) {
        return messageConf().getAuthorMap().getOrDefault(typeEnum, "");
    }

}
