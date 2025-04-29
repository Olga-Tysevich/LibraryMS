package by.lms.libraryms.services.impl;

import by.lms.libraryms.domain.BookLending;
import by.lms.libraryms.dto.req.BookLendingDTO;
import by.lms.libraryms.dto.req.BookLendingSearchReqDTO;
import by.lms.libraryms.dto.resp.ObjectChangedDTO;
import by.lms.libraryms.dto.resp.ObjectListChangedDTO;
import by.lms.libraryms.exceptions.ActionProhibitedException;
import by.lms.libraryms.mappers.BookLendingMapper;
import by.lms.libraryms.repo.BookLendingRepo;
import by.lms.libraryms.repo.search.BookLendingSearch;
import by.lms.libraryms.services.BookLendingService;
import by.lms.libraryms.services.searchobjects.BookLendingSearchReq;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class BookLendingServiceImpl extends AbstractServiceImpl<BookLending, BookLendingDTO,
        BookLendingSearchReq, BookLendingSearchReqDTO,
        BookLendingRepo, BookLendingSearch,
        BookLendingMapper> implements BookLendingService {
    @Value("${book.lending.allowedDelayForUpdate:1440}")
    private int allowedDelayForUpdate;

    public BookLendingServiceImpl(BookLendingRepo repository,
                                  BookLendingSearch searchRepo,
                                  BookLendingMapper mapper) {
        super(repository, searchRepo, mapper);
    }

    @Override
    @Transactional
    public ObjectListChangedDTO<BookLendingDTO> delete(BookLendingSearchReqDTO searchReqDTO) {
        BookLendingSearchReq searchReq = getMapper().toSearchReq(searchReqDTO);

        Set<String> forDelete = getBookLeadingListForModification(searchReq);

        BookLendingSearchReqDTO req = BookLendingSearchReqDTO.builder()
                .ids(forDelete)
                .build();

        return super.delete(req);
    }

    @Override
    public ObjectChangedDTO<BookLendingDTO> update(BookLendingDTO dto) {
       if (checkIfAllowed(getMapper().toEntity(dto))) {
           return super.update(dto);
       } else {
           throw new ActionProhibitedException("The permitted update period has expired for book lending with id: " + dto.getId());
       }
    }

    @Override
    protected Class<BookLending> clazz() {
        return BookLending.class;
    }


    private Set<String> getBookLeadingListForModification(BookLendingSearchReq searchReq) {
        List<BookLending> bookLendingList = super.find(searchReq);

        Set<String> result = new HashSet<>();

        Instant now = Instant.now();

        bookLendingList.forEach(bl -> {
            if (now.isAfter(bl.getCreatedAt().plus(allowedDelayForUpdate, ChronoUnit.MINUTES))) {
                if (checkIfAllowed(bl)) result.add(bl.getId());
            }
        });

        return result;
    }

    private boolean checkIfAllowed(BookLending bookLending) {
        Instant now = Instant.now();
        if (now.isAfter(bookLending.getCreatedAt().plus(allowedDelayForUpdate, ChronoUnit.MINUTES))) {
            //TODO лог
            System.out.println("The permitted update period has expired for book lending with id: " + bookLending.getId());
            return false;
        }
        return true;
    }
}
