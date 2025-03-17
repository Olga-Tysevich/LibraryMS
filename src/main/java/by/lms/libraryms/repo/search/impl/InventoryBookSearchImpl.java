package by.lms.libraryms.repo.search.impl;

import by.lms.libraryms.domain.Book;
import by.lms.libraryms.domain.InventoryBook;
import by.lms.libraryms.mappers.InventoryBookMapper;
import by.lms.libraryms.repo.search.BookSearch;
import by.lms.libraryms.repo.search.InventoryBookSearch;
import by.lms.libraryms.services.searchobjects.BookSearchReq;
import by.lms.libraryms.services.searchobjects.InventoryBookSearchReq;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class InventoryBookSearchImpl extends AbstractSearchRepo<InventoryBook, InventoryBookSearchReq>
        implements InventoryBookSearch {
    private final BookSearch bookSearch;
    private final InventoryBookMapper mapper;

    public InventoryBookSearchImpl(MongoTemplate mongoTemplate, BookSearch bookSearch, InventoryBookMapper mapper) {
        super(mongoTemplate);
        this.bookSearch = bookSearch;
        this.mapper = mapper;
    }

    @Override
    protected Query addParams(Query query, InventoryBookSearchReq searchReq) {

        BookSearchReq bookSearchReq = mapper.toBookSearchReq(searchReq);
        List<ObjectId> bookIds = bookSearch.find(bookSearchReq).stream()
                .map(Book::getId)
                .map(ObjectId::new)
                .toList();

        if (!bookIds.isEmpty()) {
            query.addCriteria(Criteria.where("bookId").in(bookIds));
        }

        if (Objects.nonNull(searchReq.getInventoryNumbers()) && !searchReq.getInventoryNumbers().isEmpty()) {
            query.addCriteria(Criteria.where("inventoryNumber").in(searchReq.getInventoryNumbers()));
        }

        if (Objects.nonNull(searchReq.getInventoryNumbers()) && !searchReq.getInventoryNumbers().isEmpty()) {
            query.addCriteria(Criteria.where("bookOrderIds").in(searchReq.getInventoryNumbers()));
        }

        if (Objects.nonNull(searchReq.getIsAvailable())) {
            query.addCriteria(Criteria.where("available").is(searchReq.getIsAvailable()));
        }

        return query;
    }

    @Override
    protected Class<InventoryBook> clazz() {
        return InventoryBook.class;
    }

    @Override
    protected boolean hasReferences(List<ObjectId> objectIds) {
        String relatedFieldName = "bookId";
        boolean hasReferencesInInventoryBooks = super.hasReferences("inventory_books", relatedFieldName, objectIds);
        boolean hasReferencesInStockBooks = super.hasReferences("stock_books", relatedFieldName, objectIds);
        return hasReferencesInInventoryBooks && hasReferencesInStockBooks;
    }
}
