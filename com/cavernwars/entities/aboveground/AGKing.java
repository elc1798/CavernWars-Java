package com.cavernwars.entities.aboveground;

import java.awt.Rectangle;

import com.cavernwars.Controller;
import com.cavernwars.entities.Entity;

/*
 * AGKings are "AboveGround" or "AG" units.
 * Cost: 1000 + 100 * Level
 * Stats:
 * Health: 8 + Level
 * Speed: 1
 * Damage: 5 + Level
 *
 * Special:
 * Can only be unlocked after all units are over level 5
 * All allied units that are currently alive will be healed.
 * While this unit is alive, all other allied units will have
 * doubled health and damage.
 * There can only be one AGKing alive at a time.
 */
public class AGKing extends Entity {

    private Controller session;
    private int[][] path;

    private int pathCounter = 1;

    public AGKing(Controller c , int id) {
        session = c;
        ent_ID = id;

        /*
         * Each element in 'path' is formated like such:
         * {xcoor to start at , ycoor to start at , seconds to reach next destination}
         * -1 is used to denote an infinite amount of time.
         */
        path = Entity.AGPATH;
        setSprite("/resources/TestSprite.png");
        setX(path[0][0]);
        setY(path[0][1]);
        setMaxHealth((int)session.AGLevels[0] + 8);
        setHealth((int)session.AGLevels[0] + 8);
        setSpeed(1);
        setDamage((int)session.AGLevels[0] + 5);

        hitbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] , Entity.SPRITESIZE[1]);
        facingRight = true;
        onLadder = false;
        attackbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] + 40 , Entity.SPRITESIZE[1]);
        attackTime = 0;
        attacking = false;

        // Apply special effect!
        for (Entity e : session.aboveGrounders) {
            e.setHealth(e.getMaxHealth());
        }
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
            attackbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] + 40 , Entity.SPRITESIZE[1]);
        } else {
            if (getX() < path[pathCounter][0]) {
                setX(path[pathCounter][0]);
            }
            attackbox = new Rectangle(getX() - 40, getY() , Entity.SPRITESIZE[0] + 80 , Entity.SPRITESIZE[1]);
        }
    }

    @Override
    public void attack() {
        // Can only hit one unit at a time!
        if (System.currentTimeMillis() - attackTime > attackDelay) {
            if (!onLadder) {
                for (Entity e : session.underGrounders) {
                    if (this.attackbox.intersects(e.hitbox)) {
                        e.setHealth(e.getHealth() - this.getDamage());
                        attacking = true;
                        attackTime = System.currentTimeMillis();
                        break;
                    }
                }
            }
            attacking = false;
        }
    }
}