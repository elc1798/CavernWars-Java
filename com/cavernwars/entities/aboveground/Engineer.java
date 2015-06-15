package com.cavernwars.entities.aboveground;

import java.awt.Rectangle;

import com.cavernwars.Controller;
import com.cavernwars.entities.Entity;
import com.cavernwars.entities.underground.towers.Trap;

/*
 * Engineers are "AboveGround" or "AG" units.
 * Cost: 300 + 50 * Level
 * Stats:
 * Health: 4 + Level
 * Speed: 1
 * Damage: 1 + Level
 *
 * Special:
 * Defuses traps:
 * Unit stops movement right before a trap and cannot attack.
 * The trap will be removed after 3 seconds.
 * If trap goes off via another unit triggering it, the engineer will continue moving.
 */
public class Engineer extends Entity {

    private int[][] path;

    // Unique variables to this type of unit
    private boolean defusing = false;
    private final long defuseTime = 3000; // 3 seconds
    private long defusingStart;
    private Trap defusedTrap;

    private int pathCounter = 1;

    public Engineer(Controller c , int id) {
        session = c;
        ent_ID = id;

        path = Entity.AGPATH;
        this.setSprite("/resources/AGEngineer.png");
        this.setX(path[0][0]);
        this.setY(path[0][1]);
        this.setMaxHealth((int)session.AGLevels[1] + 4);
        this.setHealth((int)session.AGLevels[1] + 4);
        this.setSpeed(1);
        this.setDamage((int)session.AGLevels[1] + 1);

        cost = 300 + 50 * (int)session.AGLevels[1];

        hitbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] , Entity.SPRITESIZE[1]);
        facingRight = true;
        onLadder = false;
        attackbox = new Rectangle(getX() , getY() , Entity.SPRITESIZE[0] + 30 , Entity.SPRITESIZE[1]);
        attackTime = 0;
        attacking = false;

        ground = 0;
        type = 1;
    }

    @Override
    public void move() {
        if (pathCounter < path.length && !defusing && !attacking) {
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
            if (!onLadder && !defusing) {
                for (Entity e : session.underGrounders) {
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
                    attacking = false;
                }
            }
        }
    }

    @Override
    public void special() {
        if (!defusing) {
            for (Trap t : session.traps) {
                if (this.attackbox.intersects(t.hitbox)) {
                    t.disabled = true;
                    this.setSprite("/resources/entities/ENGINEER-DEFUSING.png");
                    defusedTrap = t;
                    defusingStart = System.currentTimeMillis();
                    break;
                }
            }
        } else {
            if (System.currentTimeMillis() - defusingStart > defuseTime) {
                defusing = false;
                session.trapRemoval.add(defusedTrap);
                this.setSprite("/resources/entities/ENGINEER.png");
            }
        }
    }
}
