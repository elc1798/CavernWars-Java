package com.cavernwars.buttons;

import com.cavernwars.Controller;
import com.cavernwars.entities.aboveground.AGKing;

public class AGKingBUTTON {

    private Controller session;

    public AGKingBUTTON(Controller c) {
        c = session;
    }

    public void spawn() {
        AGKing newbie = new AGKing(session , (int)(session.AGLevels[0] * 10));
        session.AGLevels[0] += 0.1;
        session.aboveGrounders.add(newbie);
        session.AIMoney -= newbie.cost;
        session.AGKingSpawned = true;
    }
}
