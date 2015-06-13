package com.cavernwars.entities.aboveground;

import java.awt.Rectangle;

import com.cavernwars.Controller;
import com.cavernwars.entities.Entity;

/*
 * Knights are "AboveGround" or "AG" units.
 * Cost: 200 + 75 * Level
 * Stats:
 * Health: 3 + Level
 * Speed: 1
 * Damage: 3 + 1.5 * Level
 *
 * Special:
 * If a knight battles a unit for more than 2.5 seconds, it will
 * charge forward 100 pixels, dealing damage to every unit it passes through.
 */
public class Knight extends Entity {

    private Controller session;
    private int[][] path;

    private int pathCounter = 0;

    public Knight(Controller c) {
        session = c;
        /*
         * Each element in 'path' is formated like such:
         * {xcoor to start at , ycoor to start at , seconds to reach next destination}
         * -1 is used to denote an infinite amount of time.
         */
        path = Entity.AGPATH;
        this.setSprite("/resources/TestSprite.png");
        this.setX(path[0][0]);
        this.setY(path[0][1]);
        this.setMaxHealth((int)session.AGLevels[2] + 3);
        this.setHealth((int)session.AGLevels[2] + 3);
        this.setSpeed(1);
        this.setDamage((int)(session.AGLevels[2] * 1.5) + 3);

        hitbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] , Entity.SPRITESIZE[1]);
        facingRight = true;
        onLadder = false;
        attackbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] + 30 , Entity.SPRITESIZE[1]);
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
            attackbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] + 30 , Entity.SPRITESIZE[1]);
        } else {
            attackbox = new Rectangle(getX() - 30, getY() , Entity.SPRITESIZE[0] + 60 , Entity.SPRITESIZE[1]);
        }
    }
}
