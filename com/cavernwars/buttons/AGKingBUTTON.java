package com.cavernwars.buttons;

import com.cavernwars.Controller;
import com.cavernwars.entities.aboveground.AGKing;

public class AGKingBUTTON {

    private Controller session;

    public AGKingBUTTON(Controller c) {
        session = c;
    }

    public void spawn() {
        AGKing newbie = new AGKing(session , session.AGUnitCounter);
        session.AGUnitCounter += 1;
        session.AGLevels[0] += 0.1;
        session.aboveGrounders.add(newbie);
        session.AIMoney -= newbie.cost;
        session.AGKingSpawned = true;
    }
}
