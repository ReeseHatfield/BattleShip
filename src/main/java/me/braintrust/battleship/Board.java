package me.braintrust.battleship;

import me.braintrust.battleship.utils.Settings;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

import java.util.Timer;
import java.util.TimerTask;

public class Board {

    private final List<HittableButton> playerBoard = new ArrayList<>();
    private final List<HittableButton> otherBoard = new ArrayList<>();

    private JFrame frame = new JFrame();
    private int health = 0;
    private boolean lost = false;
    private int i = 0;
    private int j = 0;
    private Client client;

    public Board(Client client) {
        this.client = client;
        PicturePanel root = new PicturePanel("/battleship.jpg");
        GridBagLayout lay = new GridBagLayout();
        root.setLayout(lay);
        GridBagConstraints c = new GridBagConstraints();
        for (i = 0; i < 10; i++) {
            for (j = 0; j < 11; j++) {
                if (j == 0) {
                    JLabel leftSide = new JLabel("" + (char) ('A' + i), SwingConstants.CENTER);
                    leftSide.setBackground(new Color(255, 255, 255, 120));
                    leftSide.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    if (j == 10) {
                        c.ipadx = 19;
                        c.ipady = 20;
                        c.gridx = j;
                        c.gridy = i + 1;
                        leftSide.setBackground(Color.PINK);
                        leftSide.setOpaque(true);
                        root.add(leftSide, c);
                        continue;
                    }
                    c.ipadx = 20;
                    c.ipady = 20;
                    c.gridx = j;
                    c.gridy = i + 1;
                    leftSide.setBackground(Color.PINK);
                    leftSide.setOpaque(true);
                    root.add(leftSide, c);
                    continue;
                }
                HittableButton p = new HittableButton(this);
                p.setBackground(new Color(255, 255, 255, 120));
                p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                p.setOpaque(true);
                c.ipadx = 40;
                c.ipady = 40;
                c.gridx = j;
                c.gridy = i + 1;
                p.addActionListener(e -> {
                    System.out.println("button " + p.isShip());
                });
                p.setEnabled(false);

                root.add(p, c);
                playerBoard.add(p);

                JLabel topRow = new JLabel("" + j, SwingConstants.CENTER);
                topRow.setBackground(new Color(255, 255, 255, 120));
                topRow.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                c.ipadx = 30;
                c.ipady = 20;
                c.gridx = j;
                c.gridy = 0;
                topRow.setOpaque(true);
                root.add(topRow, c);

            }
            HittablePanel p = new HittablePanel();
            p.setOpaque(false);
            c.ipadx = 100;
            c.ipady = 20;
            c.gridx = 11;
            // GRIDY!!!!
            c.gridy = i + 1;
            root.add(p, c);

            for (j = 12; j < 23; j++) {
                if (j == 12) {
                    JLabel leftSide = new JLabel("" + (char) ('A' + i), SwingConstants.CENTER);
                    leftSide.setBackground(new Color(255, 255, 255, 120));
                    leftSide.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    if (j == 21) {
                        c.ipadx = 19;
                        c.ipady = 20;
                        c.gridx = j;
                        c.gridy = i + 1;
                        leftSide.setBackground(Color.PINK);
                        leftSide.setOpaque(true);
                        root.add(leftSide, c);
                        continue;
                    }
                    c.ipadx = 20;
                    c.ipady = 20;
                    c.gridx = j;
                    c.gridy = i + 1;
                    leftSide.setBackground(Color.PINK);
                    leftSide.setOpaque(true);
                    root.add(leftSide, c);
                    continue;
                }
                HittableButton oButton = new HittableButton(this, j, i + 1);
                oButton.setBackground(new Color(255, 255, 255, 120));
                oButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                oButton.setOpaque(true);
                c.ipadx = 40;
                c.ipady = 40;
                c.gridx = j;
                c.gridy = i + 1;

                root.add(oButton, c);
                otherBoard.add(oButton);

                JLabel topRow = new JLabel("" + (j - 12), SwingConstants.CENTER);
                topRow.setBackground(new Color(255, 255, 255, 120));
                topRow.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                c.ipadx = 30;
                c.ipady = 20;
                c.gridx = j;
                c.gridy = 0;
                topRow.setOpaque(true);
                root.add(topRow, c);
            }

        }

        Timer winCheckTimer = new Timer();

        winCheckTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!lost) {
                    root.repaint();
                }
            }
        }, 0, 1000);

        frame.getContentPane().add(root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 760);
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

    public void displayEndMenu(PicturePanel pp) {
        frame.dispose();
        JFrame frame = new JFrame();
        frame.getContentPane().add(pp);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Settings.GAME_WIDTH_PX, Settings.GAME_HEIGHT_PX);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public Client getClient() {
        return client;
    }

    public List<HittableButton> getPlayerBoard() {
        return playerBoard;
    }

    public List<HittableButton> getOtherBoard() {
        return otherBoard;
    }
}
