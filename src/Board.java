import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.border.Border;

public class Board {
    ArrayList<JPanel> playerPanelList = new ArrayList<>();
    ArrayList<JPanel> oPanelList = new ArrayList<>();

    public Board() {
        JFrame frame = new JFrame();
        PicturePanel root = new PicturePanel("battleship.jpg");
        GridBagLayout lay = new GridBagLayout();
        root.setLayout(lay);
        GridBagConstraints c = new GridBagConstraints();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JPanel p = new JPanel();
                p.setBackground(new Color(255,255,255,120));
                p.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                p.setOpaque(true);
                c.ipadx = 20;
                c.ipady = 20;
                c.gridx = j;
                c.gridy = i + 1;
                root.add(p, c);
                playerPanelList.add(p);

                }
                JPanel p = new JPanel();
                p.setOpaque(false);
                c.ipadx = 100;
                c.ipady = 20;
                c.gridx = 10;
                //GRIDY!!!!
                c.gridy = i+1;
                root.add(p,c);

            for (int j = 11; j < 21; j++) {
                JPanel oPanels = new JPanel();
                oPanels.setBackground(new Color(255,255,255,120));
                oPanels.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                oPanels.setOpaque(true);
                c.ipadx = 20;
                c.ipady = 20;
                c.gridx = j;
                c.gridy = i + 1;
                root.add(oPanels, c);
                oPanelList.add(oPanels);
            }


        }


        Timer t = new Timer();

        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                root.repaint();
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
}
