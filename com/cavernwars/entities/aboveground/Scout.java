package com.cavernwars.entities.aboveground;

import java.awt.Rectangle;

import com.cavernwars.Controller;
import com.cavernwars.entities.Entity;

/*
 * Scouts are "AboveGround" or "AG" units.
 * Cost: 150 + 60 * Level
 * Stats:
 * Health: 2 + Level
 * Speed: 2
 * Damage: 2 + Level
 *
 * Special:
 * This unit is ranged, depends on it's level.
 */
public class Scout extends Entity {

    private Controller session;
    private int[][] path;

    private int pathCounter = 1;

    public Scout(Controller c , int id) {
        session = c;
        ent_ID = id;

        /*
         * Each element in 'path' is formated like such:
         * {xcoor to start at , ycoor to start at , seconds to reach next destination}
         * -1 is used to denote an infinite amount of time.
         */
        path = Entity.AGPATH;
        this.setSprite("/resources/TestSprite.png");
        this.setX(path[0][0]);
        this.setY(path[0][1]);
        this.setMaxHealth((int)session.AGLevels[4] + 2);
        this.setHealth((int)session.AGLevels[4] + 2);
        this.setSpeed(2);
        this.setDamage((int)session.AGLevels[4] + 2);

        hitbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] , Entity.SPRITESIZE[1]);
        facingRight = true;
        onLadder = false;
        attackbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] + 150 , Entity.SPRITESIZE[1]);
    }

    @Override
    public void move() {
        if (pathCounter + 1 < path.length) {
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
                    dy = getSpeed();
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
            attackbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] + 150 , Entity.SPRITESIZE[1]);
        } else {
            attackbox = new Rectangle(getX() - 150, getY() , Entity.SPRITESIZE[0] + 300 , Entity.SPRITESIZE[1]);
        }
    }

    @Override
    public void attack() {
        // Can only hit one unit at a time!
        if (!onLadder) {
            for (Entity e : session.underGrounders) {
                if (this.attackbox.intersects(e.hitbox)) {
                    if (session.AGKingSpawned) {
                        e.setHealth(e.getHealth() - this.getDamage() * 2);
                    } else {
                        e.setHealth(e.getHealth() - this.getDamage());
                    }
                    break;
                }
            }
        }
    }
}