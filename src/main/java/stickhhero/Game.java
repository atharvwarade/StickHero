package stickhhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

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

//    public Game() {
//
//    }
    public Game(Stage stage, Player player, Main main) {
        this.stage = stage;
        this.player = player;
        this.line = new Line();
        this.stick = new Stick(this.line);
        this.first = new Box();
        this.second = new Box();
        this.anchorPane = new AnchorPane();
        this.main = main;
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

    public void startGameScene() {
        Scene scene = new Scene(anchorPane,500,400);
        Random random = new Random();
        createPlatform(first,100.0,0.0,200.0);
        //double secondLayoutX = 101 + (490 - 101)*(random.nextDouble());
        double secondLayoutX = first.getPlatform().getLayoutX() + 1 + first.getPlatform().getWidth() + (495 - (first.getPlatform().getLayoutX() + 1 + first.getPlatform().getWidth())) * random.nextDouble();
        double secondWidth = (secondLayoutX+1) + (495-secondLayoutX-1)*(random.nextDouble()) - (secondLayoutX);
        createPlatform(second,secondWidth,secondLayoutX,200.0);
        anchorPane.getChildren().addAll(first.getPlatform(),second.getPlatform(),this.player.getPlayerSkin());
        anchorPane.getChildren().add(line);
        stage.setScene(scene);
        stage.show();
        this.startGameLoop(player,scene);
    }

    private void startGameLoop(Player player, Scene scene) {
        timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            System.out.println("STARTING GAME");
            stick.extendLine(stage,player,scene,first,second);
            player.translateTimeline(stage,scene, stick, first, second, this);
            second.translateSecondRectangle(player);
            first.translateFirstRectangle(player);
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
            double secondWidth = (secondLayoutX+1) + (495-secondLayoutX-1)*(random.nextDouble()) - (secondLayoutX);
            createPlatform(first,secondWidth,secondLayoutX,200.0);
            Box temp = new Box();
            Box.copy(temp,second);
            Box.copy(second,first);
            Box.copy(first,temp);
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

        main.endGameScreen(player);
    }

    public void createPlatform(Box box,double width, double layoutX,double layoutY) {
        box.getPlatform().setWidth(width);
        box.getPlatform().setLayoutX(layoutX);
        box.getPlatform().setLayoutY(layoutY);
    }
}
