import javax.swing.*;
import java.awt.*;

public class HittableButton extends JButton {
    public boolean isShip = false; //0 is empty, 1 is ship
    private boolean isHit = false;

    public HittableButton() {
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
}
