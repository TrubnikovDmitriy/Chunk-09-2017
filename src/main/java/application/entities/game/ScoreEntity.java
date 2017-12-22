package application.entities.game;

import application.models.user.UserSignUp;
import application.models.user.UserUpdate;

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
}
