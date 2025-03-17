package by.lms.libraryms.repo.search.impl;

import by.lms.libraryms.domain.Author;
import by.lms.libraryms.repo.search.AuthorSearch;
import by.lms.libraryms.services.searchobjects.AuthorSearchReq;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Repository
public class AuthorSearchImpl extends AbstractSearchRepo<Author, AuthorSearchReq> implements AuthorSearch {

    public AuthorSearchImpl(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    protected Query addParams(@NotNull Query query, @NotNull AuthorSearchReq searchReq) {
        List<Criteria> criteriaList = new ArrayList<>();

        if (Objects.isNull(searchReq.getFullNames())) return query;
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

    @Override
    protected Class<Author> clazz() {
        return Author.class;
    }

}
