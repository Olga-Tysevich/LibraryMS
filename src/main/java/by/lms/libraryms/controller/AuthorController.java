package by.lms.libraryms.controller;

import by.lms.libraryms.dto.req.AuthorDTO;
import by.lms.libraryms.dto.req.AuthorSearchReqDTO;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.dto.resp.ObjectListChangedDTO;
import by.lms.libraryms.facades.AuthorFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/library/authors")
public class AuthorController {
    private final AuthorFacade authorFacade;

    @PostMapping("/add/librarian")
    public ResponseEntity<?> add(@RequestBody @Valid AuthorDTO author) {
        ObjectChangedDTO<AuthorDTO> result = authorFacade.add(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/update/librarian")
    public ResponseEntity<?> update(@RequestBody @Valid AuthorDTO author) {
        ObjectChangedDTO<AuthorDTO> result = authorFacade.update(author);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/delete/librarian")
    public ResponseEntity<?> delete(@RequestBody @Valid AuthorSearchReqDTO author) {
        ObjectListChangedDTO<AuthorDTO> result = authorFacade.delete(author);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get")
    public ResponseEntity<?> get(@RequestBody @Valid AuthorSearchReqDTO author) {
        AuthorDTO result = authorFacade.get(author);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get/list")
    public ResponseEntity<?> getList(@RequestBody @Valid AuthorSearchReqDTO author) {
        ListForPageDTO<AuthorDTO> result = authorFacade.getAll(author);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
