package com.cavernwars;

import com.cavernwars.buttons.AGEngineerBUTTON;
import com.cavernwars.buttons.AGKingBUTTON;
import com.cavernwars.buttons.AGKnightBUTTON;
import com.cavernwars.buttons.AGPriestBUTTON;
import com.cavernwars.buttons.AGScoutBUTTON;

public class AI {

    private Controller session;
    private long decisionTimer;
    private final long decisionLength = 3000;

    AGKingBUTTON AIb0;
    AGEngineerBUTTON AIb1;
    AGKnightBUTTON AIb2;
    AGPriestBUTTON AIb3;
    AGScoutBUTTON AIb4;

    public AI(Controller c) {
        session = c;
        AIb0 = new AGKingBUTTON(c);
        AIb1 = new AGEngineerBUTTON(c);
        AIb2 = new AGKnightBUTTON(c);
        AIb3 = new AGPriestBUTTON(c);
        AIb4 = new AGScoutBUTTON(c);
        decisionTimer = 0;
    }

    public void makeDecision() {
        //Simple, [Disregard for money] AI. Pretty random... limit to 20 units
        if (System.currentTimeMillis() - decisionTimer > decisionLength && session.aboveGrounders.size() <= 20) {
            boolean sufficientForKing = true;
            for (int i = 1; i < 5; i++) {
                sufficientForKing = sufficientForKing && (session.AGLevels[i] > 5.0);
            }
            if (!session.AGKingSpawned && sufficientForKing) {
                AIb0.spawn();
                session.AGKingSpawned = false;
            } else {
                int choice = (int)(Math.random() * 1000) % 4;
                try {
                switch (choice) {
                    case 0:
                        AIb1.spawn();
                        break;
                    case 1:
                        AIb2.spawn();
                        break;
                    case 2:
                        AIb3.spawn();
                        break;
                    case 3:
                        AIb4.spawn();
                        break;
                    default:
                        // Do nothing
                }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            decisionTimer = System.currentTimeMillis();
        }
    }
}
