package com.example.demo6;

import javafx.geometry.Point2D;

public class Mirror extends StraightLine{
    Mirror(double startX, double startY, double endX, double endY) {
        super(startX, startY, endX, endY);
    }
    protected LightRay nextRay(double angle,double x,double y){
        double newAngle=(-angle+2*super.clockWiseAngle(new Point2D(endX,endY),
                startX,startY)+360)%360;
        return new LightRay(x,y,Math.round(newAngle));
    }
}
