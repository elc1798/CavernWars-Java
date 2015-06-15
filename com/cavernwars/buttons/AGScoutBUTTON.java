package com.cavernwars.buttons;

import com.cavernwars.Controller;
import com.cavernwars.entities.aboveground.Scout;

public class AGScoutBUTTON {

    private Controller session;

    public AGScoutBUTTON(Controller c) {
        session = c;
    }

    public void spawn() {
        Scout newbie = new Scout(session , session.AGUnitCounter);
        session.AGUnitCounter += 1;
        session.AGLevels[4] += 0.1;
        session.aboveGrounders.add(newbie);
        session.AIMoney -= newbie.cost;
    }
}
