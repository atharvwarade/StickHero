package stickhhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class Game {

    private Stage stage;
    private ImageView playerSkin;
    private Line line;
    private Stick stick;
    private Timeline timeline;
    private Box first;
    private Box second;
    private AnchorPane anchorPane;
    private Timeline newBoxTimeline;

    public Game() {

    }
    public Game(Stage stage, Player player) {
        this.stage = stage;
        this.playerSkin = player.getPlayerSkin();
        this.line = new Line();
        this.stick = new Stick(this.line);
        this.first = new Box();
        this.second = new Box();
        this.anchorPane = new AnchorPane();
    }


    public void startGameScene(Player player) {
        Scene scene = new Scene(anchorPane,500,400);
        Random random = new Random();
        createPlatform(first,100.0,0.0,200.0);
        //double secondLayoutX = 101 + (490 - 101)*(random.nextDouble());
        double secondLayoutX = first.getPlatform().getLayoutX() + 1 + first.getPlatform().getWidth() + (495 - (first.getPlatform().getLayoutX() + 1 + first.getPlatform().getWidth())) * random.nextDouble();
        double secondWidth = (secondLayoutX+1) + (495-secondLayoutX-1)*(random.nextDouble()) - (secondLayoutX);
        createPlatform(second,secondWidth,secondLayoutX,200.0);
        anchorPane.getChildren().addAll(first.getPlatform(),second.getPlatform(),playerSkin);
        anchorPane.getChildren().add(line);
        stage.setScene(scene);
        stage.show();
        this.startGameLoop(player,scene);
    }

    private void startGameLoop(Player player, Scene scene) {
        timeline = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            System.out.println("STARTING GAME");
            stick.extendLine(stage,player,scene);
            player.translateTimeline(stage,scene, stick, first, second);
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

    private void endGameScene() {
        timeline.pause();
    }

    public void createPlatform(Box box,double width, double layoutX,double layoutY) {
        box.getPlatform().setWidth(width);
        box.getPlatform().setLayoutX(layoutX);
        box.getPlatform().setLayoutY(layoutY);
    }
}
