package io.github.kozlovva.logger.mongo.mapper;

import io.github.kozlovva.logger.commons.dto.Log;
import io.github.kozlovva.logger.mongo.model.LogDoc;

public class LogDocMapper {

    public static Log map(LogDoc log) {
        return new Log(log.getId().toString(), log.getRequest(), log.getResponse());
    }

}
