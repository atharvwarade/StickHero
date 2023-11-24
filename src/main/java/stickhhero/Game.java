package stickhhero;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

public class Game {
    private static boolean isCherryPresent; // Cherry ko present karna hai ya nahi;
    @FXML
    private Image player;
    private Stage stage;
    private static final ImageView cherry = null; // idhar cherry ka vector image laga denge;
    @FXML
    private Rectangle secondBox;
    @FXML
    public Rectangle firstBox;
    @FXML
    private AnchorPane myAnchorPane;

    public Game() {

    }
    public Game(Stage stage) {
        this.stage = stage;
        firstBox = new Rectangle();
        firstBox.setFill(Paint.valueOf("GREEN"));
        secondBox = new Rectangle();
    }

    public void startGame(Player player) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/game.fxml"));
        Parent root = fxmlLoader.load();
        Game controller = fxmlLoader.getController();
        Scene scene = new Scene(root,500,400);
        stage.setTitle("GAME");
        stage.setScene(scene);
        stage.show();
        Random random = new Random();
        Box first = new Box();
        controller.updateBox(first,100.0,0.0,200.0);
        Box second = new Box();
        double secondWidth = 101 + (490 - 102)*(random.nextDouble());
        while (true) {
            //Line line = new Line();
            break;
        }
    }

    public void setRectangle(Box box) {
        firstBox.setWidth(box.getWidth());
        firstBox.setLayoutX(box.getLayoutX());
        firstBox.setLayoutY(box.getLayoutY());
    }

    public void updateBox(Box box, double width, double layoutX, double layoutY) {
        box.setWidth(width);
        box.setLayoutX(layoutX);
        box.setLayoutY(layoutY);
        setRectangle(box);
    }
}
