package com.cavernwars.entities.underground;

import java.awt.Rectangle;

import com.cavernwars.Controller;
import com.cavernwars.entities.Entity;

/*
 * Miners are "UnderGround" or "UG" units.
 * Cost: 120 + 40 * Level
 * Stats:
 * Health: 5 + Level
 * Speed: 1
 * Damage: 1 + Level
 *
 * Special:
 * This unit attacks all units in its attack zone at the same time.
 */
public class Miner extends Entity {

    private int[][] path;

    private int pathCounter = 1;

    public Miner(Controller c , int id) {
        session = c;
        ent_ID = id;

        path = Entity.UGPATH;
        this.setSprite("/resources/UGMiner.png");
        this.setX(path[0][0]);
        this.setY(path[0][1]);
        this.setMaxHealth((int)session.UGLevels[4] + 5);
        this.setHealth((int)session.UGLevels[4] + 5);
        this.setSpeed(1);
        this.setDamage((int)session.UGLevels[4] + 1);

        cost = 120 + 40 * (int)session.UGLevels[4];

        hitbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] , Entity.SPRITESIZE[1]);
        facingRight = true;
        onLadder = false;
        attackbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] + 30 , Entity.SPRITESIZE[1]);
        attackTime = 0;
        attacking = false;

        ground = 1;
        type = 4;
    }

    @Override
    public void move() {
        if (pathCounter < path.length && !attacking) {
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
                if (path[pathCounter][1] - getY() == 0) {
                    dy = 0;
                } else {
                    dy = getSpeed() % (path[pathCounter][1] - getY());
                    dy = -dy;
                    if (dy == 0) {
                        dy = -1;
                    }
                }
            }
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
        if (System.currentTimeMillis() - attackTime > attackDelay) {
            if (!onLadder) {
                boolean hasAttacked = false;
                for (Entity e : session.aboveGrounders) {
                    if (this.attackbox.intersects(e.hitbox)) {
                        if (session.UGKingSpawned) {
                            e.setHealth(e.getHealth() - this.getDamage() * 2);
                        } else {
                            e.setHealth(e.getHealth() - this.getDamage());
                        }
                        hasAttacked = true;
                        attackTime = System.currentTimeMillis();
                    }
                }
                attacking = hasAttacked;
            }
        }
    }
}