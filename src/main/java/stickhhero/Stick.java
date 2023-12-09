package stickhhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Stick {

    private Line line;
    private double lineLength;
    private Timeline timeline;
    private boolean stickRotated;
    private boolean stickExtended;
    private Timeline rotateTimeline;
    private int count;
    private Rotate rotate;

    public Stick(Line line) {
        this.line = line;
        lineLength = 0;
        this.line.setStrokeWidth(2.0);
        this.count = 1;
        rotate = new Rotate();
        line.getTransforms().add(rotate);
        this.stickExtended = false;
    }

    public Rotate getRotate() {
        return rotate;
    }

    public void setRotate(Rotate rotate) {
        this.rotate = rotate;
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
        scene.setOnMousePressed(null);
        scene.setOnMouseReleased(null);
        this.stickExtended = false;
        scene.setOnMousePressed(event -> {
            this.growStick(stage,player,scene);
        });
        scene.setOnMouseReleased(event -> {
            timeline.stop();
            this.rotateStick(stage,player,scene);
        });
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
                    lineLength+=1.5;
                    line.setEndY(line.getStartY() - lineLength);
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public void resetAngle() {
        rotate.setAngle(0);
    }

    private void rotateStick(Stage stage, Player player, Scene scene) {
        if (this.stickExtended) {
            return;
        }
        //timeline.setOnFinished(p ->this.stickExtended = true);
        this.stickExtended = true;
        System.out.println("STICK EXTENSION COMPLETED");
        rotate.setPivotX(line.getStartX());
        rotate.setPivotY(line.getStartY());
//        rotate.setAngle(90);
//        //line.setEndX(line.getStartX() + this.lineLength);
//        this.setStickRotated(true);
        rotateTimeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    this.rotateNow(rotate,count);
                    count+=1;
                })
        );
        rotateTimeline.setCycleCount(Timeline.INDEFINITE);
        rotateTimeline.play();
    }

    private void rotateNow(Rotate rotate, int count) {
        if (count > 90) {
            rotateTimeline.stop();
            System.out.println("STICK ROTATED");
            System.out.println("Line length : "+(this.line.getEndX() - this.line.getStartX()));
            System.out.println("Linelength : "+(this.getLineLength()));
            this.setStickRotated(true);
            this.count = 0;
            return;
        }
        rotate.setAngle(count);
    }

    public void setLineCoordinates() {
        line.setStartX(101.0);
        line.setStartY(200.0);
        line.setEndX(101.0);
        line.setEndY(200.0);
//        Rotate rotate = new Rotate();
//        rotate.setPivotX(this.line.getStartX());
//        rotate.setPivotY(this.line.getStartY());
//        this.line.getTransforms().add(rotate);
//        rotate.setAngle(-90);
    }


}
