package application.models.game.field;

import application.entities.game.ScoreEntity;

public class Score {

    private final Long userID;
    private final Double score;

    public Score(Long userID, Double score) {
        this.userID = userID;
        this.score = score;
    }

    public Score(ScoreEntity entity) {
        this.userID = entity.getUserID();
        this.score = entity.getResult();
    }

    public Long getUserID() {
        return userID;
    }

    public Double getScore() {
        return score;
    }
}
