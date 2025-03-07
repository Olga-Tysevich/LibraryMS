package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.Genre;
import by.lms.libraryms.dto.req.GenreDTO;
import by.lms.libraryms.dto.req.GenreSearchReqDTO;
import by.lms.libraryms.mappers.GenreMapper;
import by.lms.libraryms.repo.GenreRepo;
import by.lms.libraryms.repo.search.GenreSearch;
import by.lms.libraryms.services.GenreService;
import by.lms.libraryms.services.searchobjects.GenreSearchReq;
import org.springframework.stereotype.Service;

@Service
public class GenreServiceImpl extends AbstractServiceImpl<Genre, GenreDTO,
        GenreSearchReq, GenreSearchReqDTO,
        GenreRepo, GenreSearch,
        GenreMapper> implements GenreService {

    public GenreServiceImpl(GenreRepo repository, GenreSearch searchRepo, GenreMapper mapper) {
        super(repository, searchRepo, mapper);
    }

    @Override
    protected Class<Genre> clazz() {
        return Genre.class;
    }
}
