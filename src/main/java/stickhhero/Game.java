package stickhhero;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class Game {

    private Stage stage;
    private Rectangle firstPlatform;
    private Rectangle secondPlatform;
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
        this.firstPlatform = new Rectangle();
        this.secondPlatform = new Rectangle();
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
        createPlatform(first,firstPlatform,100.0,0.0,200.0);
        //double secondLayoutX = 101 + (490 - 101)*(random.nextDouble());
        double secondLayoutX = firstPlatform.getLayoutX() + 1 + firstPlatform.getWidth() + (495 - (firstPlatform.getLayoutX() + 1 + firstPlatform.getWidth())) * random.nextDouble();
        double secondWidth = (secondLayoutX+1) + (495-secondLayoutX-1)*(random.nextDouble()) - (secondLayoutX);
        createPlatform(second,secondPlatform,secondWidth,secondLayoutX,200.0);
        anchorPane.getChildren().addAll(firstPlatform,secondPlatform,playerSkin);
        anchorPane.getChildren().add(line);
        stage.setScene(scene);
        stage.show();
        this.startGameLoop(player,scene);
    }

    private void startGameLoop(Player player, Scene scene) {
//        timeline = new Timeline(new KeyFrame(Duration.millis(16), event -> {
//            stick.extendLine(stage,player,scene);
//        })
//        );
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();
        stick.extendLine(stage,player,scene);
        System.out.println(stick.getLine().getEndX());
        player.translateTimeline(stage,scene, stick);
        double secondRectangleTranslate = (secondPlatform.getLayoutX() + secondPlatform.getWidth() - firstPlatform.getWidth() - firstPlatform.getLayoutX());
        second.translateSecondRectangle(secondPlatform,secondRectangleTranslate,player);
        first.translateFirstRectangle(firstPlatform,second,player);
        firstPlatform.setFill(Color.rgb(0,0,0,0.5));
        player.getBack(stick);
//        Box temp = new Box();
//        first = second;
//        firstPlatform = secondPlatform;
//        double secondLayoutX = firstPlatform.getLayoutX() + 1 + firstPlatform.getWidth() + (495 - (firstPlatform.getLayoutX() + 1 + firstPlatform.getWidth())) * random.nextDouble();
//        double secondWidth = (secondLayoutX+1) + (495-secondLayoutX-1)*(random.nextDouble()) - (secondLayoutX);
        //createNewBoxTimeline();
        //firstPlatform.setFill(Color.rgb(0,0,0,0.5));

//        double playerDistance = (line.getEndX() - playerSkin.getX());
//        playerSkin.setTranslateX(playerDistance);

    }

    private void createNewBoxTimeline() {
        newBoxTimeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    createNewBox();
                })
        );
        newBoxTimeline.setCycleCount(Timeline.INDEFINITE);
        newBoxTimeline.play();
    }

    private void createNewBox() {
//        if (second.getTranslateTransition() == null) {
//            return;
//        }
//        if (second.getTranslateTransition().getStatus() != Timeline.Status.RUNNING) {
//            System.out.println("GAGA");
//            Random random = new Random();
//            newBoxTimeline.stop();
//            Box temp = new Box();
//            double secondLayoutX = secondPlatform.getLayoutX() + 1 + 100.0 + (495 - (secondPlatform.getLayoutX() + 1 + 100.0)) * random.nextDouble();
//            double secondWidth = (secondLayoutX+1) + (495-secondLayoutX-1)*(random.nextDouble()) - (secondLayoutX);
//            System.out.println(secondLayoutX);
//            System.out.println(secondWidth);
//            createPlatform(temp,firstPlatform,100.0,300.0,200.0);
//        }
    }

    private void endGameScene() {
        timeline.pause();
    }

    public void createPlatform(Box box, Rectangle platform,double width, double layoutX,double layoutY) {
        this.updateBox(box,width,layoutX,layoutY);
        this.setRectangle(box,platform);
    }


    private void setRectangle(Box box, Rectangle platform) {
        platform.setHeight(box.getHeight());
        platform.setWidth(box.getWidth());
        platform.setLayoutX(box.getLayoutX());
        platform.setLayoutY(box.getLayoutY());
    }

    public void updateBox(Box box, double width, double layoutX, double layoutY) {
        box.setWidth(width);
        box.setLayoutX(layoutX);
        box.setLayoutY(layoutY);

    }
}
