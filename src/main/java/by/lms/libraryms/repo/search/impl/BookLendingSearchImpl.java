package by.lms.libraryms.repo.search.impl;

import by.lms.libraryms.domain.BookLending;
import by.lms.libraryms.repo.search.BookLendingSearch;
import by.lms.libraryms.services.searchobjects.BookLendingSearchReq;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class BookLendingSearchImpl extends AbstractSearchRepo<BookLending, BookLendingSearchReq>
        implements BookLendingSearch {

    public BookLendingSearchImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    protected Query addParams(Query query, BookLendingSearchReq bookLendingSearchReq) {

        if (Objects.nonNull(bookLendingSearchReq.getInventoryBookIds()) && !bookLendingSearchReq.getInventoryBookIds().isEmpty()) {
            query.addCriteria(Criteria.where("inventoryBookId").in(bookLendingSearchReq.getInventoryBookIds()));
        }

        if (Objects.nonNull(bookLendingSearchReq.getReaderIds()) && !bookLendingSearchReq.getReaderIds().isEmpty()) {
            query.addCriteria(Criteria.where("readerId").in(bookLendingSearchReq.getReaderIds()));
        }
        
        if (Objects.nonNull(bookLendingSearchReq.getReaderIds()) && !bookLendingSearchReq.getReaderIds().isEmpty()) {
            query.addCriteria(Criteria.where("readerId").in(bookLendingSearchReq.getReaderIds()));
        }

        return null;
    }

    @Override
    protected Class<BookLending> clazz() {
        return BookLending.class;
    }

    @Override
    protected boolean hasReferences(List<ObjectId> objectIds) {
       return super.hasReferences("readers", "inventoryBookLendingIds", objectIds);
    }
}
