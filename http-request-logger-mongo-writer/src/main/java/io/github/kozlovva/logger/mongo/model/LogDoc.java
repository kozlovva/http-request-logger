package io.github.kozlovva.logger.mongo.model;

import io.github.kozlovva.logger.commons.dto.Log;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@CompoundIndexes({
        @CompoundIndex(def = "{'correlationId': 1}"),
        @CompoundIndex(def = "{'request.method': 1}"),
        @CompoundIndex(def = "{'request.host': 1}"),
        @CompoundIndex(def = "{'request.path': 1}"),
        @CompoundIndex(def = "{'request.method': 1, 'response.status': 1}")
})
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Data
@Document
public class LogDoc {
    private ObjectId id;
    private String correlationId;
    private Log.Request request;
    private Log.Response response;
    private Instant createdAt;

    public LogDoc(ObjectId id, String correlationId,  Log.Request request, Log.Response response) {
        this.id = id;
        this.correlationId = correlationId;
        this.request = request;
        this.response = response;
        this.createdAt = Instant.now();
    }
}
