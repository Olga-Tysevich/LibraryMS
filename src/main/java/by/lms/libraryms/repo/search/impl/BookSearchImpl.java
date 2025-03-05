package by.lms.libraryms.repo.search.impl;

import by.lms.libraryms.domain.Book;
import by.lms.libraryms.repo.search.BookSearch;
import by.lms.libraryms.services.searchobjects.BookReq;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class BookSearchImpl extends AbstractSearchRepo<Book, BookReq> implements BookSearch {

    public BookSearchImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public boolean delete(BookReq searchReq) {
        Query query = addParams(super.query(searchReq), searchReq);

        DeleteResult result = mongoTemplate().remove(query, Book.class);

        return result.getDeletedCount() > 0;
    }

    @Override
    public List<Book> find(BookReq searchReq) {
        Query query = addParams(super.query(searchReq), searchReq);

        return mongoTemplate().find(query, Book.class);
    }

    @Override
    public ListForPageResp<Book> findList(BookReq searchReq) {
        Query query = addParams(super.query(searchReq), searchReq);

        return findList(mongoTemplate(), query, Book.class, searchReq);
    }

    private Query addParams(Query query, BookReq searchReq) {
        if (Objects.nonNull(searchReq.getTitle())) {
            query.addCriteria(Criteria.where("title").regex(searchReq.getTitle()));
        }

        if (Objects.nonNull(searchReq.getAuthorIds()) && !searchReq.getAuthorIds().isEmpty()) {
            query.addCriteria(Criteria.where("authorIds").in(searchReq.getAuthorIds()));
        }

        if (Objects.nonNull(searchReq.getGenreIds()) && !searchReq.getGenreIds().isEmpty()) {
            query.addCriteria(Criteria.where("genreIds").in(searchReq.getGenreIds()));
        }

        if (searchReq.getYear() > 0) {
            query.addCriteria(Criteria.where("year").is(searchReq.getYear()));
        }

        return query;
    }

}
