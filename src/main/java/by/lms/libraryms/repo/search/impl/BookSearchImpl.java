package by.lms.libraryms.repo.search.impl;

import by.lms.libraryms.domain.Book;
import by.lms.libraryms.repo.search.BookSearch;
import by.lms.libraryms.services.searchobjects.BookSearchReq;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class BookSearchImpl extends AbstractSearchRepo<Book, BookSearchReq> implements BookSearch {

    public BookSearchImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }


    protected Query addParams(@NotNull Query query, @NotNull BookSearchReq searchReq) {

        if (Objects.nonNull(searchReq.getTitles()) && !searchReq.getTitles().isEmpty()) {
            List<Criteria> regexCriteria = searchReq.getTitles().stream()
                    .map(title -> Criteria.where("title").regex(title, "i"))
                    .toList();

            query.addCriteria(new Criteria().orOperator(regexCriteria.toArray(new Criteria[0])));
        }

        if (Objects.nonNull(searchReq.getAuthorIds()) && !searchReq.getAuthorIds().isEmpty()) {
            query.addCriteria(Criteria.where("authorIds").in(searchReq.getAuthorIds()));
        }

        if (Objects.nonNull(searchReq.getGenreIds()) && !searchReq.getGenreIds().isEmpty()) {
            query.addCriteria(Criteria.where("genreIds").in(searchReq.getGenreIds()));
        }

        if (Objects.nonNull(searchReq.getYears()) && !searchReq.getYears().isEmpty()) {
            query.addCriteria(Criteria.where("year").in(searchReq.getYears()));
        }

        if (Objects.nonNull(searchReq.getYearFrom()) && Objects.nonNull(searchReq.getYearTo())
                && searchReq.getYearFrom() < searchReq.getYearTo()) {
            query.addCriteria(Criteria.where("year").gte(searchReq.getYearFrom())
                    .and("year").lte(searchReq.getYearTo()));
        }

        return query;
    }

    @Override
    protected Class<Book> clazz() {
        return Book.class;
    }

}
