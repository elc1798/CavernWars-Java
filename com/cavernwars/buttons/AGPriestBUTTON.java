package com.cavernwars.buttons;

import com.cavernwars.Controller;
import com.cavernwars.entities.aboveground.Priest;

public class AGPriestBUTTON {

    private Controller session;

    public AGPriestBUTTON(Controller c) {
        session = c;
    }

    public void spawn() {
        Priest newbie = new Priest(session , (int)(session.AGLevels[3] * 10));
        session.AGLevels[3] += 0.1;
        session.aboveGrounders.add(newbie);
        session.AIMoney -= newbie.cost;
    }
}
