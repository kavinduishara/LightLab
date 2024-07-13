package com.example.demo6;

import javafx.geometry.Point2D;

public class Main {
    public static void main(String[] args) {
        double newAngle=(findAngle(new Point2D(10,10),
                100,10));

    }protected static double findAngle(Point2D point, double x, double y){
        Point2D point2=new Point2D(x, y);
        double angleX=point2.angle(point,new Point2D(x+10, y));
        double angleY=point2.angle(point,new Point2D(x, y+10));
        double angle=0;
        if(angleY<=90){
            angle=angleX;
        }else if(angleY>90){
            angle=360-angleX;
        }
        System.out.println("angle"+angle);
        return 78;
    }
}
