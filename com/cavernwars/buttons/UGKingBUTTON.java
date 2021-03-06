package com.cavernwars.buttons;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.cavernwars.Controller;
import com.cavernwars.entities.underground.UGKing;

public class UGKingBUTTON {

    private Controller session;
    private BufferedImage icon;

    public final Rectangle box = new Rectangle(680 , 150 , 40 , 40);

    public UGKingBUTTON(Controller c) {
        session = c;
        try {
            icon = ImageIO.read(new File(getClass().getResource("/resources/buttons/King.png").toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void spawn() {
        UGKing newbie = new UGKing(session , session.UGUnitCounter);
        session.UGUnitCounter += 1;
        session.UGLevels[0] += 0.1;
        session.underGrounders.add(newbie);
        session.playerMoney -= newbie.cost;
        session.UGKingSpawned = true;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(icon , 680 , 150 , 40 , 40 , null);
        Toolkit.getDefaultToolkit().sync();
    }
}
