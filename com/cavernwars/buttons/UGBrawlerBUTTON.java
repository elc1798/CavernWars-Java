package com.cavernwars.buttons;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.cavernwars.Controller;
import com.cavernwars.entities.underground.Brawler;

public class UGBrawlerBUTTON {

    private Controller session;
    private BufferedImage icon;

    public final Rectangle box= new Rectangle(640 , 90 , 40 , 40);

    public UGBrawlerBUTTON(Controller c) {
        session = c;
        try {
            icon = ImageIO.read(new File(getClass().getResource("/resources/buttons/Brawler.png").toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void spawn() {
        Brawler newbie = new Brawler(session , session.UGUnitCounter);
        session.UGUnitCounter += 1;
        session.UGLevels[2] += 0.1;
        session.underGrounders.add(newbie);
        session.playerMoney -= newbie.cost;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(icon , 640 , 90 , 40 , 40 , null);
        Toolkit.getDefaultToolkit().sync();
    }
}
