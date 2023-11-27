package stickhhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {

    private Stage stage;
    private static Player player;

    @Override
    public void start(Stage s) throws Exception {
        stage = s;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/firstScreen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root,500,400);
        stage.setTitle("StickHero");
        stage.setScene(scene);
        stage.show();
//        new Timeline(
//                new KeyFrame(Duration.millis(20), event -> {
//                    somefunction() // in this function at a later point the timeline will resume
//                    timeline.pause()
//                }
//                )
//        )
    }

    public void mainMenu(Stage s) {

    }

    @FXML
    public void startGameScreen(MouseEvent mouseEvent) throws IOException {
        Node temp = (Node)mouseEvent.getSource();
        stage = (Stage)temp.getScene().getWindow();
        Game controller = new Game(stage,player);
        controller.startGameScene(player);
    }

    public void endGameScreen() {

    }

    public static void main(String[] args) {
        player = new Player();
        launch(args);
    }

    public void musicChange(MouseEvent mouseEvent) {

    }

    public void mainMenu(MouseEvent mouseEvent) {
        mainMenu((Stage)((Node)mouseEvent.getSource()).getScene().getWindow());
    }
}
