package com.cavernwars.buttons;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.cavernwars.Controller;
import com.cavernwars.entities.underground.Charger;

public class UGChargerBUTTON {

    private Controller session;
    private BufferedImage icon;

    public final Rectangle box = new Rectangle(710 , 90 , 40 , 40);

    public UGChargerBUTTON(Controller c) {
        session = c;
        try {
            icon = ImageIO.read(new File(getClass().getResource("/resources/buttons/Charger.png").toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void spawn() {
        Charger newbie = new Charger(session , session.UGUnitCounter);
        session.UGUnitCounter += 1;
        session.UGLevels[3] += 0.1;
        session.underGrounders.add(newbie);
        session.playerMoney -= newbie.cost;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(icon , 710 , 90 , 40 , 40 , null);
        Toolkit.getDefaultToolkit().sync();
    }
}
