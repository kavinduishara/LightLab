package com.example.demo6;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    protected AnchorPane box;
    List<StraightLine> list = new ArrayList<>();
    List<LightRay> list2 = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        box.heightProperty().addListener((observable, oldValue, newValue) -> {
            makeContent();
        });
        box.widthProperty().addListener((observable, oldValue, newValue) -> {
            makeContent();
        });


    }

    private void findAndDrawIntersection(LightRay lightRay) {
        final double[] distance = {Double.MAX_VALUE};
        StraightLine[] chosen = {null};
        double[] intersectionX = {0};
        double[] intersectionY = {0};

        list.forEach(straightLine -> {
            double[] point = straightLine.findEndPoint(lightRay);
            if(Double.isNaN(point[0])){
                return;
            }
            if (distance[0] > point[2] && distance[0]>0) {
                distance[0] = point[2];
                chosen[0] = straightLine;
                intersectionX[0] = point[0];
                intersectionY[0] = point[1];
            }
        });

        if (chosen[0] != null) {
            Line intersectionLine = new Line(lightRay.startX, lightRay.startY, intersectionX[0], intersectionY[0]);
            intersectionLine.setStroke(Color.RED);
            box.getChildren().add(intersectionLine);
            LightRay newray=chosen[0].nextRay(lightRay.angle,intersectionX[0],intersectionY[0]);

            if(newray != null){
                findAndDrawIntersection(newray);
            }
        }
    }

    protected void createStraightLine(double startX, double startY, double endX, double endY) {
        list.add(new StraightLine(startX, startY, endX, endY));
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.BLUE);
        line.setStrokeWidth(5);
        box.getChildren().add(line);
    }protected void createMirror(double startX, double startY, double endX, double endY) {
        list.add(new Mirror(startX, startY, endX, endY));
        Line line = new Line(startX, startY, endX, endY);
        line.setStroke(Color.BROWN);
        line.setStrokeWidth(5);
        box.getChildren().add(line);
    }

    protected void createLightRay(double startX, double startY, double angle) {
        list2.add(new LightRay(startX, startY, angle));
    }
    protected void makeContent(){
        box.getChildren().clear();
        list.clear();

        createStraightLine(0, 0, box.getWidth(), 0);
        createStraightLine(0, 0, 0, box.getHeight());
        createStraightLine(0, box.getHeight(), box.getWidth(), box.getHeight());
        createStraightLine(box.getWidth(), 0, box.getWidth(), box.getHeight());

        createStraightLine(10, 10, 500, 10);

        createMirror(10, 10, 500, 500);
        createMirror(200, 10, 800, 500);
        createMirror(0, 10, 800, 20);
        createMirror(400, 400, 600, 400);

        createLightRay(250, 150,  180);
        list2.forEach(this::findAndDrawIntersection);
    }
}