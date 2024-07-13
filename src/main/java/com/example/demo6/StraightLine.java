package com.example.demo6;

import javafx.geometry.Point2D;

public class StraightLine {
    double startX, startY, endX, endY;

    StraightLine(double startX, double startY, double endX, double endY) {
        this.endX = endX;
        this.endY = endY;
        this.startX = startX;
        this.startY = startY;
    }

    protected double[] findEndPoint(LightRay lightRay) {
        double angleRadians = Math.toRadians(lightRay.angle);
        double length = 1000;
        double x3 = lightRay.startX + length * Math.cos(angleRadians);
        double y3 = lightRay.startY + length * Math.sin(angleRadians);

        double x = getIntersectionX(lightRay.startX, lightRay.startY, x3, y3, this.startX, this.startY, this.endX, this.endY);
        double y = getIntersectionY(lightRay.startX, lightRay.startY, x3, y3, this.startX, this.startY, this.endX, this.endY);

        Point2D point = new Point2D(x, y);
        double distance = point.distance(lightRay.startX, lightRay.startY);

        if(Math.round(clockWiseAngle(point,lightRay.startX, lightRay.startY))!=lightRay.angle){
            x=Double.NaN;
            y=Double.NaN;
        }
        if(x>this.endX || x<this.startX){
            x=Double.NaN;
            y=Double.NaN;
        }

        return new double[]{x, y, distance};
    }
    protected double clockWiseAngle(Point2D point,double x,double y){
        Point2D point2=new Point2D(x, y);
        double angleX=point2.angle(point,new Point2D(x+10, y));
        double angleY=point2.angle(point,new Point2D(x, y+10));
        double angle=0;

        if(angleY<=90){
            angle=angleX;
        }else if(angleY>90){
            angle=360-angleX;
        }
        return angle;
    }

    private double getIntersectionX(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (denominator == 0) return Double.NaN; // Lines are parallel
        double numerator = (x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4);
        return numerator / denominator;
    }

    private double getIntersectionY(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        double denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (denominator == 0) return Double.NaN; // Lines are parallel
        double numerator = (x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4);
        return numerator / denominator;
    }
    protected LightRay nextRay(double angle,double x,double y){
        return null;
    }

}