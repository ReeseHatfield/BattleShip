import java.awt.*;
import java.util.ArrayList;

public class Ship {
    public ArrayList<Point> points = new ArrayList<>();

    public Ship(int startX, int startY, int endX, int endY){

        boolean isVertical = startX == endX;

        if(isVertical){
            for(int i = startY; i <= endY; i ++){
                points.add(new Point(startX, i));
            }
        }
        else{
            for(int i = startX; i <= endY; i ++){
                points.add(new Point(i, startY));
            }
        }

    }
}
