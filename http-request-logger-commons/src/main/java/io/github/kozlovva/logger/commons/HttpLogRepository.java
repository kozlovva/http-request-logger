package io.github.kozlovva.logger.commons;

import io.github.kozlovva.logger.commons.dto.Log;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface HttpLogRepository {

    void save(String correlationId, Log log);

    Optional<Log> findLogByCorrelation(String correlationId);

    List<Log> find(SearchFilters filters);

    void removeAll();

    void remove(SearchFilters.DateRange dateRange);

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    class SearchFilters {

        private DateRange dateRange;
        private Long limit = 20L;
        private Long offset = 0L;
        private Integer status;
        private String method;
        private String query;
        private String host;
        private SortDirection sortDirection = SortDirection.DESC;

        @Value
        public static class DateRange {
            Instant from;
            Instant to;

            public static DateRange fromNow(Instant to) {
                return new DateRange(Instant.now(), to);
            }
        }

        public enum SortDirection {
            ASC, DESC
        }
    }

}
