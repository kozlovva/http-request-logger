package io.github.kozlovva.logger.mongo.repository;

import io.github.kozlovva.logger.commons.HttpLogRepository;
import io.github.kozlovva.logger.commons.dto.Log;
import io.github.kozlovva.logger.mongo.mapper.LogDocMapper;
import io.github.kozlovva.logger.mongo.model.LogDoc;
import io.github.kozlovva.logger.mongo.query.FindByCorrelationQuery;
import io.github.kozlovva.logger.mongo.query.FindQuery;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ConditionalOnBean({MongoTemplate.class})
@RequiredArgsConstructor
public class MongoLogRepository implements HttpLogRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void save(String correlation, Log log) {
        var id = log.getId()!= null ? new ObjectId(log.getId()) : new ObjectId();
        var logDoc = new LogDoc(id, correlation, log.getRequest(), log.getResponse());
        mongoTemplate.save(logDoc);
    }

    @Override
    public Optional<Log> findLogByCorrelation(String correlationId) {
        return Optional.ofNullable(mongoTemplate.findOne(new FindByCorrelationQuery(correlationId).getQuery(), LogDoc.class))
                .map(LogDocMapper::map);
    }

    @Override
    public List<Log> find(SearchFilters filters) {
        var query = new FindQuery(filters);
        return mongoTemplate.find(query.getQuery(), LogDoc.class)
                .stream()
                .map(LogDocMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public void removeAll() {
        mongoTemplate.remove(LogDoc.class);
    }

    @Override
    public void remove(SearchFilters.DateRange dateRange) {

    }


}
