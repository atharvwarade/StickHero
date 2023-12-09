package stickhhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import java.io.IOException;
import java.util.Random;

public class Game {

    private Stage stage;
    private Line line;
    private Stick stick;
    private Timeline timeline;
    private Box first;
    private Box second;
    private AnchorPane anchorPane;
    private Timeline newBoxTimeline;
    private Main main;
    private Player player;
    private Label label;
    private int currentScore;
    private ImageView cherry;
    private boolean cherryPresent;
    private Label cherryLabel;

//    public Game() {
//
//    }


    public Label getCherryLabel() {
        return cherryLabel;
    }

    public void setCherryLabel(Label cherryLabel) {
        this.cherryLabel = cherryLabel;
    }

    public ImageView getCherry() {
        return cherry;
    }

    public void setCherry(ImageView cherry) {
        this.cherry = cherry;
    }

    public boolean isCherryPresent() {
        return cherryPresent;
    }

    public void setCherryPresent(boolean cherryPresent) {
        this.cherryPresent = cherryPresent;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public Game(Stage stage, Player player, Main main) {
        this.stage = stage;
        this.player = player;
        this.line = new Line();
        this.stick = new Stick(this.line);
        this.first = new Box();
        this.second = new Box();
        this.anchorPane = new AnchorPane();
        this.main = main;
        this.label = new Label();
        this.cherry = new ImageView(new Image("file:C:\\Users\\athar\\OneDrive\\Desktop\\MemoryGame\\src\\main\\resources\\img\\cherry.png"));
        this.cherry.setFitWidth(50);
        this.cherry.setFitHeight(50);
        this.setCherryPresent(false);
        this.cherryLabel = new Label();
        label.setText(String.valueOf(0));
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Stick getStick() {
        return stick;
    }

    public void setStick(Stick stick) {
        this.stick = stick;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public Box getFirst() {
        return first;
    }

    public void setFirst(Box first) {
        this.first = first;
    }

    public Box getSecond() {
        return second;
    }

    public void setSecond(Box second) {
        this.second = second;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setAnchorPane(AnchorPane anchorPane) {
        this.anchorPane = anchorPane;
    }

    public Timeline getNewBoxTimeline() {
        return newBoxTimeline;
    }

    public void setNewBoxTimeline(Timeline newBoxTimeline) {
        this.newBoxTimeline = newBoxTimeline;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void startGameScene(int currentScore) {
        player.setCoordinates();
        label.setLayoutX(400.0);
        label.setText(String.valueOf(currentScore));
        cherryLabel.setLayoutX(200);
        cherryLabel.setText("Cherry : "+player.getCherries());
        Scene scene = new Scene(anchorPane,500,400);
        Random random = new Random();
        createPlatform(first,100.0,0.0,200.0);
        //double secondLayoutX = 101 + (490 - 101)*(random.nextDouble());
        double secondLayoutX = first.getPlatform().getLayoutX() + 1 + first.getPlatform().getWidth() + (450 - (first.getPlatform().getLayoutX() + 1 + first.getPlatform().getWidth())) * random.nextDouble();
        //double secondLayoutX = 120;
        double secondWidth = (secondLayoutX+1) + (450-secondLayoutX-1)*(random.nextDouble()) - (secondLayoutX);
        if (secondWidth <= player.getPlayerSkin().getFitWidth()) {
            secondWidth = player.getPlayerSkin().getFitWidth();
        }

        createPlatform(second,secondWidth,secondLayoutX,200.0);
        anchorPane.getChildren().addAll(first.getPlatform(),second.getPlatform(),this.player.getPlayerSkin(),label);
        anchorPane.getChildren().addAll(line,cherry,cherryLabel);
        Result result = JUnitCore.runClasses(TestClass.class);
        if (secondLayoutX-first.getPlatform().getLayoutX()-first.getPlatform().getWidth() > 100) {
            System.out.println(secondLayoutX-first.getPlatform().getLayoutX()+first.getPlatform().getWidth());
            System.out.println(secondLayoutX);
            System.out.println("CHERRY YES");
            this.cherry.setLayoutX((100+secondLayoutX)/2);
            this.cherry.setLayoutY(200);
            this.setCherryPresent(true);
            this.cherry.setVisible(true);
        }
        else {
            System.out.println("CHERRY NO");
            this.setCherryPresent(false);
            this.anchorPane.getChildren().remove(cherry);
        }
        stage.setScene(scene);
        stage.show();
        this.startGameLoop(player,scene);
    }

    private void startGameLoop(Player player, Scene scene) {
        timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            System.out.println("STARTING GAME");
            if (this.isCherryPresent() && !this.anchorPane.getChildren().contains(this.cherry)) {
                this.anchorPane.getChildren().add(this.cherry);
            }
            if (!this.isCherryPresent() && this.anchorPane.getChildren().contains(this.cherry)) {
                this.anchorPane.getChildren().remove(this.cherry);
            }
            stick.extendLine(stage,player,scene,first,second);
            player.translateTimeline(scene, stick, first, second, this);
            //second.translateSecondRectangle(player);
            Box.translate(second,"second",player);
            //first.translateFirstRectangle(player);
            Box.translate(first,"first",player);
            player.getBack(stick);
            createNewBoxTimeline(player);
        })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void createNewBoxTimeline(Player player) {
        timeline.pause();
        newBoxTimeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    createNewBox(player);
                })
        );
        newBoxTimeline.setCycleCount(Timeline.INDEFINITE);
        newBoxTimeline.play();
    }

    private void createNewBox(Player player) {
        if (first.isFirstTranslated() & player.isBackTranslated() && second.isSecondTranslated()) {
            newBoxTimeline.stop();
            first.setFirstTranslated(false);
            second.setSecondTranslated(false);
            player.setBackTranslated(false);
            Random random = new Random();
            double secondLayoutX = second.getPlatform().getLayoutX() + 25 + second.getPlatform().getWidth() + (495 - (second.getPlatform().getLayoutX() + 25 + second.getPlatform().getWidth())) * random.nextDouble();
            //double secondLayoutX = 120;
            double secondWidth = (secondLayoutX+1) + (495-secondLayoutX-1)*(random.nextDouble()) - (secondLayoutX);
            if (secondWidth <= player.getPlayerSkin().getFitWidth()) {
                secondWidth = player.getPlayerSkin().getFitWidth();
            }
            createPlatform(first,secondWidth,secondLayoutX,200.0);
            Box temp = new Box();
            Box.copy(temp,second);
            Box.copy(second,first);
            Box.copy(first,temp);
            if (secondLayoutX-first.getPlatform().getLayoutX()-first.getPlatform().getWidth() > 100) {
                this.cherry.setLayoutX((100+secondLayoutX)/2);
                this.cherry.setLayoutY(200);
                this.setCherryPresent(true);
                this.cherry.setVisible(true);
            }
            else {
                this.setCherryPresent(false);
            }
            timeline.play();
        }
    }

    public void endGameScene(Player player) throws IOException {
        timeline.stop();
        if (player.getTimeline() != null) {
            player.getTimeline().stop();
        }
        if (player.getBackTimeline() != null) {
            player.getBackTimeline().stop();
        }
        if (stick.getTimeline() != null) {
            stick.getTimeline().stop();
        }
        if (first.getFirstTimeline() != null) {
            first.getFirstTimeline().stop();
        }
        if (second.getSecondTimeline() != null) {
            second.getSecondTimeline().stop();
        }
        int currentValue = Integer.parseInt(this.getLabel().getText());
        player.resetPlayer();
        main.endGameScreen(player,currentValue);
    }

    public void createPlatform(Box box,double width, double layoutX,double layoutY) {
        box.getPlatform().setWidth(width);
        box.getPlatform().setLayoutX(layoutX);
        box.getPlatform().setLayoutY(layoutY);
    }

    public void cherryCollected() {
        this.cherry.setVisible(false);
    }


    public void reviveScreen() throws IOException {
        main.reviveScreen(this,this.currentScore);
    }
}
