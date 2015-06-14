package com.cavernwars.entities.underground.towers;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Trap {

    // Information
    private int x;
    private int y;
    private int damage;
    private BufferedImage sprite;

    // Data required for unit movement and attacking
    public Rectangle hitbox;
    public Rectangle attackbox;
    public boolean disabled = false;
    public int trap_ID;

    public int[] SPRITESIZE = {20 , 20};

    // Get functions for unit information
    public int getX() {return x;}
    public int getY() {return y;}
    public int getDamage() {return damage;}
    public BufferedImage getSprite() {return sprite;}

    public void setX(int _x) {
        x = _x;
    }

    public void setY(int _y) {
        y = _y;
    }

    public void setDamage(int _damage) {
        damage = _damage;
    }

    public void setSprite(String filename) {
        try {
            sprite = ImageIO.read(new File(getClass().getResource(filename).toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void attack() {}

    public void paintComponent(Graphics g , int xcoor , int ycoor) {
        g.drawImage(sprite , xcoor , ycoor , SPRITESIZE[0] , SPRITESIZE[1] , null);
        Toolkit.getDefaultToolkit().sync();
    }

    public void update() {
        if (!disabled) {
            attack();
        }
    }
}
