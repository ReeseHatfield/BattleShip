import javax.swing.*;
import java.awt.*;

public class HittablePanel extends JPanel {
    private boolean isShip = false; //0 is empty, 1 is ship
    private boolean isHit = false;

    public HittablePanel() {
        super();
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
            return true;
        } else if (!this.isHit) {
            isHit = true;
            this.setBackground(Color.BLACK);
            return false;
        }
        return false;
    }
}
