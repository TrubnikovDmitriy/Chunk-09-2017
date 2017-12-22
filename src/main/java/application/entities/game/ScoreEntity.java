package application.entities.game;

import application.models.game.field.Score;

import javax.persistence.*;


@Entity
@Table(name = "score")
public class ScoreEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreID;

    @Column(name = "result", nullable = false)
    private Double result;

    @Column(name = "user_id", nullable = false)
    private Long userID;

    ScoreEntity() { }

    public ScoreEntity(Score score) {
        this.result = score.getScore();
        this.userID = score.getUserID();
    }

    public Long getScoreID() {
        return scoreID;
    }

    public void setScoreID(Long scoreID) {
        this.scoreID = scoreID;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
}
