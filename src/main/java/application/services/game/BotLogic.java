package application.services.game;

import application.models.game.field.Field;
import application.models.game.field.Spot;
import application.models.game.field.Step;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BotLogic {

    public static Step lowLogic(final Field field, Integer playerID) {

        final ArrayList<Spot> sourceSpots = field.getPlayerSpots(playerID);

        Spot src;
        ArrayList<Spot> destinationSpots;
        do {
            src = sourceSpots.get(GameTools.RANDOM.nextInt(sourceSpots.size()));
            destinationSpots = field.getPossiblePoints(src);
        }
        while (destinationSpots.isEmpty());

        final Spot dst = destinationSpots.get(GameTools.RANDOM.nextInt(destinationSpots.size()));
        return new Step(src, dst);
    }

    public static Step mediumLogic(final Field field, Integer playerID) {

        class StepAnalyze {

            StepAnalyze(Spot src, Spot dst) {
                this.src = src;
                this.dst = dst;
                this.assumed = field.getAssumedCount(dst, playerID);
                if (Math.abs(src.getCstX() - dst.getCstX()) < 2
                        && Math.abs(src.getCstY() - dst.getCstY()) < 2) {
                    ++this.assumed;
                }
            }

            private Spot src;
            private Spot dst;
            // Кол-во захваченных фигур
            private Integer assumed;

            public Integer getAssumed() {
                return assumed;
            }
        }

        // Жадный алгоритм
        final ArrayList<Spot> sourceSpots = field.getPlayerSpots(playerID);
        final ArrayList<StepAnalyze> steps = new ArrayList<>();

        for (Spot src : sourceSpots) {
            for (Spot dst : field.getPossiblePoints(src)) {
                steps.add(new StepAnalyze(src, dst));
            }
        }

        Collections.shuffle(steps, GameTools.RANDOM);

        final StepAnalyze max = steps.stream()
                .max(Comparator.comparing(StepAnalyze::getAssumed)).get();

        return new Step(max.src, max.dst);
    }

    public static Step highLogic(final Field field, Integer playerID) {

        class StepDeepAnalyze {

            StepDeepAnalyze(Spot src, Spot dst) {
                this.src = src;
                this.dst = dst;
                this.yourAssumed = field.getAssumedCount(dst, playerID);
                if (Math.abs(src.getCstX() - dst.getCstX()) < 2
                        && Math.abs(src.getCstY() - dst.getCstY()) < 2) {
                    ++this.yourAssumed;
                }
            }

            private Spot src;
            private Spot dst;
            // Кол-во захваченных фигур
            private Integer yourAssumed;
            private Integer enemyAssumed;

            public Integer getBenefit() {
                return yourAssumed - enemyAssumed;
            }

            public Step getStep() {
                return new Step(src, dst);
            }
        }

        final ArrayList<Spot> sourceSpots = field.getPlayerSpots(playerID);
        final ArrayList<StepDeepAnalyze> steps = new ArrayList<>();

        // Получаем все возможные ходы
        for (Spot src : sourceSpots) {
            for (Spot dst : field.getPossiblePoints(src)) {
                steps.add(new StepDeepAnalyze(src, dst));
            }
        }

        // Поросчитываем, сколько фигур в наилучшем случае захватит
        // слеующий игрок для каждого из наших шагов
        out: for (StepDeepAnalyze stepAnalyze : steps) {

            // Создаем эксперементальное поле и делаем на нем шаг
            final Field possbileField = new Field(field);
            possbileField.makeStep(stepAnalyze.getStep());

            // Получаем ID след незаблокированного игрока
            Integer nextEnemyID = playerID;
            do {
                nextEnemyID = possbileField.getNextID(nextEnemyID);
                if (nextEnemyID.equals(playerID)) {
                    stepAnalyze.enemyAssumed = 0;
                    continue out;
                }
            }
            while (possbileField.isBlocked(nextEnemyID));

            // Просчитываем его ход по "жадному алгоритму"
            final Step enemyStep = mediumLogic(possbileField, nextEnemyID);

            // Считаем сколько фигур он съест при таком варианте разивитя событий
            stepAnalyze.enemyAssumed = possbileField.getAssumedCount(
                    enemyStep.getDst(), nextEnemyID);
        }

        // Перемешиваем массив, чтобы одинаковые результаты выпадали по разному
        Collections.shuffle(steps, GameTools.RANDOM);
        // Высчитываем наилучший для нас вариант
        final StepDeepAnalyze max = steps.stream()
                .max(Comparator.comparing(StepDeepAnalyze::getBenefit))
                .get();

        return max.getStep();
    }
}
