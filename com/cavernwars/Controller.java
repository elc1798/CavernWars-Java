package com.cavernwars;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;

import com.cavernwars.GUI.GfxRenderer;
import com.cavernwars.GUI.MouseInterpreter;
import com.cavernwars.entities.Entity;
import com.cavernwars.entities.aboveground.AGKing;
import com.cavernwars.entities.aboveground.Engineer;
import com.cavernwars.entities.aboveground.Knight;
import com.cavernwars.entities.aboveground.Priest;
import com.cavernwars.entities.aboveground.Scout;
import com.cavernwars.entities.underground.Berserker;
import com.cavernwars.entities.underground.Brawler;
import com.cavernwars.entities.underground.Charger;
import com.cavernwars.entities.underground.UGKing;
import com.cavernwars.entities.underground.towers.Trap;

@SuppressWarnings("serial")
public class Controller extends JFrame {

    // Game Data
    private GfxRenderer graphicsSession;
    private MouseInterpreter mouseSession;

    public int AIMoney;
    public int playerMoney;

    public ArrayList<Entity> aboveGrounders = new ArrayList<Entity>();
    public ArrayList<Entity> underGrounders = new ArrayList<Entity>();
    public ArrayList<Trap> traps = new ArrayList<Trap>();
    public LinkedList<Entity> killQueue = new LinkedList<Entity>(); // For entities that don't die immediately

    /*
     * AGLevels Contents:
     * Index | Data
     * 0 | (AG)King
     * 1 | Engineer
     * 2 | Knight
     * 3 | Priest
     * 4 | Scout
     */
    public float[] AGLevels = new float[5];
    /*
     * UGLevels Contents:
     * Index | Data
     * 0 | (UG)King
     * 1 | Berserker
     * 2 | Brawler
     * 3 | Charger
     * 4 | Miner
     */
    public float[] UGLevels = new float[5];

    // Booleans to keep track of King buffs
    public boolean AGKingSpawned = false;
    public boolean UGKingSpawned = false;

    public Controller() {
        startGFX();
        startMouseInterpreter();

        for (int i = 0; i < 5; i++) {
            AGLevels[i] = 1;
            UGLevels[i] = 1;
        }

        AIMoney = 800;
        playerMoney = 800;

        // Testing:
        aboveGrounders.add(new Engineer(this , 0));
        aboveGrounders.add(new Knight(this , 1));
        aboveGrounders.add(new Priest(this , 2));
        aboveGrounders.add(new Scout(this , 3));
        underGrounders.add(new Berserker(this , 0));
        underGrounders.add(new Brawler(this , 1));
        underGrounders.add(new Charger(this , 2));

        aboveGrounders.add(new AGKing(this , 99));
        underGrounders.add(new UGKing(this , 99));
        AGKingSpawned = true;
        UGKingSpawned = true;
    }

    private void startGFX() {
        graphicsSession = new GfxRenderer(this);
        add(graphicsSession);
        setSize(800 , 600);
        setTitle("CavernWars");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void startMouseInterpreter() {
        mouseSession = new MouseInterpreter(this);
        addMouseListener(mouseSession);
    }

    public void removeAGUnit(int id) {
        for (int i = 0; i < aboveGrounders.size(); i++) {
            if (aboveGrounders.get(i).ent_ID == id) {
                aboveGrounders.remove(i);
                break;
            }
        }
    }

    public void removeUGUnit(int id) {
        for (int i = 0; i < underGrounders.size(); i++) {
            if (underGrounders.get(i).ent_ID == id) {
                underGrounders.remove(i);
                break;
            }
        }
    }

    public void removeTrap(int id) {
        for (int i = 0; i < traps.size(); i++) {
            if (traps.get(i).trap_ID == id) {
                traps.remove(i);
                break;
            }
        }
    }

    public void update() {
        // Update killQueue!
        while (!killQueue.isEmpty()) {
            int ground = killQueue.peek().ground;
            int id = killQueue.peek().ent_ID;
            killQueue.pop();
            if (ground == 0) {
                removeAGUnit(id);
            } else {
                removeUGUnit(id);
            }
        }

        /*
         *  This would usually be in Entity.java or one of its subclasses,
         *  but putting it here reduces runtime and achieves the same result.
         */
        if (underGrounders.size() == 0) {
            for (Entity e : aboveGrounders) {
                e.attacking = false;
            }
        }
        if (aboveGrounders.size() == 0) {
            for (Entity e : underGrounders) {
                e.attacking = false;
            }
        }
    }
}
