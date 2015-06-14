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
 * If a knight battles a unit for more than 3 seconds, it will
 * charge forward 100 pixels, dealing damage to every unit it passes through.
 */
public class Knight extends Entity {

    private int[][] path;

    private int pathCounter = 1;

    // Variables unique to this unit
    private int previousAttacks = 0;

    public Knight(Controller c , int id) {
        session = c;
        ent_ID = id;

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
        attacking = false;
        attackTime = System.currentTimeMillis();

        ground = 0;
        type = 2;
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
                int tmp = previousAttacks;
                for (Entity e : session.underGrounders) {
                    if (this.attackbox.intersects(e.hitbox)) {
                        if (session.AGKingSpawned) {
                            e.setHealth(e.getHealth() - this.getDamage() * 2);
                        } else {
                            e.setHealth(e.getHealth() - this.getDamage());
                        }
                        if (attacking) {
                            previousAttacks++;
                        }
                        attacking = true;
                        attackTime = System.currentTimeMillis();
                        previousAttacks = tmp;
                        break;
                    }
                    attacking = false;
                    previousAttacks = 0;
                }
            }
        }
    }

    @Override
    public void special() {
        if (onLadder) {
            return;
        }
        if (previousAttacks >= 3) {
            previousAttacks = 0; // Reset
            attacking = false; // Reset
            for (int i = 0; i < 10; i++) {
                for (Entity e : session.underGrounders) {
                    if (this.hitbox.intersects(e.hitbox)) {
                        if (session.AGKingSpawned) {
                            e.setHealth(e.getHealth() - this.getDamage() * 2);
                        } else {
                            e.setHealth(e.getHealth() - this.getDamage());
                        }
                        break;
                    }
                }
                if (facingRight) {
                    setX(getX() + 10);
                    if (getX() > path[pathCounter][0]) {
                        setX(path[pathCounter][0]);
                        return;
                    }
                } else {
                    setX(getX() - 10);
                    if (getX() < path[pathCounter][0]) {
                        setX(path[pathCounter][0]);
                        return;
                    }
                }
            }
        }
    }
}
