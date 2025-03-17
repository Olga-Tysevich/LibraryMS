package by.lms.libraryms.services.messages.impl;

import by.lms.libraryms.conf.i18n.MessageConf;
import by.lms.libraryms.conf.i18n.MessageTypeEnum;
import by.lms.libraryms.dto.req.BookDTO;
import by.lms.libraryms.dto.req.StockBookDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.mappers.StockBookMapper;
import by.lms.libraryms.services.BookService;
import by.lms.libraryms.services.messages.BookMessageService;
import by.lms.libraryms.services.messages.StockBookMessageService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StockBookMessageServiceImpl extends AbstractMessageServiceImpl<StockBookDTO> implements StockBookMessageService {
    private final BookMessageService bookMessageService;
    private final StockBookMapper stockBookMapper;
    private final BookService bookService;

    public StockBookMessageServiceImpl(MessageConf messageConf,
                                       BookMessageService bookMessageService,
                                       StockBookMapper stockBookMapper, BookService bookService) {
        super(messageConf);
        this.bookMessageService = bookMessageService;
        this.stockBookMapper = stockBookMapper;
        this.bookService = bookService;
    }

    //TODO разобраться с обрщением к бд
    @Override
    protected void addSpecific(MessageTypeEnum typeEnum, ObjectChangedDTO<StockBookDTO> dto, List<Object> args) {
        List<BookDTO> bookDTOList = dto.getObjects().stream()
                .map(StockBookDTO::getBookId)
                .map(bookService::findById)
                .toList();
        ObjectChangedDTO<BookDTO> bookChangedDTO = stockBookMapper.toBookChangedDTO(dto, bookDTOList);
        bookMessageService.addSpecific(typeEnum, bookChangedDTO, args);
        StockBookDTO stockBookDTO = dto.getObject();
        args.add(stockBookDTO.getQuantity());
        args.add(stockBookDTO.getDateOfReceipt());
    }

    @Override
    protected String getPattern(MessageTypeEnum typeEnum) {
        return getMessageConf().getStockBookMap().getOrDefault(typeEnum, "");
    }
}
