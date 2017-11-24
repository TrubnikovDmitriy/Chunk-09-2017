package application.exceptions.game;

import org.springframework.web.socket.WebSocketSession;


public final class GameException extends RuntimeException {

    final WebSocketSession session;
    final String payload;

    public GameException(WebSocketSession session, String payload) {
        this.payload = payload;
        this.session = session;
    }

    public WebSocketSession getSession() {
        return session;
    }

    public String getPayload() {
        return payload;
    }
}
