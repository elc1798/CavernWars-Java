package com.cavernwars.buttons;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.cavernwars.Controller;
import com.cavernwars.entities.underground.towers.StalaciteDrop;

public class UGStalaciteDropBUTTON {

    private Controller session;
    private BufferedImage icon;

    public final Rectangle box = new Rectangle(580 , 320 , 40 , 40);

    public UGStalaciteDropBUTTON(Controller c) {
        c = session;
        try {
            icon = ImageIO.read(new File(getClass().getResource("/resources/buttons/StalaciteDrop.png").toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void spawn(int x , int y) {
        StalaciteDrop newbie = new StalaciteDrop(session , session.trapIDCounter , x , y);
        session.trapIDCounter += 1;
        session.traps.add(newbie);
        session.playerMoney -= newbie.cost;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(icon , 580 , 320 , 40 , 40 , null);
        Toolkit.getDefaultToolkit().sync();
    }
}
