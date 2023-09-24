package me.braintrust.battleship;

import javax.swing.*;
import java.awt.*;

public class HittableButton extends JButton {

    private final Board board;
    private final int x;
    private final int y;
    private boolean isShip = false; // 0 is empty, 1 is ship
    private boolean isHit = false;

    public HittableButton(Board board, int x, int y) {
        this.board = board;
        this.x = x - 1;
        this.y = y - 1;
    }

    public void setAction() {
        addActionListener(e -> {
            if (!board.getClient().isTurn()) {
                return;
            }

            System.out.println("X" + x + "\nY" + y);
            board.getClient().postData(x, y, 0, 0, 0, board);
            board.getOtherButtons().get((x * 10) + y).hit();
            setEnabled(false);
            board.getClient().setLastShotX(x);
            board.getClient().setLastShotY(y);
        });
    }

    public boolean hit() {
        // Already hit, ignore.
        if (isHit) {
            return false;
        } else {
            isHit = true;
            setEnabled(false);
        }

        // A ship was hit!
        if (isShip) {
            setBackground(Color.RED);
            board.setHealth(board.getHealth() - 1);
            return true;
        }

        // No ship was hit.
        setBackground(Color.BLACK);
        return false;
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

    public void setHit(boolean isHit) {
        this.isHit = isHit;
    }
}
