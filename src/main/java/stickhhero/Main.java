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
    private Player player;

    @Override
    public void start(Stage s) throws Exception {
        player = new Player();
        stage = s;
        Player player = new Player();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/firstScreen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root,500,400);
        stage.setTitle("StickHero");
        stage.setScene(scene);
        stage.show();
    }

    public void mainMenu(Stage s) {

    }

    @FXML
    public void startGameScreen(MouseEvent mouseEvent) throws IOException {
        Node temp = (Node)mouseEvent.getSource();
        stage = (Stage)temp.getScene().getWindow();
        Game game = new Game(stage);
        game.startGame(player);
    }

    public void endGameScreen() {

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void musicChange(MouseEvent mouseEvent) {

    }

    public void mainMenu(MouseEvent mouseEvent) {
        mainMenu((Stage)((Node)mouseEvent.getSource()).getScene().getWindow());
    }
}
