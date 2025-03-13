package by.lms.libraryms.repo.search.impl;

import by.lms.libraryms.domain.InventoryBook;
import by.lms.libraryms.domain.StockBook;
import by.lms.libraryms.mappers.StockBookMapper;
import by.lms.libraryms.repo.search.InventoryBookSearch;
import by.lms.libraryms.repo.search.StockBookSearch;
import by.lms.libraryms.services.searchobjects.InventoryBookSearchReq;
import by.lms.libraryms.services.searchobjects.StockBookSearchReq;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class StockBookSearchImpl extends AbstractSearchRepo<StockBook, StockBookSearchReq> implements StockBookSearch {
    private final InventoryBookSearch inventoryBookSearch;
    private final StockBookMapper mapper;

    public StockBookSearchImpl(MongoTemplate mongoTemplate,
                               InventoryBookSearch inventoryBookSearch,
                               StockBookMapper mapper) {
        super(mongoTemplate);
        this.inventoryBookSearch = inventoryBookSearch;
        this.mapper = mapper;
    }

    @Override
    protected Query addParams(Query query, StockBookSearchReq searchReq) {
        InventoryBookSearchReq bookSearchReq = mapper.toBookSearchReq(searchReq);
        List<ObjectId> inventoryBookIds = inventoryBookSearch.find(bookSearchReq).stream()
                .map(InventoryBook::getId)
                .map(ObjectId::new)
                .toList();

        if (!inventoryBookIds.isEmpty()) {
            query.addCriteria(Criteria.where("inventoryBookId").in(inventoryBookIds));
        }

        if (Objects.nonNull(searchReq.getNumberOfBooks())) {
            Map<ObjectId, Integer> numberOfBooks = searchReq.getNumberOfBooks();
            numberOfBooks.forEach((key, value) ->
                    query.addCriteria(Criteria.where("inventoryBookId").is(key).and("quantity").is(value)));
        }

        if (Objects.nonNull(searchReq.getDateOfReceiptFrom())) {
            if (Objects.nonNull(searchReq.getDateOfReceiptTo())
                    && searchReq.getDateOfReceiptTo().isAfter(searchReq.getDateOfReceiptFrom())) {
                query.addCriteria(Criteria.where("dateOfReceipt").gte(searchReq.getDateOfReceiptFrom())
                        .and("dateOfReceipt").lt(searchReq.getDateOfReceiptTo()));
            } else {
                query.addCriteria(Criteria.where("dateOfReceipt").gte(searchReq.getDateOfReceiptFrom()));
            }
        }

        return query;
    }

    @Override
    protected Class<StockBook> clazz() {
        return StockBook.class;
    }
}
