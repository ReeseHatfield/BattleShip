import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;

public class Backend {
    private ArrayList<Ship> ships;

    public Backend(){
        createShips();

        System.out.println(this.ships.get(0).points);

    }

    /*
    Currently only adds a single ships
     */
    public void createShips(){

        this.ships = new ArrayList<Ship>();


        int startX;
        int startY;
        int endX;
        int endY;

        Scanner scnr = new Scanner(System.in);

        System.out.println("Start X: ");
        startX = scnr.nextInt();

        System.out.println("Start Y: ");
        startY = scnr.nextInt();

        System.out.println("End X: ");
        endX = scnr.nextInt();

        System.out.println("End Y: ");
        endY = scnr.nextInt();

        scnr.close();
        ships.add(new Ship(startX, startY, endX, endY));


    }

    public boolean tryHit(int x, int y){
        for(Ship s : this.ships){
            for(Point p : s.points){
                if(p.x == x && p.y == y){
                    return true;
                }
            }
        }

        return false;
    }


    public static void main(String[] args) {
        Backend backend = new Backend();
        System.out.println(backend.tryHit(4,4));
    }

}
