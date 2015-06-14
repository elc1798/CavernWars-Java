package com.cavernwars.entities.underground;

import java.awt.Rectangle;

import com.cavernwars.Controller;
import com.cavernwars.entities.Entity;

/*
 * Berserkers are "UnderGround" or "UG" units.
 * Cost: 150 + 60 * Level
 * Stats:
 * Health: 1 + Level
 * Speed: 2
 * Damage: 5 + Level
 *
 * Special:
 * If this unit's health is reduced to 0, it will gain triple damage,
 * max speed (5), and double attack speed, and will be invincible for
 * 3 seconds.
 */
public class Berserker extends Entity {

    private int[][] path;

    private int pathCounter = 1;

    private boolean berserk = false;
    private long berserkStart;
    private final long berserkLength = 3000;
    private int attackRateDivisor = 1;

    public Berserker(Controller c , int id) {
        session = c;
        ent_ID = id;

        /*
         * Each element in 'path' is formated like such:
         * {xcoor to start at , ycoor to start at , seconds to reach next destination}
         * -1 is used to denote an infinite amount of time.
         */
        path = Entity.UGPATH;
        this.setSprite("/resources/TestSprite.png");
        this.setX(path[0][0]);
        this.setY(path[0][1]);
        this.setMaxHealth((int)session.UGLevels[1] + 1);
        this.setHealth((int)session.UGLevels[1] + 1);
        this.setSpeed(2);
        this.setDamage((int)session.UGLevels[1] + 5);

        hitbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] , Entity.SPRITESIZE[1]);
        facingRight = true;
        onLadder = false;
        attackbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] + 30 , Entity.SPRITESIZE[1]);
        attackTime = 0;
        attacking = false;

        ground = 1;
        type = 1;
    }

    @Override
    public void setHealth(int _health) {
        if (_health <= 0) {
            setDamage(getDamage() * 3);
            setSpeed(5);
            super.setHealth(Integer.MAX_VALUE);
            attackRateDivisor = 2;
            berserk = true;
            berserkStart = System.currentTimeMillis();
        } else {
            super.setHealth(_health);
        }
    }

    @Override
    public void move() {
        if (pathCounter < path.length) {
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
        // Can only hit one unit at a time!
        if (System.currentTimeMillis() - attackTime > attackDelay / attackRateDivisor) {
            if (!onLadder) {
                for (Entity e : session.aboveGrounders) {
                    if (this.attackbox.intersects(e.hitbox)) {
                        if (session.AGKingSpawned) {
                            e.setHealth(e.getHealth() - this.getDamage() * 2);
                        } else {
                            e.setHealth(e.getHealth() - this.getDamage());
                        }
                        attacking = true;
                        attackTime = System.currentTimeMillis();
                        break;
                    }
                }
                attacking = false;

            }
        }
    }

    @Override
    public void special() {
        if (berserk) {
            if (System.currentTimeMillis() - berserkStart > berserkLength) {
                session.killQueue.add(this);
            } else {
                super.setHealth(Integer.MAX_VALUE);
            }
        }
    }
}