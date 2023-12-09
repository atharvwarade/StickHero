package stickhhero;

import javafx.animation.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import java.io.IOException;

// Singleton Class

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
    private boolean isTranslating;
    private boolean playerRotated;
    private Timeline cherryCollectTimeline;
    private static Player player = null;
    private Player() {
        playerSkin = new ImageView(new Image("file:C:\\Users\\athar\\OneDrive\\Desktop\\MemoryGame\\src\\main\\resources\\img\\character.png"));
        playerSkin.setFitWidth(50.0);
        playerSkin.setFitHeight(40.0);
        playerSkin.setLayoutX(50.0);
        playerSkin.setLayoutY(160.0);
        this.playerTranslated = false;
        this.backTranslated = false;
        this.playerRotated = false;
        this.cherries = 0;
    }

    public void resetPlayer() {
        playerSkin = new ImageView(new Image("file:C:\\Users\\athar\\OneDrive\\Desktop\\MemoryGame\\src\\main\\resources\\img\\character.png"));
        playerSkin.setFitWidth(50.0);
        playerSkin.setFitHeight(40.0);
        playerSkin.setLayoutX(50.0);
        playerSkin.setLayoutY(160.0);
        this.playerTranslated = false;
        this.backTranslated = false;
        this.playerRotated = false;
    }

    public static Player getInstance() {
        if (player == null) {
            return new Player();
        }
        return player;
    }

    public Timeline getCherryCollectTimeline() {
        return cherryCollectTimeline;
    }

    public void setCherryCollectTimeline(Timeline cherryCollectTimeline) {
        this.cherryCollectTimeline = cherryCollectTimeline;
    }

    public void setCoordinates() {
        playerSkin.setFitWidth(50.0);
        playerSkin.setFitHeight(40.0);
        playerSkin.setLayoutX(50.0);
        playerSkin.setLayoutY(160.0);
    }

    public boolean isPlayerRotated() {
        return playerRotated;
    }

    public void setPlayerRotated(boolean playerRotated) {
        this.playerRotated = playerRotated;
    }

    public boolean isTranslating() {
        return isTranslating;
    }

    public void setTranslating(boolean translating) {
        isTranslating = translating;
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

    public void translateTimeline(Scene scene, Stick stick, Box first, Box second, Game controller) {
        timeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    this.translateNow(stick, first, second, controller);
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SPACE && this.isTranslating) {
                this.playerRotate();
            }
        });
    }

    private void playerRotate() {
        if (this.isPlayerRotated()) {
            System.out.println("ULTA HAI");
            this.playerSkin.setLayoutY(this.playerSkin.getLayoutY() - this.playerSkin.getFitHeight());
            this.playerSkin.setScaleY(1);
            this.setPlayerRotated(false);
        }
        else {
            System.out.println("SIDHA HAI");
            this.playerSkin.setLayoutY(this.playerSkin.getLayoutY() + this.playerSkin.getFitHeight());
            this.playerSkin.setScaleY(-1);
            this.setPlayerRotated(true);
        }
    }

    private void translateNow(Stick stick, Box first, Box second, Game controller) {
        if (stick.isStickRotated()) {
            timeline.stop();
            System.out.println(second.getPlatform().getLayoutX() - stick.getLine().getStartX());
            if (first.getPlatform().getLayoutX() + first.getPlatform().getWidth() + stick.getLineLength() < second.getPlatform().getLayoutX()
                    || first.getPlatform().getLayoutX() + first.getPlatform().getWidth() + stick.getLineLength() > second.getPlatform().getLayoutX() + second.getPlatform().getWidth()) {
                //System.out.println("GIR GAYA");
                fallTranslation(stick, controller);
                return;
            }
            System.out.println("PLAYER TRANSITION STARTS");
            try {
                int currentValue = Integer.parseInt(controller.getLabel().getText());
                controller.getLabel().setText(String.valueOf(currentValue + 1));;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            stick.setStickRotated(false);

            timeline = new Timeline(
                    new KeyFrame(Duration.millis(10), event -> {
                        startTranslation(count,stick, controller,second);
                        count = count + 1.0;
                    })
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            this.isTranslating = true;
            if (!controller.isCherryPresent()) {
                return;
            }
            cherryCollectTimeline = new Timeline(
                    new KeyFrame(Duration.millis(10), event -> {
                        collectCherry(stick,controller);
                    })
            );
            cherryCollectTimeline.setCycleCount(Timeline.INDEFINITE);
            cherryCollectTimeline.play();
            //this.playerSkin.setTranslateX(stick.getLine().getEndX() - playerSkin.getX());

        }
    }

    private void collectCherry(Stick stick, Game controller) {
        if (!this.isTranslating) {
            cherryCollectTimeline.stop();
            return;
        }
        if (this.isPlayerRotated()) {
            if (this.playerSkin.getLayoutX() + this.playerSkin.getFitWidth() >= controller.getCherry().getLayoutX() &&
                    this.playerSkin.getLayoutX() + this.playerSkin.getFitWidth() <= controller.getCherry().getLayoutX() + controller.getCherry().getFitWidth()) {
                cherryCollectTimeline.stop();
                System.out.println("CHERRY COLLECTED");
                this.cherries+=1;
                controller.getCherryLabel().setText("CHERRIES : "+this.cherries);
                controller.cherryCollected();
            }
        }

    }

    private void fallTranslation(Stick stick,Game controller) {
        System.out.println("STARTING FALLING PROCEDURE");
        timeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    startHorizontalFallTranslation(stick, controller);
                    count+=1.0;
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        this.isTranslating = true;
    }

    private void startHorizontalFallTranslation(Stick stick,Game controller) {
        if (count >= stick.getLineLength()) {
            timeline.stop();
            this.isTranslating = false;
            this.count=0.0;
            System.out.println("FALLING HORIZONTAL COMPLETED");
            timeline = new Timeline(
                    new KeyFrame(Duration.millis(5), event -> {
                        try {
                            startVerticalFallTranslation(controller);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
            );
            timeline.setCycleCount(500);
            timeline.play();
        }
        this.playerSkin.setLayoutX(this.playerSkin.getLayoutX() + 1.0);
    }

    private void startVerticalFallTranslation(Game controller) throws IOException {
        if (this.getPlayerSkin().getLayoutY() >= 600.0) {
            if (this.cherries>=0) {
                timeline.stop();
                controller.reviveScreen();
                return;
            }
            controller.endGameScene(this);
            return;
        }
        this.getPlayerSkin().setLayoutY(this.getPlayerSkin().getLayoutY() + 1.0);
    }

    private void startTranslation(double count, Stick stick, Game controller, Box second) {
        if (count >= stick.getLineLength() + second.getPlatform().getWidth() - this.playerSkin.getFitWidth()) {
            timeline.stop();
            this.isTranslating = false;
            this.setPlayerTranslated(true);
            controller.setCurrentScore(controller.getCurrentScore()+1);
            stick.setLineCoordinates();
            stick.resetAngle();
            System.out.println(stick.getLineLength());
            System.out.println("PLAYER TRANSITION COMPLETED");
            this.count = 0.0;
            return;
        }
        if (this.isPlayerRotated()) {
            if (this.playerSkin.getLayoutX() + this.playerSkin.getFitWidth() >= controller.getSecond().getPlatform().getLayoutX()) {
                timeline.stop();
                fallTranslation(stick,controller);
                return;
            }
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
