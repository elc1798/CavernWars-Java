package com.cavernwars.GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.cavernwars.Controller;

@SuppressWarnings("serial")
public class GfxRenderer extends JPanel {

    private BufferedImage playScreen;
    private Controller session;

    public GfxRenderer(Controller session) {
        this.session = session;
        try {
            playScreen = ImageIO.read(new File(getClass().getResource("/resources/CavernWarsBackground.png").toURI()));
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