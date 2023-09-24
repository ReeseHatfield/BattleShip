package me.braintrust.battleship;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Ship {

    private final List<Point> points = new ArrayList<>();
    private final List<Point> hitPoints = new ArrayList<>();

    public Ship(int startX, int startY, int endX, int endY){
        boolean isVertical = startX == endX;

        if (isVertical) {
            for (int i=startY; i<=endY; i++){
                points.add(new Point(startX, i));
            }
        } else {
            for (int i=startX; i<=endX; i++){
                points.add(new Point(i, startY));
            }
        }
    }

    public void hit(Point p){
        //Call copy constructor; maybe unnecessary
        this.hitPoints.add(new Point(p));
    }

    public List<Point> getPoints() {
        return points;
    }

    public List<Point> getHitPoints() {
        return hitPoints;
    }
}
