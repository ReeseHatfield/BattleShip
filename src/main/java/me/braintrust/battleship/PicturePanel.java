package me.braintrust.battleship;

//Code for main.java.battleship.PicturePanel (formally picPanel) comes from the following link to intellipaat.com
//https://intellipaat.com/community/74492/

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class PicturePanel extends JPanel{

    private BufferedImage image;
    private int width;
    private int height;

    public PicturePanel(String fname) {
        //reads the image
        try {
            URL resourceUrl = getClass().getResource(fname);
            if (resourceUrl == null) throw new IllegalArgumentException("Resource not found: " + fname);
            image = ImageIO.read(resourceUrl);
            width = image.getWidth();
            height = image.getHeight();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    //this will draw the image
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image,0,0,this);
    }
}
