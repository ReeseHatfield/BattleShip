import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;

public class Backend {
    private ArrayList<Ship> ships;
    private Scanner scnr;

    public Backend(Scanner scnr){
        this.scnr = scnr;
        createShips();


    }

    /*
    Currently only adds a single ships
     */
    private void createShips(){

        this.ships = new ArrayList<Ship>();


        int startX;
        int startY;
        int endX;
        int endY;


        for (int i = 0; i < 2; i++) {
            System.out.println("MAKING SHIP " + i);
            System.out.println("Start X: ");
            startX = scnr.nextInt();

            System.out.println("Start Y: ");
            startY = scnr.nextInt();

            System.out.println("End X: ");
            endX = scnr.nextInt();

            System.out.println("End Y: ");
            endY = scnr.nextInt();

            ships.add(new Ship(startX, startY, endX, endY));
        }



    }

    public boolean tryHit(int x, int y){
        for(Ship s : this.ships){
            for(Point p : s.points){
                if(p.x == x && p.y == y){
                    s.hit(p);
                    return true;
                }
            }
        }

        return false;
    }

    public ArrayList<Point> getPointsHit(){
        ArrayList<Point> allPointsHit = new ArrayList<>();
        for(Ship s : this.ships){
            allPointsHit.addAll(s.hitPoints);
        }

        return allPointsHit;

    }
    public ArrayList<Ship> getShips() {
        return ships;
    }

}