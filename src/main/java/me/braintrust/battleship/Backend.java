package me.braintrust.battleship;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.*;

public class Backend {

    private static final Scanner input = new Scanner(System.in);
    private final List<Ship> ships = new ArrayList<>();

    public Backend() {
        createShips();
    }

    private boolean isOutOfBounds(int toCheck) {
        return (toCheck < 0 || toCheck > 9);
    }

    private void createShips() {
        int startX;
        int startY;
        int endX;
        int endY;

        for (int i = 0; i < 2; i++) {
            System.out.println("MAKING SHIP " + i);
            System.out.println("Start X: ");
            startX = input.nextInt();

            System.out.println("Start Y: ");
            startY = input.nextInt();

            System.out.println("End X: ");
            endX = input.nextInt();

            System.out.println("End Y: ");
            endY = input.nextInt();

            ships.add(new Ship(startX, startY, endX, endY));

            if (isOutOfBounds(startX) || isOutOfBounds(startY) || isOutOfBounds(endX) || isOutOfBounds(endY)) {
                System.out.println("Error");
                i--;
            }
        }

        System.out.println("ALL SHIP POINTS:");
        for (Ship s : this.ships) {
            for (Point p : s.getPoints()) {
                System.out.println("X: " + p.x + " Y: " + p.y);
            }
        }

    }

    public boolean tryHit(int x, int y) {
        for (Ship s : this.ships) {
            for (Point p : s.getPoints()) {
                if (p.x == x && p.y == y) {
                    s.hit(p);
                    return true;
                }
            }
        }
        return false;
    }

    public List<Point> getPointsHit() {
        ArrayList<Point> allPointsHit = new ArrayList<>();
        for (Ship s : this.ships) {
            allPointsHit.addAll(s.getHitPoints());
        }
        return allPointsHit;
    }

    public List<Ship> getShips() {
        return ships;
    }
}
