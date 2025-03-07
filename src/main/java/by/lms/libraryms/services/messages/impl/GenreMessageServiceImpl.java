package by.lms.libraryms.services.messages.impl;

import by.lms.libraryms.conf.i18n.MessageConf;
import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.dto.req.GenreDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.services.messages.GenreMessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class GenreMessageServiceImpl extends AbstractMessageServiceImpl<GenreDTO> implements GenreMessageService {
    public GenreMessageServiceImpl(MessageConf messageConf) {
        super(messageConf);
    }

    @Override
    protected void addSpecific(MessageTypeEnum typeEnum, ObjectChangedDTO<GenreDTO> dto, List<Object> args) {
        GenreDTO genreDTO = dto.getObject();
        if (Objects.nonNull(genreDTO)) {
            args.add(StringUtils.defaultIfBlank(genreDTO.getName(), ""));
        }
    }

    @Override
    protected String getPattern(MessageTypeEnum typeEnum) {
        return messageConf().getGenreMap().getOrDefault(typeEnum, "");
    }
}
