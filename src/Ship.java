import java.awt.*;
import java.util.ArrayList;

public class Ship {
    public ArrayList<Point> points = new ArrayList<>();
    public ArrayList<Point> hitPoints = new ArrayList<>();

    public Ship(int startX, int startY, int endX, int endY){

        boolean isVertical = startX == endX;

        if(isVertical){
            for(int i = startY; i <= endY; i ++){
                points.add(new Point(startX, i));
            }
        }
        else{
            for(int i = startX; i <= endX; i ++){
                points.add(new Point(i, startY));
            }
        }

    }

    public void hit(Point p){
        //Call copy constructor; maybe unnecessary
        this.hitPoints.add(new Point(p));
    }
}
