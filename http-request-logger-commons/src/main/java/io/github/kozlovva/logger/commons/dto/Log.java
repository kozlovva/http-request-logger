package io.github.kozlovva.logger.commons.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Log {

    private String id;
    @Setter
    private Request request;
    @Setter
    private Response response;


    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public final static class Request {
        private String origin;
        private String type;
        private String protocol;
        private String remote;
        private String method;
        private String uri;
        private String host;
        private String path;
        private String scheme;
        private String port;
        private Map<String, List<String>> headers;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public final static class Response {
        private String origin;
        private Long duration;
        private Integer status;
        private String type;
        private String protocol;
        private Map<String, List<String>> headers;
        private Map<String, Object> body;
    }

}
