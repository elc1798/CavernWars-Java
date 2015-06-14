package com.cavernwars.buttons;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.cavernwars.Controller;
import com.cavernwars.entities.underground.Berserker;

public class UGBerserkerBUTTON {

    private Controller session;
    private BufferedImage icon;

    public final Rectangle box = new Rectangle(570 , 90 , 40 , 40);

    public UGBerserkerBUTTON(Controller c) {
        c = session;
        try {
            icon = ImageIO.read(new File(getClass().getResource("/resources/buttons/Berserker.png").toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void spawn() {
        Berserker newbie = new Berserker(session , (int)(session.UGLevels[1] * 10));
        session.UGLevels[1] += 0.1;
        session.underGrounders.add(newbie);
        session.playerMoney -= newbie.cost;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(icon , 570 , 90 , 40 , 40 , null);
        Toolkit.getDefaultToolkit().sync();
    }
}
