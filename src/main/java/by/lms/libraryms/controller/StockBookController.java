package by.lms.libraryms.controller;

import by.lms.libraryms.dto.req.*;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.dto.resp.ObjectListChangedDTO;
import by.lms.libraryms.facades.StockBookFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/library/stock_books")
public class StockBookController {
    private final StockBookFacade stockBookFacade;

    @PostMapping("/add/librarian")
    public ResponseEntity<?> add(@RequestBody @Valid StockBookDTO dto) {
        ObjectChangedDTO<StockBookDTO> result = stockBookFacade.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/update/librarian")
    public ResponseEntity<?> update(@RequestBody @Valid StockBookDTO dto) {
        ObjectChangedDTO<StockBookDTO> result = stockBookFacade.update(dto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/delete/librarian")
    public ResponseEntity<?> delete(@RequestBody @Valid StockBookSearchReqDTO stockBook) {
        ObjectListChangedDTO<StockBookDTO> result = stockBookFacade.delete(stockBook);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get/librarian")
    public ResponseEntity<?> get(@RequestBody @Valid StockBookSearchReqDTO stockBook) {
        StockBookDTO result = stockBookFacade.get(stockBook);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get/list/librarian")
    public ResponseEntity<?> getList(@RequestBody @Valid StockBookSearchReqDTO stockBook) {
        ListForPageDTO<StockBookDTO> result = stockBookFacade.getAll(stockBook);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
