package application.dao.game;

import application.entities.game.ScoreEntity;
import application.models.game.field.Score;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;


@Repository
public class ScoreDaoJpa {

    private EntityManager em;

    ScoreDaoJpa(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void addScore(Score score) {
        final ScoreEntity scoreEntity = new ScoreEntity(score);
        em.persist(scoreEntity);
    }

    public List<Score> getBestScores(Integer limit) {
        final TypedQuery<ScoreEntity> query = em.createQuery(
                "SELECT s FROM ScoreEntity s ORDER BY s.result", ScoreEntity.class);

        query.setFirstResult(0);
        query.setMaxResults(limit);
        final  List<ScoreEntity> scoreEntityList = query.getResultList();

        final List<Score> scoreList = new ArrayList<>(scoreEntityList.size());
        scoreEntityList.forEach(scoreEntity -> scoreList.add(new Score(scoreEntity)));
        return scoreList;
    }
}
