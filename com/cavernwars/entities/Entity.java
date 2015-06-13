package com.cavernwars.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public abstract class Entity {

    private int x;
    private int y;
    private int health;
    private BufferedImage sprite;

    public int getX() {return x;}
    public int getY() {return y;}
    public int getHealth() {return health;}
    public BufferedImage getSprite() {return sprite;}

    public void setX(int _x) {
        x = _x;
    }

    public void setY(int _y) {
        y = _y;
    }

    public void setHealth(int _health) {
        health = _health;
    }

    public void setSprite(String filename) {
        try {
            sprite = ImageIO.read(new File(getClass().getResource("/resources/CavernWarsBackground.png").toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void move() {}
    public void attack() {}
    public void special() {}

    public void paintComponent(Graphics g , int xcoor , int ycoor) {
        g.drawImage(sprite , xcoor , ycoor , null);
    }

    public void update() {
        move();
        attack();
        special();
    }
}