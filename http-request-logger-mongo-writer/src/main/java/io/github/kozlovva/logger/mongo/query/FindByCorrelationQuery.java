package io.github.kozlovva.logger.mongo.query;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

@RequiredArgsConstructor
public class FindByCorrelationQuery implements ExampleQuery {

    private final String correlationId;

    @Override
    public Query getQuery() {
        return Query.query(Criteria.where("correlationId").is(correlationId));
    }

}
