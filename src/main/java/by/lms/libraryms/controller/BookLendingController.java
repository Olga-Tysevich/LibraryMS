package by.lms.libraryms.controller;

import by.lms.libraryms.dto.req.BookLendingDTO;
import by.lms.libraryms.dto.req.BookLendingSearchReqDTO;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.dto.resp.ObjectListChangedDTO;
import by.lms.libraryms.facades.BookLendingFacade;
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
@RequestMapping("api/v1/library/book_lendings")
public class BookLendingController {
    private final BookLendingFacade bookLendingFacade;

    @PostMapping("/add/librarian")
    public ResponseEntity<?> add(@RequestBody @Valid BookLendingDTO bookLending) {
        ObjectChangedDTO<BookLendingDTO> result = bookLendingFacade.add(bookLending);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/update/librarian")
    public ResponseEntity<?> update(@RequestBody @Valid BookLendingDTO bookLending) {
        ObjectChangedDTO<BookLendingDTO> result = bookLendingFacade.update(bookLending);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/delete/librarian")
    public ResponseEntity<?> delete(@RequestBody @Valid BookLendingSearchReqDTO bookLending) {
        ObjectListChangedDTO<BookLendingDTO> result = bookLendingFacade.delete(bookLending);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get")
    public ResponseEntity<?> get(@RequestBody @Valid BookLendingSearchReqDTO bookLending) {
        BookLendingDTO result = bookLendingFacade.get(bookLending);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get/list")
    public ResponseEntity<?> getList(@RequestBody @Valid BookLendingSearchReqDTO bookLending) {
        ListForPageDTO<BookLendingDTO> result = bookLendingFacade.getAll(bookLending);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
