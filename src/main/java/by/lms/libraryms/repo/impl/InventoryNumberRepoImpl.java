package by.lms.libraryms.repo.impl;

import by.lms.libraryms.domain.InventoryNumber;
import by.lms.libraryms.domain.InventoryNumberElement;
import by.lms.libraryms.domain.InventoryPrefixEnum;
import by.lms.libraryms.repo.InventoryNumberRepo;
import by.lms.libraryms.repo.search.impl.AbstractSearchRepo;
import by.lms.libraryms.services.searchobjects.InventoryNumberSearchReq;
import by.lms.libraryms.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class InventoryNumberRepoImpl extends AbstractSearchRepo<InventoryNumber, InventoryNumberSearchReq>
        implements InventoryNumberRepo {
    @Value("${inventory.number.firstVal}")
    private int firstVal;

    public InventoryNumberRepoImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    protected Query addParams(Query query, InventoryNumberSearchReq searchReq) {

        if (Objects.nonNull(searchReq.getPrefixes()) && !searchReq.getPrefixes().isEmpty()) {
            searchReq.getNumbers().forEach(n ->
                    query.addCriteria(Criteria.where("prefix").is(n))
            );
        }

        if (Objects.nonNull(searchReq.getNumbers()) && !searchReq.getNumbers().isEmpty()) {
            query.addCriteria(Criteria.where("numbers").in(searchReq.getPrefixes()));
        }

        if (Objects.nonNull(searchReq.getIsDisposedOf())) {
            query.addCriteria(Criteria.where("disposedOf").is(searchReq.getIsDisposedOf()));
        }

        if (Objects.nonNull(searchReq.getDisposedDateFrom())) {
            if (Objects.nonNull(searchReq.getDisposedDateTo())
                    && searchReq.getDisposedDateTo().isAfter(searchReq.getDisposedDateFrom())) {
                query.addCriteria(Criteria.where("disposedDate").gte(searchReq.getDisposedDateFrom())
                        .and("disposedDate").lt(searchReq.getDisposedDateTo()));
            } else {
                query.addCriteria(Criteria.where("disposedDate").gte(searchReq.getDisposedDateFrom()));
            }
        }

        return query;
    }


    @Override
    protected Class<InventoryNumber> clazz() {
        return InventoryNumber.class;
    }

    /**
     * Method for finding the last number with a given prefix.
     * Filter by prefix. Sort by size of the numbers list. Take only one (first) result.
     *
     * @param prefix The prefix of inventory number.
     * @return next inventory number
     */
    @Override
    public final InventoryNumber createNewNumber(InventoryPrefixEnum prefix) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("prefix").is(prefix)),
                Aggregation.sort(Sort.by(Sort.Order.desc("numbers.size()"))),
                Aggregation.limit(1)
        );

        InventoryNumber lastInventoryNumber = mongoTemplate().aggregate(agg, InventoryNumber.class, InventoryNumber.class)
                .getUniqueMappedResult();

        InventoryNumber result;

        if (Objects.nonNull(lastInventoryNumber)) {
            List<InventoryNumberElement> numbers = lastInventoryNumber.getNumbers();
            InventoryNumberElement nextNumber = new InventoryNumberElement(numbers.getLast().number() + 1);
            List<InventoryNumberElement> newNumbersList = new ArrayList<>(numbers);
            newNumbersList.removeLast();
            newNumbersList.addLast(nextNumber);
            InventoryNumberElement[] newNumbers = newNumbersList.toArray(new InventoryNumberElement[0]);
            result = new InventoryNumber(prefix, nextNumber, newNumbers);

            mongoTemplate().save(lastInventoryNumber);
        } else {
            InventoryNumberElement number = new InventoryNumberElement(firstVal);
            result = mongoTemplate().save(new InventoryNumber(prefix, number));
        }

        return result;
    }

    @Override
    public InventoryNumber find(InventoryNumber example) {
        Query query = new Query();
        query.addCriteria(Criteria.byExample(example));
        List<InventoryNumber> results = mongoTemplate().find(query, clazz());
        return results.isEmpty() ? null : results.getFirst();
    }

    @Override
    @NonNull
    public <S extends InventoryNumber> S save(@NonNull S entity) {
        if (entity.getIsDisposedOf() && Objects.nonNull(entity.getDisposedDate())) {
            Query query = new Query(Criteria.where("id").is(entity.getId()).and("isDisposedOf").is(true));
            boolean exists = mongoTemplate().exists(query, InventoryNumber.class);

            if (!exists) {
                return mongoTemplate().save(entity);
            }
        }
        throw new UnsupportedOperationException(Constants.SAVE_OPERATION_NOT_SUPPORTED);
    }
}
