package application.models.game.game;

import application.models.game.field.Step;
import application.models.game.player.PlayerAbstractActive;
import application.models.game.player.PlayerBot;
import application.models.game.player.PlayerGamer;
import application.services.game.GameSocketStatusCode;
import application.services.game.GameTools;
import application.views.game.StatusCode;
import application.views.game.statuscode2xx.StatusCode200;
import application.views.game.statuscode2xx.StatusCode201;
import application.views.game.statuscode2xx.StatusCode204;
import application.views.game.statuscode2xx.StatusCode2xx;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public final class GameActive extends GameAbstract {

    private Integer currentPlayerID;
    private final ConcurrentHashMap<Integer /*playerID*/, PlayerAbstractActive> gamers;
    private Boolean gameOver;

    public GameActive(GamePrepare prepared) {
        super(prepared.getGameID(), prepared.getField(),
                prepared.getNumberOfPlayers(), prepared.getHashMapOfWatchers());

        this.gamers = new ConcurrentHashMap<>(getNumberOfPlayers());

        Integer playerIdIncrement = GameTools.PLAYER_1;
        for (PlayerGamer gamer : prepared.getGamers()) {
            gamer.setPlayerID(playerIdIncrement);
            gamers.put(playerIdIncrement++, gamer);
        }
        for (PlayerBot bot : prepared.getBots()) {
            bot.setPlayerID(playerIdIncrement);
            gamers.put(playerIdIncrement++, bot);
        }

        this.getField().initialize(getNumberOfPlayers());
        this.currentPlayerID = GameTools.PLAYER_1;
        this.gameOver = false;

        notifyPlayers(new StatusCode200(this));
    }

    public synchronized Boolean makeStep(Step step) {

        if (!getField().getPlayerInPoint(step.getSrc()).equals(currentPlayerID)) {
            return false;
        }

        if (!getField().makeStep(step)) {
            return false;
        }

        notifyPlayers(new StatusCode201(step));

        if (getField().isGameOver()) {
            this.end();
            this.gameOver = true;
            return false;
        }

        while (true) {
            currentPlayerID = (currentPlayerID + 1) % (getNumberOfPlayers() + 1);
            if (currentPlayerID == 0) {
                currentPlayerID = GameTools.PLAYER_1;
            }
            if (!getField().isBlocked(currentPlayerID)) {
                break;
            }
            notifyPlayers(new StatusCode2xx(
                    GameSocketStatusCode.BLOCKED, gamers.get(currentPlayerID)));
        }

        if (gamers.get(currentPlayerID) instanceof PlayerBot) {

            final PlayerBot bot = (PlayerBot) gamers.get(currentPlayerID);
            this.makeStep(bot.generateStep(getField()));
        }
        return true;
    }

    public synchronized void playerOff(Long userID) {
        gamers.values().forEach(gamer -> {
            if (gamer.getUserID().equals(userID)) {
                gamer.switchOff();
                notifyPlayers(new StatusCode2xx(
                        GameSocketStatusCode.PLAYER_OFF, gamer));
            }
        });
    }

    public synchronized void playerOff(PlayerGamer player) {
        player.switchOff();
        new StatusCode2xx(GameSocketStatusCode.PLAYER_OFF, player);
    }

    @Override
    // Нужно ли делать synchronized?
    void notifyPlayers(StatusCode statusCode) {
        gamers.values().forEach(gamer -> {
            if (gamer.getUserID() != null) {
                if (gamer.getOnline() && gamer.getSession().isOpen()) {
                    this.sendMessageToPlayer(gamer, statusCode);
                } else {
                    this.playerOff((PlayerGamer) gamer);
                }
            }
        });
        getHashMapOfWatchers().values().forEach(watcher -> {
            if (watcher.getSession().isOpen()) {
                this.sendMessageToPlayer(watcher, statusCode);
            } else {
                getHashMapOfWatchers().remove(watcher.getUserID());
            }
        });
    }

    private void end() {
        notifyPlayers(new StatusCode204(getField()));

        getHashMapOfWatchers().clear();

        gamers.values().forEach(gamer -> {
            if (gamer instanceof PlayerGamer) {
                gamer.getSession().getAttributes().remove(GameTools.GAME_ID_ATTR);
            }
        });
        gamers.clear();
    }

    @JsonIgnore
    public boolean getGameOver() {
        return gameOver;
    }

    @JsonIgnore
    public Long getCurrentUserID() {
        return gamers.get(currentPlayerID).getUserID();
    }

    public Integer getCurrentPlayerID() {
        return currentPlayerID;
    }

    public Collection<PlayerAbstractActive> getGamers() {
        return gamers.values();
    }
}
