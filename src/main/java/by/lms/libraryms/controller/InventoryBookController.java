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

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid InventoryBookDTO inventoryBook) {
        ObjectChangedDTO<InventoryBookDTO> result = inventoryBookFacade.add(inventoryBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid InventoryBookDTO inventoryBook) {
        ObjectChangedDTO<InventoryBookDTO> result = inventoryBookFacade.update(inventoryBook);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> update(@RequestBody @Valid InventoryBookSearchReqDTO inventoryBook) {
        ObjectChangedDTO<InventoryBookDTO> result = inventoryBookFacade.delete(inventoryBook);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get")
    public ResponseEntity<?> get(@RequestBody @Valid InventoryBookSearchReqDTO inventoryBook) {
        InventoryBookDTO result = inventoryBookFacade.get(inventoryBook);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get/list")
    public ResponseEntity<?> getList(@RequestBody @Valid InventoryBookSearchReqDTO inventoryBook) {
        ListForPageDTO<InventoryBookDTO> result = inventoryBookFacade.getAll(inventoryBook);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
