package com.cavernwars.buttons;

import com.cavernwars.Controller;
import com.cavernwars.entities.aboveground.Knight;

public class AGKnightBUTTON {

    private Controller session;

    public AGKnightBUTTON(Controller c) {
        c = session;
    }

    public void spawn() {
        Knight newbie = new Knight(session , (int)(session.AGLevels[2] * 10));
        session.AGLevels[2] += 0.1;
        session.aboveGrounders.add(newbie);
        session.AIMoney -= newbie.cost;
    }
}
