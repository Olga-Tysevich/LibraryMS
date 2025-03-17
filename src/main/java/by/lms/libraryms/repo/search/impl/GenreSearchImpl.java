package by.lms.libraryms.repo.search.impl;

import by.lms.libraryms.domain.Genre;
import by.lms.libraryms.repo.search.GenreSearch;
import by.lms.libraryms.services.searchobjects.GenreSearchReq;
import jakarta.validation.constraints.NotNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class GenreSearchImpl extends AbstractSearchRepo<Genre, GenreSearchReq> implements GenreSearch {

    public GenreSearchImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    @Override
    protected Query addParams(@NotNull Query query, @NotNull GenreSearchReq searchReq) {

        if (Objects.isNull(searchReq.getNames())) return query;
        List<Criteria> criteriaList = searchReq.getNames().stream()
                .map(name -> Criteria.where("name").is(name))
                .toList();

        query.addCriteria(new Criteria().orOperator(criteriaList.toArray(new Criteria[0])));
        return query;
    }

    @Override
    protected Class<Genre> clazz() {
        return Genre.class;
    }

    @Override
    protected boolean hasReferences(List<ObjectId> objectIds) {
        return super.hasReferences("books", "genreIds", objectIds);
    }
}
