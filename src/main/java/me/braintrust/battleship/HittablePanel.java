package me.braintrust.battleship;

import javax.swing.*;
import java.awt.*;

public class HittablePanel extends JPanel {

    private boolean isShip = false; // 0 is empty, 1 is ship
    private boolean isHit = false;

    public boolean hit() {
        // Already hit, ignore.
        if (isHit) {
            return false;
        }

        isHit = true;
        setBackground(isShip ? Color.RED : Color.BLACK);
        return isShip;
    }

    public boolean isShip() {
        return isShip;
    }

    public void setShip(boolean isShip) {
        this.isShip = isShip;
    }

    public boolean isHit() {
        return isHit;
    }

    public void setHit() {
        isHit = true;
    }
}
