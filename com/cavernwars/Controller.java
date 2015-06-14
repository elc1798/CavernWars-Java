package com.cavernwars;

import java.util.ArrayList;

import javax.swing.JFrame;

import com.cavernwars.GUI.GfxRenderer;
import com.cavernwars.GUI.MouseInterpreter;
import com.cavernwars.entities.Entity;
import com.cavernwars.entities.aboveground.Engineer;
import com.cavernwars.entities.underground.towers.Trap;

@SuppressWarnings("serial")
public class Controller extends JFrame {

    private GfxRenderer graphicsSession;
    private MouseInterpreter mouseSession;

    public ArrayList<Entity> aboveGrounders = new ArrayList<Entity>();
    public ArrayList<Entity> underGrounders = new ArrayList<Entity>();
    public ArrayList<Trap> traps = new ArrayList<Trap>();

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
        aboveGrounders.add(new Engineer(this , 0));

        for (int i = 0; i < 5; i++) {
            AGLevels[i] = 1;
            UGLevels[i] = 1;
        }

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
}
