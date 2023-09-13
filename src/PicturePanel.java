//Code for PicturePanel (formally picPanel) comes from the following link to intellipaat.com
//https://intellipaat.com/community/74492/

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PicturePanel extends JPanel{

    private BufferedImage image;

    private int w,h;

    public PicturePanel(String fname){


        //reads the image

        try {

            image = ImageIO.read(new File(fname));

            w = image.getWidth();

            h = image.getHeight();

        } catch (IOException ioe) {

            System.out.println("Could not read in the pic");

            //System.exit(0);

        }

    }

    public Dimension getPreferredSize() {

        return new Dimension(w,h);

    }

    //this will draw the image

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        g.drawImage(image,0,0,this);

    }

}

