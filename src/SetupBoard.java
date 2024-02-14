import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SetupBoard extends JFrame {

    //private Client client;
    private boolean finishedSetup = false;
    private Backend backend;
    private ArrayList<JButton> buttons = new ArrayList<>(100);
    private boolean pickingStartShip = true;
    private Point startPoint;
    int remainingShips = 15;

    public SetupBoard(Client client, Backend backend) {
        //JFrame frame = new JFrame();
        this.backend = backend;
        //this.client = client;
        JPanel root = new PicturePanel(".//production//BattleShip//theFleet.jpg");
        JPanel serverSelect = new JPanel();
        JPanel board = new JPanel();

        JTextField serverField = new JTextField(20);
        JLabel label = new JLabel("Please input IP: ");
        JButton submitButton = new JButton("Start");
        submitButton.addActionListener(e -> {
            finishedSetup = true;
            client.setIP(serverField.getText().trim());
            this.dispose();
        });
        serverSelect.add(label);
        serverSelect.add(serverField);
        serverSelect.add(submitButton);

        //temp label
        JLabel temp = new JLabel("Remaining Ships: " + remainingShips);
        serverSelect.add(temp);

        serverSelect.setBackground(new Color(1,1,1,0));
        board.setBackground(new Color(1,1,1,0));
        root.setLayout(new BoxLayout(root,BoxLayout.Y_AXIS));
        root.add(serverSelect);
        root.add(board);

        GridBagLayout lay = new GridBagLayout();
        board.setLayout(lay);
        GridBagConstraints c = new GridBagConstraints();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 11; j++) {
                if (j == 0) {
                    JLabel leftSide = new JLabel("" + (char) ('A'+i), SwingConstants.CENTER);
                    leftSide.setBackground(new Color(255,255,255,120));
                    leftSide.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    if (j == 10) {
                        c.ipadx = 19;
                        c.ipady = 20;
                        c.gridx = j;
                        c.gridy = i+1;
                        leftSide.setBackground(Color.PINK);
                        leftSide.setOpaque(true);
                        board.add(leftSide,c);
                        continue;
                    }
                    c.ipadx = 20;
                    c.ipady = 20;
                    c.gridx = j;
                    c.gridy = i + 1;
                    leftSide.setBackground(Color.PINK);
                    leftSide.setOpaque(true);
                    board.add(leftSide,c);
                    continue;
                }
                SetupButton p = new SetupButton(j-1,i,this);
                buttons.add(p);
                p.setBackground(new Color(255,255,255,120));
                p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                p.setOpaque(true);
                c.ipadx = 40;
                c.ipady = 40;
                c.gridx = j;
                c.gridy = i + 1;

                board.add(p, c);

                JLabel topRow = new JLabel("" + j,SwingConstants.CENTER);
                topRow.setBackground(new Color(255,255,255,120));
                topRow.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                c.ipadx = 30;
                c.ipady = 20;
                c.gridx = j;
                c.gridy = 0;
                topRow.setOpaque(true);
                board.add(topRow, c);

            }
        }
        //frame.getContentPane().add(root);
        this.setContentPane(root);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1400,760);
        this.setResizable(false);
        //root.setVisible(true);
        this.setVisible(true);
        //this.repaint();
    }

    public boolean getFin() {
        return finishedSetup;
    }

    public boolean getShipOrder() {
        return pickingStartShip;
    }
    public void setStartPoint(Point p) {
        if (remainingShips < 1) {
            return;
        }
        this.startPoint = p;
        pickingStartShip = false;
    }
    public void makeShip(Point endPoint) {
        pickingStartShip = true;
        Ship newShip = backend.guiMakingShips(startPoint.x,startPoint.y,endPoint.x,endPoint.y);
        if (newShip == null) {
            return;
        }

        //This loop is the easy way out because I didn't want to think
        for (Point p : newShip.points) {
            remainingShips--;
            JButton button = this.buttons.get(p.y * 10 + p.x);
            button.setBackground(Color.GREEN);
        }

    }
}