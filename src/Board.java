import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.border.Border;

public class Board {
    ArrayList<HittableButton> playerPanelList = new ArrayList<>();
    ArrayList<HittableButton> oPanelList = new ArrayList<>();
    JFrame frame = new JFrame();
    int health = 15;
    boolean lost = false;
    boolean isTurn = true;

    public Board() {
        PicturePanel root = new PicturePanel("battleship.jpg");
        GridBagLayout lay = new GridBagLayout();
        root.setLayout(lay);
        GridBagConstraints c = new GridBagConstraints();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                HittableButton p = new HittableButton();
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
                playerPanelList.add(p);

                }
                HittablePanel p = new HittablePanel();
                p.setOpaque(false);
                c.ipadx = 100;
                c.ipady = 20;
                c.gridx = 10;
                //GRIDY!!!!
                c.gridy = i+1;
                root.add(p,c);

            for (int j = 11; j < 21; j++) {
                HittableButton oPanels = new HittableButton();
                oPanels.setBackground(new Color(255,255,255,120));
                oPanels.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                oPanels.setOpaque(true);
                c.ipadx = 40;
                c.ipady = 40;
                c.gridx = j;
                c.gridy = i + 1;
                oPanels.addActionListener(e -> {
                    if (isTurn) {
                        oPanels.hit();
                        isTurn = false;
                    }
                });
                root.add(oPanels, c);
                oPanelList.add(oPanels);
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
        playerPanelList.get(panel).setBackground(bg);
    }
    public int getHealth() {
        System.out.println(health);
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public void losingMenu() {
        PicturePanel loss = new PicturePanel("loss.jpg");
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
        PicturePanel loss = new PicturePanel("pepewin.jpg");
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
}
