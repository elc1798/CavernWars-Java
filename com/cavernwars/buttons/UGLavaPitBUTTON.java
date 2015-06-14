package com.cavernwars.buttons;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.cavernwars.Controller;
import com.cavernwars.entities.underground.towers.LavaPit;

public class UGLavaPitBUTTON {

    private Controller session;
    private BufferedImage icon;

    public UGLavaPitBUTTON(Controller c) {
        c = session;
        try {
            icon = ImageIO.read(new File(getClass().getResource("/resources/buttons/LavaPit.png").toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void spawn(int x , int y) {
        LavaPit newbie = new LavaPit(session , session.trapIDCounter , x , y);
        session.trapIDCounter += 1;
        session.traps.add(newbie);
        session.playerMoney -= newbie.cost;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(icon , 700 , 320 , 40 , 40 , null);
        Toolkit.getDefaultToolkit().sync();
    }
}
