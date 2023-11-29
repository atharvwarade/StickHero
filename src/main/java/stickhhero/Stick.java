package stickhhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Stick {

    private Line line;
    private double lineLength;
    private Timeline timeline;
    private boolean stickRotated;
    private boolean stickExtended;
    private Timeline rotateTimeline;

    public Stick(Line line) {
        this.line = line;
        lineLength = 0;
        this.line.setStrokeWidth(2.0);
    }

    public Timeline getRotateTimeline() {
        return rotateTimeline;
    }

    public void setRotateTimeline(Timeline rotateTimeline) {
        this.rotateTimeline = rotateTimeline;
    }

    public boolean isStickExtended() {
        return stickExtended;
    }

    public void setStickExtended(boolean stickExtended) {
        this.stickExtended = stickExtended;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;

    }

    public double getLineLength() {
        return lineLength;
    }

    public void setLineLength(double lineLength) {
        this.lineLength = lineLength;
    }

    public boolean isStickRotated() {
        return stickRotated;
    }

    public void setStickRotated(boolean stickRotated) {
        this.stickRotated = stickRotated;
    }

    public void extendLine(Stage stage, Player player, Scene scene, Box first, Box second) {
        this.stickExtended = false;
        scene.setOnMousePressed(e -> {
            this.growStick(stage,player,scene);
        });
        scene.setOnMouseReleased(e -> {
            timeline.stop();
            this.rotateStick(stage,player,scene);
        });
    }



    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    private void rotateStick(Stage stage, Player player, Scene scene) {
        if (this.stickExtended) {
            return;
        }
        //timeline.setOnFinished(p ->this.stickExtended = true);
        this.stickExtended = true;
        System.out.println("STICK EXTENSION COMPLETED");
//        Rotate rotate = new Rotate();
//        rotate.setPivotX(line.getStartX());
//        rotate.setPivotY(line.getStartY());
//        line.getTransforms().add(rotate);
//        rotate.setAngle(90);
//        //line.setEndX(line.getStartX() + this.lineLength);
//        this.setStickRotated(true);
        rotateTimeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    this.rotateNow();
                })
        );
        rotateTimeline.setCycleCount(Timeline.INDEFINITE);
        rotateTimeline.play();
    }

    private void rotateNow() {
        if ((this.line.getEndX() >= this.line.getStartX() + this.getLineLength()) && (this.line.getEndY() >= 200.0)) {
            rotateTimeline.stop();
            System.out.println("STICK ROTATED");
            System.out.println("Line length : "+(this.line.getEndX() - this.line.getStartX()));
            System.out.println("Linelength : "+(this.getLineLength()));
            this.setStickRotated(true);
            return;
        }
        else if (this.line.getEndX() >= this.line.getStartX() + this.getLineLength()) {
            this.line.setEndY(this.line.getEndY() + 1.0);
        }
        else if (this.line.getEndY() >= 200.0) {
            this.line.setEndX(this.line.getEndX() + 1.0);
        }
        else {
            this.line.setEndY(this.line.getEndY() + 1.0);
            this.line.setEndX(this.line.getEndX() + 1.0);
        }
    }

    public void setLineCoordinates() {
        line.setStartX(101.0);
        line.setStartY(200.0);
        line.setEndX(101.0);
        line.setEndY(200.0);
    }

    private void growStick(Stage stage, Player player, Scene scene) {
        if (this.isStickExtended()) {
            return;
        }
        System.out.println("EXTENDING LINE");
        this.setLineLength(0.0);
        this.setLineCoordinates();
        System.out.println("PAPAPA");
         timeline = new Timeline(
                new KeyFrame(Duration.millis(10), e -> {
                    lineLength+=1.0;
                    line.setEndY(line.getStartY() - lineLength);
                })
         );
         timeline.setCycleCount(Timeline.INDEFINITE);
         timeline.play();
    }
}
