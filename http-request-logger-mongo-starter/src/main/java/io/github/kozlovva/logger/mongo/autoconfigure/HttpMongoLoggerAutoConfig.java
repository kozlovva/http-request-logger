package io.github.kozlovva.logger.mongo.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kozlovva.logger.mongo.MongoHttpLogWriter;
import io.github.kozlovva.logger.mongo.repository.MongoLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.zalando.logbook.DefaultSink;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.autoconfigure.LogbookAutoConfiguration;
import org.zalando.logbook.json.JsonHttpLogFormatter;

@ConditionalOnClass(Logbook.class)
@Configuration
@AutoConfigureAfter(
        value = {JacksonAutoConfiguration.class, MongoAutoConfiguration.class},
        name = {
                "org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration", // Spring Boot 1.x
                "org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration" // Spring Boot 2.x
        })
@AutoConfigureBefore(value = LogbookAutoConfiguration.class)
@RequiredArgsConstructor
public class HttpMongoLoggerAutoConfig {

    @Bean
    public MongoLogRepository mongoLogRepository(MongoTemplate mongoTemplate) {
        return new MongoLogRepository(mongoTemplate);
    }

    @Bean
    public MongoHttpLogWriter mongoHttpLogWriter(MongoLogRepository mongoLogRepository, ObjectMapper objectMapper) {
        return new MongoHttpLogWriter(mongoLogRepository, objectMapper);
    }

    @Bean
    public Logbook logbook(final MongoHttpLogWriter writer) {
        return Logbook.builder()
                .sink(new DefaultSink(new JsonHttpLogFormatter(), writer))
                .build();
    }

}
