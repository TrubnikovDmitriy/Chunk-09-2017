package application.views.game.statuscode1xx;

import application.models.game.game.GamePrepare;
import application.services.game.GameSocketStatusCode;
import application.views.game.StatusCode;

import java.util.Collection;

public final class StatusCode111 extends StatusCode {

    private final Collection<GamePrepare> games;

    public StatusCode111(GameSocketStatusCode statusCode,
                         Collection<GamePrepare> games) {
        super(statusCode);
        this.games = games;
    }

    public Collection<GamePrepare> getGames() {
        return games;
    }
}