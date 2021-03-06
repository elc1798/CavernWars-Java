package com.cavernwars.entities.underground;

import java.awt.Rectangle;

import com.cavernwars.Controller;
import com.cavernwars.entities.Entity;

/*
 * Chargers are "UnderGround" or "UG" units.
 * Cost: 75 + 25 * Level
 * Stats:
 * Health: 3 + Level
 * Speed: 2
 * Damage: 1 + Level * 2
 *
 * Special:
 * When this unit arrives on a new platform, it charges forward at max speed until it hits a unit.
 * When it collides, it deals additional damage based on how long it was charging.
 */
public class Charger extends Entity {

    private int[][] path;

    private int pathCounter = 1;

    // Unique variables for this unit
    private boolean charging = false;
    private long chargeTime;

    public Charger(Controller c , int id) {
        session = c;
        ent_ID = id;

        path = Entity.UGPATH;
        this.setSprite("/resources/UGCharger.png");
        this.setX(path[0][0]);
        this.setY(path[0][1]);
        this.setMaxHealth((int)session.UGLevels[3] + 3);
        this.setHealth((int)session.UGLevels[3] + 3);
        this.setSpeed(2);
        this.setDamage((int)session.UGLevels[3] * 2 + 1);

        cost = 75 + 25 * (int)session.UGLevels[3];

        hitbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] , Entity.SPRITESIZE[1]);
        facingRight = true;
        onLadder = false;
        attackbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] + 30 , Entity.SPRITESIZE[1]);
        attackTime = 0;
        attacking = false;
        charging = false;
        chargeTime = System.currentTimeMillis();

        ground = 1;
        type = 3;
    }

    @Override
    public void move() {
        if (pathCounter < path.length && !attacking) {
            charging = false;
            if (getX() == path[pathCounter][0] && getY() == path[pathCounter][1]) {
                pathCounter++;
            }
            if (path[pathCounter][2] == 1) {
                setX(path[pathCounter][0]);
                setY(path[pathCounter][1]);
                pathCounter++;
            }
            int dx , dy;
            if (path[pathCounter - 1][0] - path[pathCounter][0] == 0) {
                onLadder = true;
                dx = 0;
                dy = getSpeed() % (path[pathCounter][1] - getY());
                dy = -dy;
                if (dy == 0) {
                    dy = -1;
                }
            } else if (path[pathCounter - 1][1] - path[pathCounter][1] == 0) {
                onLadder = false;
                dy = 0;
                setSpeed(5);
                charging = true;
                if (path[pathCounter][0] > path[pathCounter - 1][0]) {
                    dx = getSpeed() % ((int)Math.abs((float)(path[pathCounter][0] - getX())));
                    if (dx == 0) {
                        dx = 1;
                    }
                } else {
                    dx = getSpeed() % ((int)Math.abs((float)(path[pathCounter][0] - getX())));
                    dx = -dx;
                    if (dx == 0) {
                        dx = -1;
                    }
                }
            } else {
                onLadder = false;
                if (path[pathCounter][1] - getY() == 0) {
                    dy = 0;
                    charging = true;
                    setSpeed(5);
                } else {
                    dy = getSpeed() % (path[pathCounter][1] - getY());
                    dy = -dy;
                    if (dy == 0) {
                        dy = -1;
                    }
                }
                if (path[pathCounter][0] - getX() == 0) {
                    dx = 0;
                } else {
                    if (path[pathCounter][0] > path[pathCounter - 1][0]) {
                        dx = getSpeed() % ((int)Math.abs((float)(path[pathCounter][0] - getX())));
                        if (dx == 0) {
                            dx = 1;
                        }
                    } else {
                        dx = getSpeed() % ((int)Math.abs((float)(path[pathCounter][0] - getX())));
                        dx = -dx;
                        if (dx == 0) {
                            dx = -1;
                        }
                    }
                }
            }
            if (!charging) { // Only reset if this unit is MOVING
                chargeTime = System.currentTimeMillis();
            }
            setSpeed(2);
            setX(getX() + dx);
            setY(getY() + dy);
            facingRight = path[pathCounter][0] - path[pathCounter - 1][0] > 0;
        }
        if (facingRight) {
            if (getX() > path[pathCounter][0]) {
                setX(path[pathCounter][0]);
            }
            attackbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] + 30 , Entity.SPRITESIZE[1]);
        } else {
            if (getX() < path[pathCounter][0]) {
                setX(path[pathCounter][0]);
            }
            attackbox = new Rectangle(getX() - 30, getY() , Entity.SPRITESIZE[0] + 30 , Entity.SPRITESIZE[1]);
        }
        hitbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] , Entity.SPRITESIZE[1]);
    }

    @Override
    public void attack() {
        // Can only hit one unit at a time!
        if (System.currentTimeMillis() - attackTime > attackDelay) {
            if (!onLadder) {
                for (Entity e : session.aboveGrounders) {
                    if (this.attackbox.intersects(e.hitbox)) {
                        charging = false;
                        int bonusDmg = (int)(System.currentTimeMillis() - chargeTime) / 1000 * (int)session.UGLevels[3];
                        chargeTime = System.currentTimeMillis();
                        if (session.UGKingSpawned) {
                            e.setHealth(e.getHealth() - this.getDamage() * 2 - bonusDmg);
                        } else {
                            e.setHealth(e.getHealth() - this.getDamage() - bonusDmg);
                        }
                        attacking = true;
                        attackTime = System.currentTimeMillis();
                        break;
                    }
                    attacking = false;
                }
            }
        }
    }
}