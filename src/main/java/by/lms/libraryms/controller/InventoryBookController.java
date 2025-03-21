package by.lms.libraryms.controller;

import by.lms.libraryms.dto.req.InventoryBookDTO;
import by.lms.libraryms.dto.req.InventoryBookSearchReqDTO;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.facades.InventoryBookFacade;
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
@RequestMapping("api/v1/library/inventory_books")
public class InventoryBookController {
    private final InventoryBookFacade inventoryBookFacade;

    @PostMapping("/update/librarian")
    public ResponseEntity<?> update(@RequestBody @Valid InventoryBookDTO book) {
        ObjectChangedDTO<InventoryBookDTO> result = inventoryBookFacade.update(book);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get/librarian")
    public ResponseEntity<?> get(@RequestBody @Valid InventoryBookSearchReqDTO book) {
        InventoryBookDTO result = inventoryBookFacade.get(book);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get/list")
    public ResponseEntity<?> getList(@RequestBody @Valid InventoryBookSearchReqDTO book) {
        ListForPageDTO<InventoryBookDTO> result = inventoryBookFacade.getAll(book);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
