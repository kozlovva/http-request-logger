package io.github.kozlovva.logger.mongo.query;

import org.springframework.data.mongodb.core.query.Query;

public interface ExampleQuery {

    Query getQuery();
}
