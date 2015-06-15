package com.cavernwars.GUI;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.cavernwars.Controller;

public class HealthBars {

    private BufferedImage icon;
    private Controller session;

    public HealthBars(Controller c) {
        session = c;
        try {
            icon = ImageIO.read(new File(getClass().getResource("/resources/HealthBarSegment.png").toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        g.drawImage(icon , 5 , 15 , session.AIHealth , 5 , null);
        g.drawImage(icon , 520 , 15 , -session.playerHealth , 5 , null);
        Toolkit.getDefaultToolkit().sync();
    }
}
