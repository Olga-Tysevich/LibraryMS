package by.lms.libraryms.controller;

import by.lms.libraryms.dto.req.GenreDTO;
import by.lms.libraryms.dto.req.GenreSearchReqDTO;
import by.lms.libraryms.dto.resp.ListForPageDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.facades.GenreFacade;
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
@RequestMapping("api/v1/library/genres")
public class GenreController {
    private final GenreFacade genreFacade;

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid GenreDTO genre) {
        ObjectChangedDTO<GenreDTO> result = genreFacade.add(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Valid GenreDTO genre) {
        ObjectChangedDTO<GenreDTO> result = genreFacade.update(genre);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> update(@RequestBody @Valid GenreSearchReqDTO genre) {

        ObjectChangedDTO<GenreDTO> result = genreFacade.delete(genre);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get")
    public ResponseEntity<?> get(@RequestBody @Valid GenreSearchReqDTO genre) {
        GenreDTO result = genreFacade.get(genre);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/get/list")
    public ResponseEntity<?> getList(@RequestBody @Valid GenreSearchReqDTO genre) {
        ListForPageDTO<GenreDTO> result = genreFacade.getAll(genre);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
