package by.lms.libraryms.repo.impl;

import by.lms.libraryms.domain.Inventory;
import by.lms.libraryms.domain.InventoryNumber;
import by.lms.libraryms.domain.InventoryNumberElement;
import by.lms.libraryms.domain.InventoryPrefixEnum;
import by.lms.libraryms.exceptions.BindingInventoryNumberException;
import by.lms.libraryms.exceptions.ObjectNotFound;
import by.lms.libraryms.exceptions.UnbindInventoryNumberException;
import by.lms.libraryms.repo.InventoryNumberRepo;
import by.lms.libraryms.repo.search.impl.AbstractSearchRepo;
import by.lms.libraryms.services.searchobjects.InventoryNumberSearchReq;
import by.lms.libraryms.utils.Constants;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public final class InventoryNumberRepoImpl extends AbstractSearchRepo<InventoryNumber, InventoryNumberSearchReq>
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
    public Inventory createNewNumber(@NonNull InventoryPrefixEnum prefix, @NonNull Inventory relatedObject) throws BindingInventoryNumberException {
        try {
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


            if (Objects.nonNull(result)) {
                List<InventoryNumberElement> numbers = result.getNumbers();
                InventoryNumberElement nextNumber = new InventoryNumberElement(numbers.getLast().number() + 1);
                List<InventoryNumberElement> newNumbersList = new ArrayList<>(numbers);
                newNumbersList.removeLast();
                newNumbersList.addLast(nextNumber);
                InventoryNumberElement[] newNumbers = newNumbersList.toArray(new InventoryNumberElement[0]);
                result = new InventoryNumber(prefix, nextNumber, newNumbers);

            } else {
                InventoryNumberElement number = new InventoryNumberElement(firstVal);
                result = new InventoryNumber(prefix, number);
            }

            result.setRelatedId(new ObjectId(relatedObject.getId()));
            relatedObject.setInventoryNumberId(new ObjectId(result.getId()));
            mongoTemplate().save(result);
            return relatedObject;
        } catch (Exception e) {
            throw new BindingInventoryNumberException(relatedObject, e.getLocalizedMessage());
        }
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
            if (!inventoryNumber.isRelated()) {
                inventoryNumber.setRelated(true);
                mongoTemplate().save(inventoryNumber);
            }
        }


        throw new UnsupportedOperationException(Constants.SAVE_OPERATION_NOT_SUPPORTED);
    }

    @Override
    public void unbind(@NonNull Inventory relatedObject) throws UnbindInventoryNumberException {
        Query query = new Query(Criteria.where("relatedId").is(relatedObject.getId()));
        InventoryNumber result = mongoTemplate().findOne(query, clazz());

        if (Objects.isNull(result))
            throw new ObjectNotFound("Inventory number for object: " + relatedObject + " not found");

        LocalDate updateDate = result.getUpdatedAt().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate now = LocalDate.now(ZoneId.systemDefault());

        if (!updateDate.equals(now)) {
            throw new UnbindInventoryNumberException("The unbinding period has expired! Inventory Number: " + result
                    + ", Related Id: " + result.getRelatedId());
        }

        ObjectId relatedId = new ObjectId(relatedObject.getId());
        if (!relatedId.equals(result.getRelatedId())) {
            throw new UnbindInventoryNumberException("The inventory number is linked to another object! Inventory Number: " + result
                    + ", related Id: " + result.getRelatedId() + ", the inventory number is linked to another object id: " + relatedId);
        }

        result.setRelated(false);
        result.setRelatedId(null);
        mongoTemplate().save(result);
    }
}
