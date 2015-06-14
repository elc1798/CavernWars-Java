package com.cavernwars.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.cavernwars.Controller;

public abstract class Entity {

    /*
     * FOR TESTING PURPOSES ONLY
     */
    public boolean show = true;

    // Game Session info
    public Controller session;

    // Unit information:
    private int x;
    private int y;
    private int maxHealth;
    private int health;
    private int speed;
    private int damage;
    private BufferedImage sprite;

    public int cost;

    // Data required for unit movement and attacking
    public Rectangle hitbox;
    public Rectangle attackbox;
    public boolean facingRight;
    public boolean onLadder;
    public int ent_ID;
    public long attackTime;
    public boolean attacking;
    public int ground;
    public int type;

    public final long attackDelay = 1000; // 1 s between attacks

    // Get functions for unit information
    public int getX() {return x;}
    public int getY() {return y;}
    public int getMaxHealth() {return maxHealth;};
    public int getHealth() {return health;}
    public int getSpeed() {return speed;}
    public int getDamage() {return damage;}
    public BufferedImage getSprite() {return sprite;}

    // Constants for Entities
    public static final int[] SPRITESIZE = {20 , 20};
    public static final int[][] AGPATH = {
        {100 , 80 , 0},
        {500 , 80 , 0},
        {510 , 120 , 1},
        {450 , 160 , 0},
        {5 , 160 , 0},
        {5 , 235 , 0},
        {500 , 235 , 0},
        {500 , 310 , 0},
        {5 , 310 , 0},
        {5 , 400 , 0},
        {500 , 400 , 0},
        {500 , 475 , 0},
        {5 , 475 , 0},
        {5 , 550 , 0},
        {430 , 550 , 0}
    };
    public static final int[][] UGPATH = {
        {430 , 550 , 0},
        {5 , 550 , 0},
        {5 , 475 , 0},
        {500 , 475 , 0},
        {500 , 400 , 0},
        {5 , 400 , 0},
        {5 , 310 , 0},
        {500 , 310 , 0},
        {500 , 235 , 0},
        {5 , 235 , 0},
        {5 , 160 , 0},
        {450 , 160 , 0},
        {510 , 120 , 0},
        {500 , 80 , 1},
        {120 , 80 , 0}
    };

    // Set functions for unit information
    public void setX(int _x) {x = _x;}

    public void setY(int _y) {y = _y;}

    public void setMaxHealth(int _mHealth) {maxHealth = _mHealth;}

    public void setHealth(int _health) {
        health = _health;
        if (health > maxHealth) {
            health = maxHealth;
        }
        if (health <= 0) {
            session.killQueue.add(this);
            if (ground == 0) {
                session.playerMoney += cost / 2;
                if (type == 0) {
                    session.AGKingSpawned = false;
                }
            } else {
                session.AIMoney += cost / 2;
                if (type == 0) {
                    session.UGKingSpawned = false;
                }
            }
        }
    }

    public void setSpeed(int _speed) {
        speed = _speed;
        if (speed > 5) {
            speed = 5;
        }
    }

    public void setDamage(int _damage) {damage = _damage;}

    public void setSprite(String filename) {
        try {
            sprite = ImageIO.read(new File(getClass().getResource(filename).toURI()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Unit gameplay functions
    public void move() {}
    public void attack() {}
    public void special() {}

    public void paintComponent(Graphics g , int xcoor , int ycoor) {
        g.drawImage(sprite , xcoor , ycoor , SPRITESIZE[0] , SPRITESIZE[1] , null);
        if (show) {
            g.setColor(Color.BLUE);
            g.drawRect(attackbox.x, attackbox.y, attackbox.width, attackbox.height);
            g.setColor(Color.GREEN);
            g.drawRect(hitbox.x, hitbox.y, hitbox.width, hitbox.height);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    public void update() {
        try {
            move();
        } catch(Exception e) {}
        attack();
        special();
    }
}