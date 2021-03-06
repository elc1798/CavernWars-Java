package com.cavernwars.GUI;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.cavernwars.Controller;
import com.cavernwars.entities.Entity;

@SuppressWarnings("serial")
public class GfxRenderer extends JPanel implements Runnable {

    private BufferedImage playScreen;
    private Controller session;

    private static final int DELAY = 24;
    private Thread animus; //Animation driver
    private boolean gameEnd = false;

    public GfxRenderer(Controller session) {
        this.session = session;
        try {
            playScreen = ImageIO.read(new File(getClass().getResource("/resources/CavernWarsBackground.png").toURI()));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void endScreen(int WINNER) {
        gameEnd = true;
        if (WINNER == 0) {
            try {
                playScreen = ImageIO.read(new File(getClass().getResource("/resources/CavernWarsLoseScreen.png").toURI()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                playScreen = ImageIO.read(new File(getClass().getResource("/resources/CavernWarsWinScreen.png").toURI()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        session.aboveGrounders.clear();
        session.underGrounders.clear();
        session.traps.clear();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(playScreen, 0, 0, null);
        // Button rendering:
        session.b0.paintComponent(g);
        session.b1.paintComponent(g);
        session.b2.paintComponent(g);
        session.b3.paintComponent(g);
        session.b4.paintComponent(g);
        session.b5.paintComponent(g);
        session.b6.paintComponent(g);
        // Description rendering:
        session.getCurrentDescriptor().paintComponent(g);
        // Healthbar rendering:
        session.getHealthBars().paintComponent(g);
        for (Entity e : session.aboveGrounders) {
            e.paintComponent(g);
        }
        for (Entity e : session.underGrounders) {
            e.paintComponent(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        animus = new Thread(this);
        animus.start();
    }

    @Override
    public void run() {
        long beforeTime, timeDiff, sleep;
        beforeTime = System.currentTimeMillis();
        while (!gameEnd) {
            for (Entity e : session.aboveGrounders) {
                e.update();
            }
            for (Entity e : session.underGrounders) {
                e.update();
            }
            session.update();
            repaint();
            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;
            if (sleep < 0) {
                sleep = 2;
            }
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }
            beforeTime = System.currentTimeMillis();
        }
    }
}