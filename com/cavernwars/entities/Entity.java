package com.cavernwars.entities;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public abstract class Entity {

    private int x;
    private int y;
    private int health;
    /* Note the speed is a ratio. It should take an entity of speed 1 to arrive at the next
     * destination on its path in "n" amount of seconds. Speed 2 should arrive in n/2 seconds,
     * speed 3 should arrive in n/3, etc.
     */
    private int speed;
    private int damage;
    private BufferedImage sprite;

    public int getX() {return x;}
    public int getY() {return y;}
    public int getHealth() {return health;}
    public int getSpeed() {return speed;}
    public int getDamage() {return damage;}
    public BufferedImage getSprite() {return sprite;}

    // Constants for Entities
    public static final int[] SPRITESIZE = {20 , 20};
    public static final int[][] AGPATH = {
        {100 , 80 , 10},
        {500 , 80 , 0},
        {510 , 120 , 4},
        {450 , 160 , 8},
        {5 , 160 , 6},
        {5 , 235 , 10},
        {500 , 235 , 6},
        {500 , 310 , 10},
        {5 , 310 , 6},
        {5 , 400 , 10},
        {500 , 400 , 6},
        {500 , 425 , 10},
        {5 , 425 , 8},
        {5 , 550 , 10},
        {430 , 550 , -1}
    };
    public static final int[][] UGPATH = {
        {430 , 550 , 10},
        {5 , 550 , 8},
        {5 , 425 , 10},
        {500 , 425 , 6},
        {500 , 400 , 10},
        {5 , 400 , 6},
        {5 , 310 , 10},
        {500 , 310 , 6},
        {500 , 235 , 10},
        {5 , 235 , 6},
        {5 , 160 , 8},
        {450 , 160 , 4},
        {510 , 120 , 0},
        {500 , 80 , 10},
        {120 , 80 , -1}
    };


    public void setX(int _x) {
        x = _x;
    }

    public void setY(int _y) {
        y = _y;
    }

    public void setHealth(int _health) {
        health = _health;
    }

    public void setSpeed(int _speed) {
        speed = _speed;
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

    public void move() {}
    public void attack() {}
    public void special() {}

    public void paintComponent(Graphics g , int xcoor , int ycoor) {
        g.drawImage(sprite , xcoor , ycoor , SPRITESIZE[0] , SPRITESIZE[1] , null);
        Toolkit.getDefaultToolkit().sync();
    }

    public void update() {
        move();
        attack();
        special();
    }
}