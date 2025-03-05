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

import java.util.List;
import java.util.Objects;

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

        if (Objects.nonNull(searchReq.getName())) {
            query.addCriteria(Criteria.where("name").regex(searchReq.getName()));
        }

        if (Objects.nonNull(searchReq.getSurname())) {
            query.addCriteria(Criteria.where("surname").regex(searchReq.getSurname()));
        }

        return query;
    }
}
