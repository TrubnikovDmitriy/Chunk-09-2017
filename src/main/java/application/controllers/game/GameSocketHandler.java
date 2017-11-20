package application.controllers.game;

import application.views.game.StatusCode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public abstract class GameSocketHandler {

    public abstract void controller(Integer code, JsonNode jsonNode, WebSocketSession session);

    protected final String toJSON(StatusCode statusCode) {
        try {
            return this.mapper.writeValueAsString(statusCode);
        } catch (IOException e) {
            gameLogger.error(e.getMessage(), e.getCause());
            return null;
        }
    }

    protected final synchronized void sendMessage(final WebSocketSession session,
                                                  String payload) {
        try {
            session.sendMessage(new TextMessage(payload));
        } catch (IOException e) {
            gameLogger.error(e.getMessage(), e.getCause());
        }
    }

    public abstract void emergencyDiconnect(WebSocketSession session, Long userID, Long gameID);

    protected final Logger getGameLogger() {
        return gameLogger;
    }

    private final Logger gameLogger = LoggerFactory.getLogger(GameSocketHandler.class);

    @Autowired
    @Qualifier("mymapper")
    protected final ObjectMapper mapper = new ObjectMapper();
}