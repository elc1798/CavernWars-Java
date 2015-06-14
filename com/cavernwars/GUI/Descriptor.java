package com.cavernwars.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.cavernwars.Controller;

public class Descriptor {

    private Controller session;
    private String description;
    private int[] dimensions;

    public Descriptor(Controller c) {
        session = c;
        dimensions = new int[]{540 , 430 , 260 , 110};
        description = "";
    }

    public void updateDescription(int buttonID) {
        switch(buttonID) {
            case 0:
                description = "The King of the Underground\n";
                description += "Cost: " + (1000 + 100 * (int)session.UGLevels[0]) + " | ";
                description += "Health: " + ((int)session.UGLevels[0] + 8) + " | ";
                description += "Speed: 1 | ";
                description += "Damage: " + ((int)session.UGLevels[0] + 5) + "\n";
                description += "Special: Can only be spawned after all units are over\nlevel 5. All allied units that are currently alive will be\nhealed. While this unit is alive, all other allied units\nwill have doubled damage. There can only be one\nUGKing alive at a time.";
                break;
            case 1:
                description = "Dwarven Berserker\n";
                description += "Cost: " + (150 + 60 * (int)session.UGLevels[1]) + " | ";
                description += "Health: " + ((int)session.UGLevels[1] + 1) + " | ";
                description += "Speed: 2 | ";
                description += "Damage: " + ((int)session.UGLevels[1] + 5) + "\n";
                description += "Special: If this unit's health is reduced to 0, it will gain\n";
                description += "triple damage, max speed (5), and double attack speed,\n";
                description += "and will be invincible for 3 seconds.";
                break;
            case 2:
                description = "Underground Brawler\n";
                description += "Cost: " + (100 + 50 * (int)session.UGLevels[2]) + " | ";
                description += "Health: " + ((int)session.UGLevels[2] + 4) + " | ";
                description += "Speed: 1 | ";
                description += "Damage: " + ((int)session.UGLevels[2] + 2) + "\n";
                description += "Special: If this unit kills another unit, it gains 1 damage\n";
                description += "and regenerates 2 health.";
                break;
            case 3:
                description = "Battle Charger\n";
                description += "Cost: " + (75 + 25 * (int)session.UGLevels[3]) + " | ";
                description += "Health: " + ((int)session.UGLevels[3] + 3) + " | ";
                description += "Speed: 2 | ";
                description += "Damage: " + ((int)session.UGLevels[3] * 2 + 1) + "\n";
                description += "Special: When this unit arrives on a new platform, it\n";
                description += "charges forward at max speed until it hits a unit. When\n";
                description += "it collides, it deals additional damage based on how\n";
                description += "long it was charging.";
                break;
            case 4:
                description = "Dwarven Miner\n";
                description += "Cost: " + (120 + 40 * (int)session.UGLevels[4]) + " | ";
                description += "Health: " + ((int)session.UGLevels[4] + 5) + " | ";
                description += "Speed: 1 | ";
                description += "Damage: " + ((int)session.UGLevels[4] + 1) + "\n";
                description += "Special: The Miner's large pickaxe hits all units in its\n";
                description += "attack zone at the same time.";
                break;
            case 5:
                description = "Stalacite Drop\n";
                description += "Cost: 1000\n";
                description += "Stalacites will fall on the first unit that walks under\n";
                description += "this trap.";
                break;
            case 6:
                description = "Lava Pit\n";
                description += "Cost: 3000\n";
                description += "All units that walk into a lava pit will die.";
                break;
            case -1:
                description = "";
                break;
            default:
                description = "";
                break;
        }
    }

    public void paintComponent(Graphics g) {
        Font f = new Font("Helvetica", Font.PLAIN, 10);
        g.setColor(Color.white);
        g.setFont(f);
        String[] descriptionSplit = description.split("\n");
        for (int i = 0; i < descriptionSplit.length; i++) {
            g.drawString(descriptionSplit[i] , dimensions[0] , dimensions[1] + 13 * i);
        }
    }
}
