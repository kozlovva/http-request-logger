package io.github.kozlovva.logger.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kozlovva.logger.commons.HttpLogRepository;
import io.github.kozlovva.logger.commons.dto.Log;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class MongoHttpLogWriter implements HttpLogWriter {

    private final HttpLogRepository httpLogRepository;
    private final ObjectMapper objectMapper;
    @Override
    public void write(Precorrelation precorrelation, String request) throws IOException {
        log.debug(request);
        var parsedRequest = objectMapper.readValue(request, Log.Request.class);
        var logDoc = new Log();
        logDoc.setRequest(parsedRequest);
        httpLogRepository.save(precorrelation.getId(), logDoc);
    }

    @Override
    public void write(Correlation correlation, String response) throws IOException {
        log.debug(response);
        var parsedResponse = objectMapper.readValue(response, Log.Response.class);
        var logDoc = httpLogRepository.findLogByCorrelation(correlation.getId())
                .orElseGet(Log::new);
        logDoc.setResponse(parsedResponse);
        httpLogRepository.save(correlation.getId(), logDoc);
    }
}
