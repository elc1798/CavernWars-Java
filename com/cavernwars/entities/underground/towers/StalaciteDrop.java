package com.cavernwars.entities.underground.towers;

import java.awt.Rectangle;

import com.cavernwars.Controller;
import com.cavernwars.entities.Entity;

public class StalaciteDrop extends Trap {

    public StalaciteDrop(Controller c , int id , int x , int y) {
        session = c;
        trap_ID = id;
        setX(x);
        setY(y);
        cost = 1000;
        hitbox = new Rectangle(x , y , SPRITESIZE[0] , SPRITESIZE[1]);
        attackbox = new Rectangle(x - 10, y - 10, SPRITESIZE[0] + 20 , SPRITESIZE[1] + 10);
        setSprite("/resources/traps/StalaciteDrop.png");
    }

    @Override
    public void attack() {
        for (Entity e : session.aboveGrounders) {
            if (this.attackbox.intersects(e.hitbox)) {
                session.killQueue.add(e);
                session.trapRemoval.add(this);
                break;
            }
        }
    }
}
