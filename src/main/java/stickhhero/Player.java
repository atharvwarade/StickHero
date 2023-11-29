package stickhhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Player {

    private int highScore;
    private int cherries; // this will track total cherries ab tak ki;
    private ImageView playerSkin;
    private Timeline timeline;
    private TranslateTransition translateTransition;
    private Timeline backTimeline;
    private boolean playerTranslated;
    private double count;
    private boolean backTranslated;
    public Player() {
        playerSkin = new ImageView(new Image("file:C:\\Users\\athar\\OneDrive\\Desktop\\MemoryGame\\src\\main\\resources\\img\\character.png"));
        playerSkin.setFitWidth(50.0);
        playerSkin.setFitHeight(40.0);
        playerSkin.setLayoutX(50.0);
        playerSkin.setLayoutY(160.0);
        this.playerTranslated = false;
        this.backTranslated = false;
    }

    public void setCoordinates() {
        playerSkin.setFitWidth(50.0);
        playerSkin.setFitHeight(40.0);
        playerSkin.setLayoutX(50.0);
        playerSkin.setLayoutY(160.0);
    }

    public boolean isBackTranslated() {
        return backTranslated;
    }

    public void setBackTranslated(boolean backTranslated) {
        this.backTranslated = backTranslated;
    }

    public Timeline getBackTimeline() {
        return backTimeline;
    }

    public void setBackTimeline(Timeline backTimeline) {
        this.backTimeline = backTimeline;
    }

    public TranslateTransition getTranslateTransition() {
        return translateTransition;
    }

    public void setTranslateTransition(TranslateTransition translateTransition) {
        this.translateTransition = translateTransition;
    }

    public boolean isPlayerTranslated() {
        return playerTranslated;
    }

    public void setPlayerTranslated(boolean playerTranslated) {
        this.playerTranslated = playerTranslated;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getCherries() {
        return cherries;
    }

    public void setCherries(int cherries) {
        this.cherries = cherries;
    }

    public ImageView getPlayerSkin() {
        return playerSkin;
    }

    public void setPlayerSkin(ImageView playerSkin) {
        this.playerSkin = playerSkin;
    }

    public void translateTimeline(Stage stage, Scene scene, Stick stick, Box first, Box second, Game controller) {
        timeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    this.translateNow(stage,scene,stick, first, second, controller);
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    private void translateNow(Stage stage, Scene scene, Stick stick, Box first, Box second, Game controller) {
        if (stick.isStickRotated()) {
            timeline.stop();
            System.out.println(second.getPlatform().getLayoutX() - stick.getLine().getStartX());
            if (first.getPlatform().getLayoutX() + first.getPlatform().getWidth() + stick.getLineLength() < second.getPlatform().getLayoutX()
            || first.getPlatform().getLayoutX() + first.getPlatform().getWidth() + stick.getLineLength() > second.getPlatform().getLayoutX() + second.getPlatform().getWidth()) {
                //System.out.println("GIR GAYA");
                fallTranslation(stage, stick, first, second, controller);
                return;
            }
            System.out.println("PLLAYER TRANSITION STARTS");
            stick.setStickRotated(false);

            timeline = new Timeline(
                    new KeyFrame(Duration.millis(10), event -> {
                        startTranslation(count,stick, controller);
                        count = count + 1.0;
                    })
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            //this.playerSkin.setTranslateX(stick.getLine().getEndX() - playerSkin.getX());

        }
    }

    private void fallTranslation(Stage stage, Stick stick, Box first, Box second, Game controller) {
        System.out.println("STARTING FALLING PROCEDURE");
        timeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                           startHorizontalFallTranslation(stage,stick, controller);
                           count+=1.0;
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void startHorizontalFallTranslation(Stage stage, Stick stick, Game controller) {
        if (count >= stick.getLineLength()) {
            timeline.stop();
            this.count=0.0;
            System.out.println("FALLING HORIZONTAL COMPLETED");
            timeline = new Timeline(
                    new KeyFrame(Duration.millis(5), event -> {
                        try {
                            startVerticalFallTranslation(stage, stick, controller);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
        this.playerSkin.setLayoutX(this.playerSkin.getLayoutX() + 1.0);
    }

    private void startVerticalFallTranslation(Stage stage, Stick stick, Game controller) throws IOException {
        if (this.getPlayerSkin().getLayoutY() >= 600.0) {
            controller.endGameScene(this);
            return;
        }
        this.getPlayerSkin().setLayoutY(this.getPlayerSkin().getLayoutY() + 1.0);
    }

    private void startTranslation(double count, Stick stick, Game controller) {
        if (count >= stick.getLineLength()) {
            timeline.stop();
            this.setPlayerTranslated(true);
            stick.setLineCoordinates();
            System.out.println(stick.getLineLength());
            System.out.println("PLAYER TRANSITION COMPLETED");
            this.count = 0.0;
            return;
        }
        this.playerSkin.setLayoutX(this.playerSkin.getLayoutX() + 1.0);
    }

    public void getBack(Stick stick) {

        backTimeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    getBackNow(stick);
                })
        );
        backTimeline.setCycleCount(Timeline.INDEFINITE);
        backTimeline.play();
    }

    private void getBackNow(Stick stick) {
        if (this.isPlayerTranslated()) {
            backTimeline.stop();
            System.out.println("BACK TRANSLATION STARTED");
            this.setPlayerTranslated(false);
//            translateTransition = new TranslateTransition();
//            translateTransition.setNode(this.getPlayerSkin());
//            translateTransition.setByX(-stick.getLineLength());
//            translateTransition.setDuration(Duration.millis(1000));
//            translateTransition.play();
            backTimeline = new Timeline(
                    new KeyFrame(Duration.millis(10), event -> {
                        startBackTranslation();
                    })
            );
            backTimeline.setCycleCount(Timeline.INDEFINITE);
            backTimeline.play();
        }
    }

    private void startBackTranslation() {
        if (this.playerSkin.getLayoutX() <= 100.0 - this.playerSkin.getFitWidth()) {
            System.out.println("BACK TRANSLATION COMPLETED");
            backTimeline.stop();
            this.setBackTranslated(true);
            return;
        }
        this.playerSkin.setLayoutX(this.playerSkin.getLayoutX()-1.0);
    }
}
