import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class Board {

    public Board() {
        JFrame frame = new JFrame();
        PicturePanel root = new PicturePanel("battleship2.jpg");
        GridBagLayout lay = new GridBagLayout();
        root.setLayout(lay);
        GridBagConstraints c = new GridBagConstraints();


        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JPanel p = new JPanel();
                p.setBackground(Color.BLACK);
                p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                if (i % 2 == 0 || j % 2 == 1) {
                    p.setOpaque(false);
                }
                //p.setOpaque(false);
                c.ipadx = 20;
                c.ipady = 20;
                c.gridx = j;
                c.gridy = i+1;
                root.add(p, c);
            }
        }

//        JButton b1 = new JButton("Button 1");
//        c.fill = GridBagConstraints.HORIZONTAL;
//        c.gridwidth = 10;
//        c.ipady = 40;
//        c.gridx = 0;
//        c.gridy = 0;
//        root.add(b1,c);
//
//        JButton b2 = new JButton("Button 2");
//        c.weighty = 1.0;
//        c.gridx = 0;
//        c.gridy = 1;
//        root.add(b2, c);
//
//        JButton b3 = new JButton("Button 3");
//        c.weighty = 0;
//        c.gridx = 0;
//        c.gridy = 2;
//        root.add(b3, c);







        frame.getContentPane().add(root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,760);
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
