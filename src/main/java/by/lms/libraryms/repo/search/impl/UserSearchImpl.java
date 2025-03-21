package by.lms.libraryms.repo.search.impl;

import by.lms.libraryms.domain.Address;
import by.lms.libraryms.domain.auth.User;
import by.lms.libraryms.repo.search.UserSearch;
import by.lms.libraryms.services.searchobjects.UserSearchReq;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserSearchImpl extends AbstractSearchRepo<User, UserSearchReq> implements UserSearch {
    public UserSearchImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public long delete(UserSearchReq searchReq) {
        List<User> users = super.find(searchReq);
        if (users.isEmpty())  return 0;

        List<ObjectId> userIds = users.stream()
                .map(User::getId)
                .map(ObjectId::new)
                .toList();

        Set<ObjectId> addressIds = users.stream()
                .flatMap(user -> user.getAddressIds().stream())
                .collect(Collectors.toSet());

        Query query = new Query(Criteria.where("id").in(addressIds));
        List<Address> addresses = mongoTemplate().find(query, Address.class);

        addresses.forEach(address -> address.getUserIds().removeIf(userIds::contains));

        if (!addresses.isEmpty()) {
            mongoTemplate().save(addresses, "addresses");
        }

        return super.delete(searchReq);
    }

    @Override
    protected Query addParams(Query query, UserSearchReq searchReq) {

        if (Objects.nonNull(searchReq.getUsernames()) && !searchReq.getUsernames().isEmpty()) {
            query.addCriteria(Criteria.where("usernames").in(searchReq.getUsernames()));
        }

        if (Objects.nonNull(searchReq.getEmails()) && !searchReq.getEmails().isEmpty()) {
            query.addCriteria(Criteria.where("emails").in(searchReq.getEmails()));
        }

        if (Objects.nonNull(searchReq.getFullNames()) && !searchReq.getFullNames().isEmpty()) {
            Map<String, String> fullNames = searchReq.getFullNames();
            for (Map.Entry<String, String> fullName : fullNames.entrySet()) {
                String name = fullName.getKey();
                String surname = fullName.getValue();
                if (name.equals(surname)) {
                    query.addCriteria(Criteria.where("name").is(name).orOperator(Criteria.where("surname").is(surname)));
                } else {
                    query.addCriteria(Criteria.where(name).in(name).and("surname").is(surname));
                }
            }
        }

        if (Objects.nonNull(searchReq.getAddressIds()) && !searchReq.getAddressIds().isEmpty()) {
            query.addCriteria(Criteria.where("addressId").in(searchReq.getAddressIds()));
        }

        if (Objects.nonNull(searchReq.getRoles()) && !searchReq.getRoles().isEmpty()) {
            query.addCriteria(Criteria.where("roles").in(searchReq.getRoles()));
        }

        return query;
    }

    @Override
    protected Class<User> clazz() {
        return User.class;
    }

    @Override
    protected boolean hasReferences(List<ObjectId> objectIds) {
        return super.hasReferences("readers", "userId", objectIds);
    }
}
