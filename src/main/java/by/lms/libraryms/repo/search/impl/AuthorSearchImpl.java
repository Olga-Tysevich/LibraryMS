package by.lms.libraryms.repo.search.impl;

import by.lms.libraryms.domain.Author;
import by.lms.libraryms.repo.search.AuthorSearch;
import by.lms.libraryms.services.searchobjects.AuthorSearchReq;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import com.mongodb.client.result.DeleteResult;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class AuthorSearchImpl extends AbstractSearchRepo<Author, AuthorSearchReq> implements AuthorSearch {

    public AuthorSearchImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    public boolean delete(AuthorSearchReq searchReq) {

        Query query = addParams(super.query(searchReq), searchReq);

        DeleteResult result = mongoTemplate().remove(query, Author.class);

        return result.getDeletedCount() > 0;
    }

    @Override
    public List<Author> find(AuthorSearchReq searchReq) {

        Query query = addParams(super.query(searchReq), searchReq);

        return mongoTemplate().find(query, Author.class);
    }

    @Override
    public ListForPageResp<Author> findList(AuthorSearchReq searchReq) {

        Query query = addParams(super.query(searchReq), searchReq);

        return findList(mongoTemplate(), query, Author.class, searchReq);
    }
    private Query addParams(Query query, AuthorSearchReq searchReq) {
        List<Criteria> criteriaList = new ArrayList<>();

        searchReq.getFullNames().stream()
                .filter(name -> !name.isBlank())
                .map(name -> Arrays.asList(name.trim().split("\\s+")))
                .forEach(parts -> {
                    if (parts.size() == 2) {
                        criteriaList.add(new Criteria()
                                .andOperator(
                                        Criteria.where("name").regex(parts.get(0), "i"),
                                        Criteria.where("surname").regex(parts.get(1), "i")
                                ));
                    } else {
                        List<Criteria> subCriteria = parts.stream()
                                .map(part -> new Criteria().orOperator(
                                        Criteria.where("name").regex(part, "i"),
                                        Criteria.where("surname").regex(part, "i")
                                ))
                                .toList();

                        criteriaList.add(new Criteria().orOperator(subCriteria.toArray(new Criteria[0])));
                    }
                });

        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().orOperator(criteriaList.toArray(new Criteria[0])));
        }

        return query;
    }

}
