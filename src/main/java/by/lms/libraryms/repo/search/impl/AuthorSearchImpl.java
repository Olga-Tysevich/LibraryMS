package by.lms.libraryms.repo.search.impl;

import by.lms.libraryms.domain.Author;
import by.lms.libraryms.repo.search.AuthorSearch;
import by.lms.libraryms.services.searchobjects.AuthorReq;
import by.lms.libraryms.services.searchobjects.ListForPageResp;
import com.mongodb.client.result.DeleteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthorSearchImpl extends AbstractSearchRepo<Author, AuthorReq> implements AuthorSearch {
    private final MongoTemplate mongoTemplate;

    @Override
    public boolean delete(AuthorReq searchReq) {

        Query query = addParams(super.query(searchReq), searchReq);

        DeleteResult result = mongoTemplate.remove(query, Author.class);

        return result.getDeletedCount() > 0;
    }

    @Override
    public List<Author> find(AuthorReq searchReq) {

        Query query = addParams(super.query(searchReq), searchReq);

        return mongoTemplate.find(query, Author.class);
    }

    @Override
    public ListForPageResp<Author> findList(AuthorReq searchReq) {

        Query query = addParams(super.query(searchReq), searchReq);

        return findList(mongoTemplate, query, Author.class, searchReq);
    }

    private Query addParams(Query query, AuthorReq searchReq) {

        if (Objects.nonNull(searchReq.getName())) {
            query.addCriteria(Criteria.where("name").regex(searchReq.getName()));
        }

        if (Objects.nonNull(searchReq.getSurname())) {
            query.addCriteria(Criteria.where("surname").regex(searchReq.getSurname()));
        }

        return query;
    }
}
