import javax.swing.*;
import java.awt.*;

public class SetupButton extends JButton {

    private int xCoord;
    private int yCoord;

    public SetupButton(int xCoord, int yCoord, SetupBoard setupBoard) {
        super();
        this.xCoord = xCoord;
        this.yCoord = yCoord;

        this.addActionListener(e -> {
            if (setupBoard.getShipOrder()) { //Selecting Start of ship
                setupBoard.setStartPoint(new Point(xCoord,yCoord));
            }
            else { //Selecting End of Ship
                setupBoard.makeShip(new Point(xCoord,yCoord));
            }
        });
    }
}