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
    private double width;
    private double layoutX;
    private double layoutY;

    private boolean secondTranslated;
    private Timeline secondTimeline;
    private Timeline firstTimeline;

    public boolean isSecondTranslated() {
        return secondTranslated;
    }

    public void setSecondTranslated(boolean secondTranslated) {
        this.secondTranslated = secondTranslated;
    }

    public double getLayoutX() {
        return layoutX;
    }

    public void setLayoutX(double layoutX) {
        this.layoutX = layoutX;
    }

    public double getLayoutY() {
        return layoutY;
    }

    public void setLayoutY(double layoutY) {
        this.layoutY = layoutY;
    }

    public boolean isFirstTranslated() {
        return firstTranslated;
    }

    public void setFirstTranslated(boolean firstTranslated) {
        this.firstTranslated = firstTranslated;
    }

    public Box() {
        this.firstTranslated = false;
        this.secondTranslated = false;
    }
    public Box(double width) {
        this.width = width;
    }

//    public Rectangle getPlatform() {
//        return platform;
//    }
//
//    public void setPlatform(Rectangle box) {
//        this.platform = box;
//    }




    public int getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void translateSecondRectangle(Rectangle secondPlatform,double secondRectangleTranslate, Player player) {
        secondTimeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    //System.out.println("INITIALIZED");
                    this.translateSecondNow(secondPlatform,secondRectangleTranslate,player);
                })
        );
        secondTimeline.setCycleCount(Timeline.INDEFINITE);
        secondTimeline.play();
    }


    private void translateSecondNow(Rectangle secondPlatform, double secondRectangleTranslate, Player player) {
        if (player.isPlayerTranslated()) {
            System.out.println("HUHU");
            secondTimeline.stop();
//            translateTransition = new TranslateTransition();
//            translateTransition.setNode(secondPlatform);
//            translateTransition.setDuration(Duration.millis(1000));
//            translateTransition.setByX(-secondRectangleTranslate);
//            translateTransition.play();
            secondTimeline = new Timeline(
                    new KeyFrame(Duration.millis(10), event -> {
                        this.startSecondTranslation(secondPlatform);
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
            return;
        }
        secondPlatform.setLayoutX(secondPlatform.getLayoutX() - 1.0);
    }


    public void translateFirstRectangle(Rectangle firstPlatform, Box second, Player player) {
        firstTimeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    this.translateFirstNow(firstPlatform,second, player);
                    //this.startFirstTranslation(firstPlatform, player);
                })
        );
        firstTimeline.setCycleCount(Timeline.INDEFINITE);
        firstTimeline.play();
    }

    private void translateFirstNow(Rectangle firstPlatform, Box second, Player player) {
        if (player.isPlayerTranslated()) {
            firstTimeline.stop();
            //player.setPlayerTranslated(false);
            second.setSecondTranslated(false);
            firstTimeline = new Timeline(
                    new KeyFrame(Duration.millis(10), event -> {
                        startFirstTranslation(firstPlatform, player);
                    })
            );
            firstTimeline.setCycleCount(Timeline.INDEFINITE);
            firstTimeline.play();
        }
    }

    private void startFirstTranslation(Rectangle firstPlatform,Player player) {
        if (firstPlatform.getLayoutX() <= -100.0) {
            firstTimeline.stop();

            this.setFirstTranslated(true);
            return;
        }
        firstPlatform.setLayoutX(firstPlatform.getLayoutX() - 1.0);
    }
}
