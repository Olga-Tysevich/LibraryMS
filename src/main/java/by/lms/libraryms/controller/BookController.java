package by.lms.libraryms.controller;

import by.lms.libraryms.dto.req.BookDTO;
import by.lms.libraryms.dto.req.BookSearchReqDTO;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.dto.resp.ObjectListChangedDTO;
import by.lms.libraryms.facades.BookFacade;
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
@RequestMapping("api/v1/library/books")
public class BookController {
    private final BookFacade bookFacade;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid BookDTO book) {
        ObjectChangedDTO<BookDTO> result = bookFacade.add(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid BookDTO book) {
        ObjectChangedDTO<BookDTO> result = bookFacade.update(book);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody @Valid BookSearchReqDTO book) {
        ObjectListChangedDTO<BookDTO> result = bookFacade.delete(book);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get")
    public ResponseEntity<?> get(@RequestBody @Valid BookSearchReqDTO book) {
        BookDTO result = bookFacade.get(book);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get/list")
    public ResponseEntity<?> getList(@RequestBody @Valid BookSearchReqDTO book) {
        ListForPageDTO<BookDTO> result = bookFacade.getAll(book);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
