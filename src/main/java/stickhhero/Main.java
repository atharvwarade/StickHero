package stickhhero;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    private Stage stage;
    private static Player player = Player.getInstance();

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
        controller.startGameScene(0);
    }

    @FXML
    public void endGameScreen(Player curr_player, int currentValue) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gameOverScreen.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root,500,400);
        //System.out.println(root.getChildrenUnmodifiable());
//        this.score.setText(String.valueOf(currentValue));
        ((Label)root.lookup("#score")).setText(String.valueOf(currentValue));
        if (currentValue > player.getHighScore()) {
            //this.best.setText(String.valueOf(currentValue));
//            System.out.println(currentValue);
            ((Label)root.lookup("#best")).setText(String.valueOf(currentValue));
            player.setHighScore(currentValue);
        }
        else {
            ((Label)root.lookup("#best")).setText(String.valueOf(player.getHighScore()));
        }
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
    @FXML
    public void reviveScreen(Game controllerr, int currentScore) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/revive.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root,500,400);
        stage.setScene(scene);
        stage.setTitle("Revive Screen");
        ((Text)root.lookup("#cherries")).setText("CHERRIES AFTER REVIVAL - "+(player.getCherries()-3));
        stage.show();
        ((Button)root.lookup("#yes")).setOnAction(e -> {
            Game controller = new Game(stage,player,this);
            player.resetPlayer();
            player.setCherries(player.getCherries() - 3);
            System.out.println("CURRENT SCORE : "+currentScore);
            controller.setCurrentScore(currentScore);
            controller.startGameScene(currentScore);
        });
        ((Button)root.lookup("#no")).setOnAction(e -> {
            try {
                endGameScreen(player,currentScore);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void mainMenu(MouseEvent mouseEvent) throws IOException {
        mainMenu((Stage)((Node)mouseEvent.getSource()).getScene().getWindow());
    }
}
