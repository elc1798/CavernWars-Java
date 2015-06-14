package com.cavernwars.entities.aboveground;

import java.awt.Rectangle;

import com.cavernwars.Controller;
import com.cavernwars.entities.Entity;

/*
 * Priests are "AboveGround" or "AG" units.
 * Cost: 100 + 200 * Level
 * Stats:
 * Health: 2 + 0.5 * Level
 * Speed: 1
 * Damage: 0
 *
 * Special:
 * Will heal all units in a radius based on level by priest level + that unit's level
 */
public class Priest extends Entity {

    private int[][] path;

    private int pathCounter = 1;

    // Unique variables for this unit
    private boolean attacked = false;

    public Priest(Controller c , int id) {
        session = c;
        ent_ID = id;

        path = Entity.AGPATH;
        this.setSprite("/resources/TestSprite.png");
        this.setX(path[0][0]);
        this.setY(path[0][1]);
        this.setMaxHealth((int)session.AGLevels[3] / 2 + 2);
        this.setHealth((int)session.AGLevels[3] / 2 + 2);
        this.setSpeed(1);
        this.setDamage(0);

        hitbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] , Entity.SPRITESIZE[1]);
        facingRight = true;
        onLadder = false;
        attackbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] + 30 , Entity.SPRITESIZE[1]);
        attacking = false;
        attackTime = System.currentTimeMillis(); // Prevents instantly healing upon spawn

        ground = 0;
        type = 3;
    }

    @Override
    public void move() {
        if (pathCounter < path.length && !attacked) {
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
                if (dy == 0) {
                    dy = 1;
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
                    if (dy == 0) {
                        dy = 1;
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
        } else {
            if (getX() < path[pathCounter][0]) {
                setX(path[pathCounter][0]);
            }
        }
        attackbox = new Rectangle(getX() - 50 , getY() - 50 , Entity.SPRITESIZE[0] + 100 , Entity.SPRITESIZE[1] + 100);
        hitbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] , Entity.SPRITESIZE[1]);
    }

    @Override
    public void attack() {
        // Although this unit does not attack, we need to check if it is BEING attacked
        for (Entity e : session.underGrounders) {
            if (e.attackbox.intersects(this.hitbox)) {
                attacked = true;
                break;
            }
            attacked = false;
        }
    }

    @Override
    public void special() {
        if (System.currentTimeMillis() - attackTime > attackDelay) {
            for (Entity e : session.aboveGrounders) {
                if (e.ent_ID != this.ent_ID) {
                    e.setHealth(e.getHealth() + (int)session.AGLevels[3] + (int)session.AGLevels[e.type]);
                }
            }
            attackTime = System.currentTimeMillis();
        }
    }
}