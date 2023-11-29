package stickhhero;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage stage;
    private static Player player = new Player();

    @Override
    public void start(Stage s) throws Exception {
        stage = s;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/firstScreen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root,500,400);
        stage.setTitle("StickHero");
        stage.setScene(scene);
        stage.show();
    }

    public void mainMenu(Stage s) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/firstScreen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root,500,400);
        stage.setTitle("StickHero");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void startGameScreen(MouseEvent mouseEvent) throws IOException {
        player.setCoordinates();
        Node temp = (Node)mouseEvent.getSource();
        stage = (Stage)temp.getScene().getWindow();
        Game controller = new Game(stage,player, this);
        controller.startGameScene();
    }

    @FXML
    public void endGameScreen(Player curr_player) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gameOverScreen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root,500,400);
        stage.setTitle("GAME OVER");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        //player = new Player();
        launch(args);
    }

    public void musicChange(MouseEvent mouseEvent) {

    }

    public void mainMenu(MouseEvent mouseEvent) throws IOException {
        mainMenu((Stage)((Node)mouseEvent.getSource()).getScene().getWindow());
    }
}
