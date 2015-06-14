package com.cavernwars.buttons;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.cavernwars.Controller;
import com.cavernwars.entities.underground.Miner;

public class UGMinerBUTTON {

    private Controller session;
    private BufferedImage icon;

    public UGMinerBUTTON(Controller c) {
        c = session;
        try {
            icon = ImageIO.read(new File(getClass().getResource("/resources/buttons/Miner.png").toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void spawn() {
        Miner newbie = new Miner(session , (int)(session.UGLevels[4] * 10));
        session.UGLevels[4] += 0.1;
        session.underGrounders.add(newbie);
        session.playerMoney -= newbie.cost;
    }

    public void paintComponent(Graphics g) {
        g.drawImage(icon , 600 , 150 , 40 , 40 , null);
        Toolkit.getDefaultToolkit().sync();
    }
}
