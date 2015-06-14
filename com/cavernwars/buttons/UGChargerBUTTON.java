package com.cavernwars.buttons;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.cavernwars.Controller;
import com.cavernwars.entities.underground.Charger;

public class UGChargerBUTTON {

    private Controller session;
    private BufferedImage icon;

    public UGChargerBUTTON(Controller c) {
        c = session;
        try {
            icon = ImageIO.read(new File(getClass().getResource("/resources/buttons/Charger.png").toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void spawn() {
        Charger newbie = new Charger(session , (int)(session.UGLevels[3] * 10));
        session.UGLevels[3] += 0.1;
        session.underGrounders.add(newbie);
        session.playerMoney -= newbie.cost;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(icon , 710 , 90 , 40 , 40 , null);
        Toolkit.getDefaultToolkit().sync();
    }
}
