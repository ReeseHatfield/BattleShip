package me.braintrust.battleship;

import me.braintrust.battleship.utils.Settings;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;

import java.util.Timer;
import java.util.TimerTask;

public class Board {

    private final List<HittableButton> playerButtons = new ArrayList<>();
    private final List<HittableButton> otherButtons = new ArrayList<>();

    private final JFrame frame = new JFrame();
    private final Client client;
    private int health = 0;

    public Board(Client client) {
        this.client = client;
        this.frame.setSize(1400, 760);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        PicturePanel root = new PicturePanel("/battleship.jpg");
        root.setLayout(new BorderLayout());

        JPanel overlay = new JPanel();
        overlay.setOpaque(false);
        overlay.setLayout(new BoxLayout(overlay, BoxLayout.X_AXIS));
        overlay.add(configureBoard(playerButtons));
        overlay.add(configureBoard(otherButtons));
        root.add(overlay, BorderLayout.CENTER);

        frame.getContentPane().add(root);
        frame.setVisible(true);
    }

    private JPanel configureBoard(List<HittableButton> buttons) {
        JPanel board = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        for (int i=0; i<=10; i++) {
            for (int j=0; j<=10; j++) {
                // Ignore the top left corner!
                if (i == 0 && j == 0) {
                    continue;
                }

                JComponent cell;
                if (i == 0) {
                    cell = configureCell(new JLabel(String.valueOf((char) ('A' + j - 1))), Color.PINK);
                } else if (j == 0) {
                    cell = configureCell(new JLabel(String.valueOf(i)));
                } else {
                    cell = configureCell(new HittableButton(this, i, j));
                }

                // Add the buttons to the board!
                if (cell instanceof HittableButton button) {
                    button.setAction();
                    buttons.add(button);
                }

                constraints.gridx = i;
                constraints.gridy = j;
                board.add(cell, constraints);
            }
        }

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                board.repaint();
            }
        }, 0, 1000);

        board.setOpaque(false);
        return board;
    }

    private JComponent configureCell(JComponent cell) {
        return configureCell(cell, new Color(255, 255, 255, 120));
    }

    private JComponent configureCell(JComponent cell, Color color) {
        if (cell instanceof JLabel label) {
            label.setHorizontalAlignment(SwingConstants.CENTER);
        }

        cell.setBackground(color);
        cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cell.setOpaque(true);
        cell.setPreferredSize(new Dimension(40, 40));
        return cell;
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

    public List<HittableButton> getPlayerButtons() {
        return playerButtons;
    }

    public List<HittableButton> getOtherButtons() {
        return otherButtons;
    }

    public Client getClient() {
        return client;
    }

    public int getHealth() {
        System.out.println("Health: " + health);
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
