package by.lms.libraryms.repo.impl;

import by.lms.libraryms.domain.InventoryNumber;
import by.lms.libraryms.domain.InventoryPrefixEnum;
import by.lms.libraryms.repo.InventoryNumberRepo;
import by.lms.libraryms.repo.search.impl.AbstractSearchRepo;
import by.lms.libraryms.services.searchobjects.InventoryNumberSearchReq;
import by.lms.libraryms.utils.Constants;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public final class InventoryNumberRepoImpl extends AbstractSearchRepo<InventoryNumber, InventoryNumberSearchReq>
        implements InventoryNumberRepo {

    public InventoryNumberRepoImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    /**
     * Method for finding the last number with a given prefix.
     * Filter by prefix. Sort by size of the numbers list.
     * Take only one (first) result or not yet linked inventory number if it exists.
     *
     * @param prefix The prefix of inventory number.
     * @return next inventory number
     */
    @Override
    public InventoryNumber findLastNumber(@NonNull InventoryPrefixEnum prefix) {
        InventoryNumber result;

        Query query = new Query(Criteria.where("prefix").is(prefix).and("isRelated").is(false));
        List<InventoryNumber> existingNumbers = mongoTemplate().find(query, InventoryNumber.class);

        if (!existingNumbers.isEmpty()) {
            result = existingNumbers.getFirst();
        } else {
            Aggregation agg = Aggregation.newAggregation(
                    Aggregation.match(Criteria.where("prefix").is(prefix)),
                    Aggregation.sort(Sort.by(Sort.Order.desc("numbers.size()"))),
                    Aggregation.limit(1)
            );

            result = mongoTemplate().aggregate(agg, InventoryNumber.class, InventoryNumber.class)
                    .getUniqueMappedResult();
        }

        return result;
    }

    @Override
    public Optional<InventoryNumber> find(@NonNull InventoryNumber example) {
        Query query = new Query();
        query.addCriteria(Criteria.byExample(example));
        List<InventoryNumber> results = mongoTemplate().find(query, clazz());
        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }

    @Override
    public Optional<InventoryNumber> findById(@NonNull ObjectId id) {
        return Optional.ofNullable(mongoTemplate().findById(id, InventoryNumber.class));
    }

    @Override
    @NonNull
    public InventoryNumber save(@NonNull InventoryNumber entity) {

        if (Objects.isNull(entity.getId())) {
            return mongoTemplate().save(entity);
        }

        if (entity.getIsDisposedOf() && Objects.nonNull(entity.getDisposedDate())) {
            Query query = new Query(Criteria.where("id").is(entity.getId()).and("isDisposedOf").is(true));
            boolean exists = mongoTemplate().exists(query, InventoryNumber.class);

            if (!exists) {
                return mongoTemplate().save(entity);
            }
        } else {
            InventoryNumber inventoryNumber = findById(new ObjectId(entity.getId())).orElseThrow();
            LocalDate updateDate = inventoryNumber.getUpdatedAt().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate now = LocalDate.now(ZoneId.systemDefault());
            if (!inventoryNumber.isRelated() || updateDate.isEqual(now)) {
                mongoTemplate().save(entity);
            }
        }

        throw new UnsupportedOperationException(Constants.SAVE_OPERATION_NOT_SUPPORTED);
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
}
