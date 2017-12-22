package application.views.game.information;

import application.models.game.game.GamePrepare;
import application.models.game.player.PlayerBot;
import application.models.game.player.PlayerGamer;

import java.util.ArrayList;
import java.util.Collection;


@SuppressWarnings("unused")
public final class GameInformationVerbose extends GameInformation {
    private final Long masterID;
    private final Collection<PlayerGamer> realPlayers;
    private final Collection<ViewPlayerBot> botPlayers;

    public GameInformationVerbose(GamePrepare game) {
        super(game);
        masterID = game.getMasterID();
        realPlayers = game.getGamers().values();
        botPlayers = new ArrayList<>();
        for (PlayerBot bot : game.getBots().values()) {
            botPlayers.add(new ViewPlayerBot(bot));
        }
    }


    public Long getMasterID() {
        return masterID;
    }

    public Collection<PlayerGamer> getRealPlayers() {
        return realPlayers;
    }

    public Collection<ViewPlayerBot> getBotPlayers() {
        return botPlayers;
    }

    private static class ViewPlayerBot {

        private final Long botID;
        private final String botname;
        private final Integer botlvl;

        ViewPlayerBot(PlayerBot bot) {
            this.botID = bot.getBotID();
            this.botname = bot.getUsername();
            this.botlvl = bot.getLevel();
        }

        public Long getBotID() {
            return botID;
        }

        public String getBotname() {
            return botname;
        }

        public Integer getBotlvl() {
            return botlvl;
        }
    }
}
