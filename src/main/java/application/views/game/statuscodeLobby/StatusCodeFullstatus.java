package application.views.game.statuscodeLobby;

import application.models.game.game.GamePrepare;
import application.services.game.GameSocketStatusCode;
import application.views.game.StatusCode;

import java.util.Collection;

public final class StatusCodeFullstatus extends StatusCode {

    private final Collection<GamePrepare> games;

    public StatusCodeFullstatus(Collection<GamePrepare> games) {
        super(GameSocketStatusCode.FULL_STATUS);
        this.games = games;
    }

    public Collection<GamePrepare> getGames() {
        return games;
    }
}
