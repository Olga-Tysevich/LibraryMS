package by.lms.libraryms.controller;

import by.lms.libraryms.dto.req.AuthorDTO;
import by.lms.libraryms.dto.req.AuthorSearchReqDTO;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.facades.AuthorFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/library/author")
public class AuthorController {
    private final AuthorFacade authorFacade;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid AuthorDTO author) {
        ObjectChangedDTO result = authorFacade.addAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid AuthorDTO author) {
        ObjectChangedDTO result = authorFacade.updateAuthor(author);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody @Valid AuthorSearchReqDTO author) {
        ObjectChangedDTO result = authorFacade.deleteAuthor(author);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get")
    public ResponseEntity<?> get(@RequestBody @Valid AuthorSearchReqDTO author) {
        AuthorDTO result = authorFacade.getAuthor(author);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get/list")
    public ResponseEntity<?> getList(@RequestBody @Valid AuthorSearchReqDTO author) {
        ListForPageDTO<AuthorDTO> result = authorFacade.getAuthors(author);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
