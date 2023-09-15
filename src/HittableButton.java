import javax.swing.*;
import java.awt.*;

public class HittableButton extends JButton {
    public boolean isShip = false; //0 is empty, 1 is ship
    private boolean isHit = false;
    private Board board;

    private int x = 0;
    private int y = 0;

    public HittableButton() {
        super();
    }

    public HittableButton(int x, int y, Board board) {
        super();
        this.x = x-13;
        this.y = y-1;
        this.board = board;
    }

    public void setShipStatus(boolean status) {
        isShip = status;
    }

    public boolean shipStatus() {
        return isShip;
    }
    public void setHit() {
        isHit = true;
    }

    public boolean isHit() {
        return isHit;
    }

    public boolean hit () {
        if (this.isShip && !this.isHit) {
            isHit = true;
            this.setBackground(Color.RED);
            this.setEnabled(false);
            return true;
        } else if (!this.isHit) {
            isHit = true;
            this.setBackground(Color.BLACK);
            this.setEnabled(false);
            return false;
        }
        return false;
    }

    public void setAction() {
        this.addActionListener(e -> {
            System.out.println("X" + x + "\nY" + y);
            board.getClient().postData(x, y, board);
            board.oPanelList.get(y*10+x).hit();
            this.setEnabled(false);
        });
    }
}
