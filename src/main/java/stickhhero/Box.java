package stickhhero;

import java.awt.*;

public class Box {
    private final int height = 300;
    private double width;
    private double layoutX;
    private double layoutY;

    public double getLayoutX() {
        return layoutX;
    }

    public void setLayoutX(double layoutX) {
        this.layoutX = layoutX;
    }

    public double getLayoutY() {
        return layoutY;
    }

    public void setLayoutY(double layoutY) {
        this.layoutY = layoutY;
    }


    public Box() {

    }
    public Box(double width) {
        this.width = width;
    }

//    public Rectangle getPlatform() {
//        return platform;
//    }
//
//    public void setPlatform(Rectangle box) {
//        this.platform = box;
//    }

    public int getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }
}
