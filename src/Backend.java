import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;

public class Backend {
    private ArrayList<Ship> ships;
    private Scanner scnr;
    private Client client;
    private boolean finishedSetup = false;

    public Backend(Scanner scnr, Client client){
        this.scnr = scnr;
        this.client = client;
        createShips();


    }

    private boolean isOutOfBounds(int toCheck){
        return (toCheck < 0 || toCheck > 9);
    }

    private void createShips(){

        this.ships = new ArrayList<Ship>();
        SetupBoard set = new SetupBoard(client, this);
        while(!set.getFin()) {
            try {
                //System.out.println("sleeper");
                set.repaint();
                Thread.sleep(100);
            }
            catch (Exception e){}
        }
        System.out.println("Finished Setup");



//        int startX;
//        int startY;
//        int endX;
//        int endY;
//
//
//        for (int i = 0; i < 2; i++) {
//            System.out.println("MAKING SHIP " + i);
//            System.out.println("Start X: ");
//            startX = scnr.nextInt();
//
//            System.out.println("Start Y: ");
//            startY = scnr.nextInt();
//
//            System.out.println("End X: ");
//            endX = scnr.nextInt();
//
//            System.out.println("End Y: ");
//            endY = scnr.nextInt();
//
//            ships.add(new Ship(startX, startY, endX, endY));
//
//            if(isOutOfBounds(startX) || isOutOfBounds(startY) || isOutOfBounds(endX) || isOutOfBounds(endY)){
//                System.out.println("Error");
//                i--;
//                continue;
//            }
//        }

        System.out.println("ALL SHIP POINTS:");
        for(Ship s : this.ships){
            for (Point p : s.points){
                System.out.println("X: " + p.x + " Y: " + p.y);
            }
        }



    }

    public Ship guiMakingShips(int xStart, int yStart, int xEnd, int yEnd) {
        Ship newShip = new Ship(xStart,yStart,xEnd,yEnd);
        ships.add(newShip);
        return newShip; //Assumed success for now
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
