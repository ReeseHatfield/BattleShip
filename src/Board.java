import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Board {
    ArrayList<HittableButton> playerBoard = new ArrayList<>();
    ArrayList<HittableButton> otherBoard = new ArrayList<>();
    JFrame frame = new JFrame();
    Backend backend;
    int health = 0;
    boolean lost = false;
    boolean isTurn = true;
    int i = 0;
    int j = 0;

    private Client client;
    public Board(Client client) {
        this.client = client;
        PicturePanel root = new PicturePanel(".\\production\\BattleShip\\battleship.jpg");
        GridBagLayout lay = new GridBagLayout();
        root.setLayout(lay);
        GridBagConstraints c = new GridBagConstraints();
        for (i = 0; i < 10; i++) {
            for (j = 0; j < 11; j++) {
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
                        root.add(leftSide,c);
                        continue;
                    }
                    c.ipadx = 20;
                    c.ipady = 20;
                    c.gridx = j;
                    c.gridy = i + 1;
                    leftSide.setBackground(Color.PINK);
                    leftSide.setOpaque(true);
                    root.add(leftSide,c);
                    continue;
                }
                HittableButton p = new HittableButton(this);
                p.setBackground(new Color(255,255,255,120));
                p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                p.setOpaque(true);
                c.ipadx = 40;
                c.ipady = 40;
                c.gridx = j;
                c.gridy = i + 1;
                p.addActionListener(e -> {
                    System.out.println("button " + p.isShip);
                });
                p.setEnabled(false);

                root.add(p, c);
                playerBoard.add(p);

                JLabel topRow = new JLabel("" + j,SwingConstants.CENTER);
                topRow.setBackground(new Color(255,255,255,120));
                topRow.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                c.ipadx = 30;
                c.ipady = 20;
                c.gridx = j;
                c.gridy = 0;
                topRow.setOpaque(true);
                root.add(topRow, c);

                }
                JPanel p = new JPanel();
                p.setOpaque(false);
                c.ipadx = 100;
                c.ipady = 20;
                c.gridx = 11;
                //GRIDY!!!!
                c.gridy = i+1;
                root.add(p,c);

            for (j = 12; j < 23; j++) {
                if (j == 12) {
                    JLabel leftSide = new JLabel("" + (char) ('A'+i), SwingConstants.CENTER);
                    leftSide.setBackground(new Color(255,255,255,120));
                    leftSide.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    if (j == 21) {
                        c.ipadx = 19;
                        c.ipady = 20;
                        c.gridx = j;
                        c.gridy = i+1;
                        leftSide.setBackground(Color.PINK);
                        leftSide.setOpaque(true);
                        root.add(leftSide,c);
                        continue;
                    }
                    c.ipadx = 20;
                    c.ipady = 20;
                    c.gridx = j;
                    c.gridy = i+1;
                    leftSide.setBackground(Color.PINK);
                    leftSide.setOpaque(true);
                    root.add(leftSide,c);
                    continue;
                }
                HittableButton oButton = new HittableButton(j, i+1, this);
                oButton.setBackground(new Color(255,255,255,120));
                oButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                oButton.setOpaque(true);
                c.ipadx = 40;
                c.ipady = 40;
                c.gridx = j;
                c.gridy = i + 1;

                oButton.setAction();


                /*oPanels.addActionListener(e -> {
                    if (isTurn) {
                        int x = j-19;
                        int y = i-10;
                        System.out.println("X" + x + "\nY" + y);
                        client.postData(x, y, this);
                        oPanels.hit();
                        oPanels.setEnabled(false);
                        //isTurn = false;
                    }
                });*/
                root.add(oButton, c);
                otherBoard.add(oButton);

                JLabel topRow = new JLabel("" + (j-12),SwingConstants.CENTER);
                topRow.setBackground(new Color(255,255,255,120));
                topRow.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                c.ipadx = 30;
                c.ipady = 20;
                c.gridx = j;
                c.gridy = 0;
                topRow.setOpaque(true);
                root.add(topRow, c);
            }


        }


        Timer t = new Timer();

        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!lost) {
                    root.repaint();
                }
            }
        },0,1000);

        frame.getContentPane().add(root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400,760);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void changePanelColor(int side, int panel, Color bg) {
        playerBoard.get(panel).setBackground(bg);
    }
    public int getHealth() {
        System.out.println("Health: " + health);
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }


    public void losingMenu() {
        PicturePanel loss = new PicturePanel(".\\production\\BattleShip\\loss.jpg");
        /*JLabel losingText = new JLabel("YOU LOST");
        loss.add(losingText);*/
        frame.dispose();
        JFrame frame = new JFrame();
        frame.getContentPane().add(loss);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400,760);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void winningMenu() {
        PicturePanel loss = new PicturePanel(".\\production\\BattleShip\\pepewin.jpg");
        /*JLabel losingText = new JLabel("YOU LOST");
        loss.add(losingText);*/
        frame.dispose();
        JFrame frame = new JFrame();
        frame.getContentPane().add(loss);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400,760);
        frame.setVisible(true);
        frame.setResizable(false);
    }
    public Client getClient() {
        return client;
    }
}
