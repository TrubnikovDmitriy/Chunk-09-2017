package application.views.game.lobby;

import application.services.game.GameSocketStatusCode;
import application.views.game.StatusCode;

public class StatusCodeChangeMaster extends StatusCode {

    private final Long masterID;
    private final String masterName;

    public StatusCodeChangeMaster(Long masterID, String masterName) {
        super(GameSocketStatusCode.CHANGE_MASTER);
        this.masterID = masterID;
        this.masterName = masterName;
    }
}