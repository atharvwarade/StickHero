package stickhhero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {

    private int highScore;
    private int cherries; // this will track total cherries ab tak ki;
    private Image playerSkin;
    public Player() {

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

    public Image getPlayerSkin() {
        return playerSkin;
    }

    public void setPlayerSkin(Image playerSkin) {
        this.playerSkin = playerSkin;
    }
}
