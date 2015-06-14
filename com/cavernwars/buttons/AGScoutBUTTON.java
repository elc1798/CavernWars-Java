package com.cavernwars.buttons;

import com.cavernwars.Controller;
import com.cavernwars.entities.aboveground.Scout;

public class AGScoutBUTTON {

    private Controller session;

    public AGScoutBUTTON(Controller c) {
        c = session;
    }

    public void spawn() {
        Scout newbie = new Scout(session , (int)(session.AGLevels[4] * 10));
        session.AGLevels[4] += 0.1;
        session.aboveGrounders.add(newbie);
        session.AIMoney -= newbie.cost;
    }
}
