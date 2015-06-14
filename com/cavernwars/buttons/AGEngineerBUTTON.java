package com.cavernwars.buttons;

import com.cavernwars.Controller;
import com.cavernwars.entities.aboveground.Engineer;

public class AGEngineerBUTTON {

    private Controller session;

    public AGEngineerBUTTON(Controller c) {
        session = c;
    }

    public void spawn() {
        Engineer newbie = new Engineer(session , (int)(session.AGLevels[1] * 10));
        session.AGLevels[1] += 0.1;
        session.aboveGrounders.add(newbie);
        session.AIMoney -= newbie.cost;
    }
}
