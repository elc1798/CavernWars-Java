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

    private int pathCounter = 0;

    public AGKing(Controller c) {
        session = c;
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

        // Apply special effect!
        for (Entity e : session.aboveGrounders) {
            e.setHealth(e.getMaxHealth());
        }
    }

    @Override
    public void move() {
        if (pathCounter + 1 < path.length && Math.abs((double) getX() - path[pathCounter +1][0]) < 5.0 && Math.abs((double) getX() - path[pathCounter +1][0]) < 5.0) {
            setX(path[pathCounter + 1][0]);
            setY(path[pathCounter + 1][1]);
        }
        // Out of bounds?? This is a hacky fix :D
        if (getX() < 0) {
            setX(5);
        }
        if (getX() > 515) {
            setX(500);
        }
        if (pathCounter + 1 < path.length && getX() == path[pathCounter + 1][0] && getY() == path[pathCounter + 1][1]) {
            pathCounter++;
        }
        if (path[pathCounter][2] != -1) {
            if (path[pathCounter][2] == 0) {
                setX(path[pathCounter + 1][0]);
                setY(path[pathCounter + 1][1]);
            } else {
                int dx , dy;
                dx = (path[pathCounter + 1][0] - path[pathCounter][0]) / path[pathCounter][2] * getSpeed() / 10;
                dy = (path[pathCounter + 1][1] - path[pathCounter][1]) / path[pathCounter][2] * getSpeed() / 10;
                facingRight = dx > 0;
                onLadder = dx == 0;
                setX(getX() + dx);
                setY(getY() + dy);
            }
        }
        if (facingRight) {
            attackbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] + 40 , Entity.SPRITESIZE[1]);
        } else {
            attackbox = new Rectangle(getX() - 40, getY() , Entity.SPRITESIZE[0] + 80 , Entity.SPRITESIZE[1]);
        }
    }

    @Override
    public void attack() {
        // Can only hit one unit at a time!
        if (!onLadder) {
            for (Entity e : session.underGrounders) {
                if (this.attackbox.intersects(e.hitbox)) {
                    e.setHealth(e.getHealth() - this.getDamage());
                    break;
                }
            }
        }
    }
}