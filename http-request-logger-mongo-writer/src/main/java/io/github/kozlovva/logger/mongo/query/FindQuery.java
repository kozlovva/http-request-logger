package io.github.kozlovva.logger.mongo.query;

import io.github.kozlovva.logger.commons.HttpLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class FindQuery implements ExampleQuery {

    private final HttpLogRepository.SearchFilters filters;

    @Override
    public Query getQuery() {
        List<Criteria> criteriaList = new ArrayList<>();
        if (filters.getHost() != null)
            criteriaList.add(Criteria.where("request.host").regex(filters.getHost(), "i"));
        if (filters.getMethod() != null)
            criteriaList.add(Criteria.where("request.method").is(filters.getMethod()));
        if (filters.getStatus() != null)
            criteriaList.add(Criteria.where("response.status").is(filters.getStatus()));
        if (filters.getDateRange() != null) {
            if (filters.getDateRange().getFrom() != null)
                criteriaList.add(Criteria.where("createdAt").gte(filters.getDateRange().getFrom()));
            if (filters.getDateRange().getTo() != null)
                criteriaList.add(Criteria.where("createdAt").lte(filters.getDateRange().getTo()));
        }

        return Query.query(new Criteria().andOperator(criteriaList.toArray(new Criteria[criteriaList.size()])));
    }

}
