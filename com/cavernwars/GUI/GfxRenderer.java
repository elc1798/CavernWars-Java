package com.cavernwars.GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GfxRenderer extends JPanel {
    private BufferedImage playScreen;

    public GfxRenderer() {
        try {
            playScreen = ImageIO.read(new File(getClass().getResource("/resources/TESTFILE.png").toURI()));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(playScreen, 0, 0, null);
    }
}
