package com.example.demo6;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class HelloController implements Initializable {
    @FXML
    private Line firstLight;
    @FXML
    protected Line mirror;
    @FXML
    protected AnchorPane box;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final Line[] line = {firstLight};
        final double[] angleDegrees = {160};
        AtomicReference<Double> angle= new AtomicReference<>(Math.toRadians(angleDegrees[0]));
        System.out.println(Math.cos(angle.get()));
        System.out.println(Math.sin(angle.get()));
        Point2D[] lightRayEnd = {new Point2D(line[0].getEndX() + line[0].getLayoutX(), line[0].getEndY() + line[0].getLayoutY())};
        Point2D mirror1 = new Point2D(mirror.getStartX() + mirror.getLayoutX(), mirror.getStartY() + mirror.getLayoutY());
        Point2D mirror2 = new Point2D(mirror.getEndX() + mirror.getLayoutX(), mirror.getEndY() + mirror.getLayoutY());
        AtomicReference<Double> mirrorAngle= new AtomicReference<>(mirror1.angle(mirror2,new Point2D(mirror.getStartX()+10 + mirror.getLayoutX(),line[0].getEndY() + line[0].getLayoutY())));
        System.out.println("mirror angle="+mirrorAngle.get());
        AtomicReference<Double> angle3 = new AtomicReference<>(lightRayEnd[0].angle(mirror2, mirror1));

        System.out.println();
        final AtomicBoolean[] hit = {new AtomicBoolean(false)};

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), e -> {
            lightRayEnd[0] = new Point2D(line[0].getEndX() + line[0].getLayoutX(), line[0].getEndY() + line[0].getLayoutY());
            line[0].setEndX(line[0].getEndX() + Math.cos(angle.get()));
            line[0].setEndY(line[0].getEndY() + Math.sin(angle.get()));

            double newAngle3 = lightRayEnd[0].angle(mirror2, mirror1);
            System.out.println(newAngle3);

            if (angle3.get() < newAngle3) {
                System.out.println("comming");
            }

            if( angle3.get() > newAngle3 && angle3.get()>179.5 && !hit[0].get()){
                System.out.println("hit");
                hit[0].set(true);
                System.out.println(mirrorAngle);
                angleDegrees[0] =180-2*mirrorAngle.get()+angleDegrees[0];
                angle.set(Math.toRadians(angleDegrees[0]));
                double x= line[0].getEndX();
                double y= line[0].getEndY();
                double lX= line[0].getLayoutX();
                double lY= line[0].getLayoutY();
                line[0] =new Line( x+lX ,  y+ lY,x +lX+ Math.cos(angle.get()),y +lY+ Math.sin(angle.get()));
                box.getChildren().add(line[0]);
                System.out.println(angleDegrees[0]);
            }
            angle3.set(newAngle3);
        }));

        timeline.setCycleCount(200);
        timeline.play();
    }
}
