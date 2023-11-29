package stickhhero;

import javafx.animation.KeyFrame;
import javafx.scene.shape.*;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Box {
    private final int height = 300;
    private boolean firstTranslated;
    private Rectangle platform;

    private boolean secondTranslated;
    private Timeline secondTimeline;
    private Timeline firstTimeline;

    public Timeline getSecondTimeline() {
        return secondTimeline;
    }

    public void setSecondTimeline(Timeline secondTimeline) {
        this.secondTimeline = secondTimeline;
    }

    public Timeline getFirstTimeline() {
        return firstTimeline;
    }

    public void setFirstTimeline(Timeline firstTimeline) {
        this.firstTimeline = firstTimeline;
    }

    public Rectangle getPlatform() {
        return platform;
    }

    public void setPlatform(Rectangle platform) {
        this.platform = platform;
    }

    public Box() {
        this.firstTranslated = false;
        this.secondTranslated = false;
        this.platform = new Rectangle();
        this.platform.setHeight(height);
    }

    public static void copy(Box temp, Box main) {
//        temp.setWidth(main.getWidth());
//        temp.setLayoutX(main.getLayoutX());
//        temp.setLayoutY(main.getLayoutY());
        temp.getPlatform().setWidth(main.getPlatform().getWidth());
        temp.getPlatform().setLayoutX(main.getPlatform().getLayoutX());
        temp.getPlatform().setLayoutY(main.getPlatform().getLayoutY());
        temp.setFirstTranslated(main.isFirstTranslated());
        temp.setSecondTranslated(main.isSecondTranslated());
    }

    public boolean isSecondTranslated() {
        return secondTranslated;
    }

    public void setSecondTranslated(boolean secondTranslated) {
        this.secondTranslated = secondTranslated;
    }

    public boolean isFirstTranslated() {
        return firstTranslated;
    }

    public void setFirstTranslated(boolean firstTranslated) {
        this.firstTranslated = firstTranslated;
    }




    public int getHeight() {
        return height;
    }

    public void translateSecondRectangle(Player player) {
        secondTimeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    this.translateSecondNow(player);
                })
        );
        secondTimeline.setCycleCount(Timeline.INDEFINITE);
        secondTimeline.play();
    }


    private void translateSecondNow(Player player) {
        if (player.isPlayerTranslated()) {
            secondTimeline.stop();
            System.out.println("SECOND RECTANGLE TRANSLATING");
//            translateTransition = new TranslateTransition();
//            translateTransition.setNode(secondPlatform);
//            translateTransition.setDuration(Duration.millis(1000));
//            translateTransition.setByX(-secondRectangleTranslate);
//            translateTransition.play();
            secondTimeline = new Timeline(
                    new KeyFrame(Duration.millis(10), event -> {
                        this.startSecondTranslation(this.getPlatform());
                    })
            );
            secondTimeline.setCycleCount(Timeline.INDEFINITE);
            secondTimeline.play();
        }
    }

    private void startSecondTranslation(Rectangle secondPlatform) {
        if (secondPlatform.getLayoutX() <= 101.0 - secondPlatform.getWidth()) {
            secondTimeline.stop();
            this.setSecondTranslated(true);
            System.out.println("SECOND RECTANGLE TRANSLATED");
            return;
        }
        secondPlatform.setLayoutX(secondPlatform.getLayoutX() - 1.0);
    }


    public void translateFirstRectangle(Player player) {
        firstTimeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    this.translateFirstNow(this.getPlatform(), player);
                    //this.startFirstTranslation(firstPlatform, player);
                })
        );
        firstTimeline.setCycleCount(Timeline.INDEFINITE);
        firstTimeline.play();
    }

    private void translateFirstNow(Rectangle firstPlatform, Player player) {
        if (player.isPlayerTranslated()) {
            System.out.println("FIRST RECTANGLE TRANSLATING");
            firstTimeline.stop();
            //player.setPlayerTranslated(false);
            firstTimeline = new Timeline(
                    new KeyFrame(Duration.millis(1), event -> {
                        startFirstTranslation(firstPlatform, player);
                    })
            );
            firstTimeline.setCycleCount(Timeline.INDEFINITE);
            firstTimeline.play();
        }
    }

    private void startFirstTranslation(Rectangle firstPlatform,Player player) {
        if (firstPlatform.getLayoutX() <= -400.0) {
            System.out.println("FIRST RECTANGLE TRANSLATED");
            firstTimeline.stop();
            this.setFirstTranslated(true);
            return;
        }
        firstPlatform.setLayoutX(firstPlatform.getLayoutX() - 1.0);
    }
}
