package com.cavernwars;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;

import com.cavernwars.GUI.Descriptor;
import com.cavernwars.GUI.GfxRenderer;
import com.cavernwars.GUI.HealthBars;
import com.cavernwars.GUI.MouseInterpreter;
import com.cavernwars.buttons.UGBerserkerBUTTON;
import com.cavernwars.buttons.UGBrawlerBUTTON;
import com.cavernwars.buttons.UGChargerBUTTON;
import com.cavernwars.buttons.UGKingBUTTON;
import com.cavernwars.buttons.UGLavaPitBUTTON;
import com.cavernwars.buttons.UGMinerBUTTON;
import com.cavernwars.buttons.UGStalaciteDropBUTTON;
import com.cavernwars.entities.Entity;
import com.cavernwars.entities.underground.towers.Trap;

@SuppressWarnings("serial")
public class Controller extends JFrame {

    // Game Data
    private GfxRenderer graphicsSession;
    private MouseInterpreter mouseSession;
    private Descriptor descriptor;
    private HealthBars healthBars;
    private AI ai;

    public int AIMoney;
    public int playerMoney;
    public int AIHealth;
    public int playerHealth;

    public ArrayList<Entity> aboveGrounders = new ArrayList<Entity>();
    public ArrayList<Entity> underGrounders = new ArrayList<Entity>();
    public ArrayList<Trap> traps = new ArrayList<Trap>();
    public LinkedList<Entity> killQueue = new LinkedList<Entity>(); // For entities that don't die immediately
    public LinkedList<Trap> trapRemoval = new LinkedList<Trap>(); // Queue for trap removal

    // Player buttons:
    public UGKingBUTTON b0;
    public UGBerserkerBUTTON b1;
    public UGBrawlerBUTTON b2;
    public UGChargerBUTTON b3;
    public UGMinerBUTTON b4;
    public UGStalaciteDropBUTTON b5;
    public UGLavaPitBUTTON b6;

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
    public int AGUnitCounter = 0;
    public int UGUnitCounter = 0;
    public int trapIDCounter = 0;

    // Booleans to keep track of King buffs
    public boolean AGKingSpawned = false;
    public boolean UGKingSpawned = false;

    public Controller() {
        startGFX();
        startMouseInterpreter();
        descriptor = new Descriptor(this);

        for (int i = 0; i < 5; i++) {
            AGLevels[i] = 1;
            UGLevels[i] = 1;
        }

        AIMoney = 800;
        playerMoney = 800;
        AIHealth = 200;
        playerHealth = 200;

        b0 = new UGKingBUTTON(this);
        b1 = new UGBerserkerBUTTON(this);
        b2 = new UGBrawlerBUTTON(this);
        b3 = new UGChargerBUTTON(this);
        b4 = new UGMinerBUTTON(this);
        b5 = new UGStalaciteDropBUTTON(this);
        b6 = new UGLavaPitBUTTON(this);
        healthBars = new HealthBars(this);

        ai = new AI(this);
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

    public Descriptor getCurrentDescriptor() {
        return descriptor;
    }

    public HealthBars getHealthBars() {
        return healthBars;
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

    public void mouseActionCheck() {
        descriptor.updateDescription(mouseSession.updateMouseLocation());
    }

    public void update() {
        // Check for dead base
        if (AIHealth <= 0) {
            graphicsSession.endScreen(1);
        } else if (playerHealth <= 0) {
            graphicsSession.endScreen(0);
        }

        // Run AI decision
        ai.makeDecision();

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

        // Remove used traps
        while(!trapRemoval.isEmpty()) {
            int id = trapRemoval.peek().trap_ID;
            trapRemoval.pop();
            removeTrap(id);
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

        // Check mouse location and apply appropriate action
        mouseActionCheck();
    }
}
