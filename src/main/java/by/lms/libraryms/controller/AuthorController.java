package by.lms.libraryms.controller;

import by.lms.libraryms.facades.AuthorFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/library/author")
public class AuthorController {
    private final AuthorFacade facade;


}
