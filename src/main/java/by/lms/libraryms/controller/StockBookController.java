package by.lms.libraryms.controller;

import by.lms.libraryms.dto.req.*;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
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
@RequestMapping("api/v1/library/stock_book")
public class StockBookController {
    private final StockBookFacade stockBookFacade;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid StockBookDTO dto) {
        ObjectChangedDTO<StockBookDTO> result = stockBookFacade.add(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid StockBookDTO dto) {
        ObjectChangedDTO<StockBookDTO> result = stockBookFacade.update(dto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody @Valid InventoryBookSearchReqDTO book) {
        ObjectChangedDTO<InventoryBookDTO> result = stockBookFacade.delete(book);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get")
    public ResponseEntity<?> get(@RequestBody @Valid InventoryBookSearchReqDTO book) {
        InventoryBookDTO result = stockBookFacade.get(book);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get/list")
    public ResponseEntity<?> getList(@RequestBody @Valid InventoryBookSearchReqDTO book) {
        ListForPageDTO<InventoryBookDTO> result = stockBookFacade.getAll(book);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
