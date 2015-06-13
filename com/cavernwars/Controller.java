package com.cavernwars;

import java.util.ArrayList;

import javax.swing.JFrame;

import com.cavernwars.GUI.GfxRenderer;
import com.cavernwars.GUI.MouseInterpreter;
import com.cavernwars.entities.Entity;
import com.cavernwars.entities.aboveground.Engineer;

@SuppressWarnings("serial")
public class Controller extends JFrame {

    private GfxRenderer graphicsSession;
    private MouseInterpreter mouseSession;

    public ArrayList<Entity> aboveGrounders = new ArrayList<Entity>();
    public ArrayList<Entity> underGrounders = new ArrayList<Entity>();

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

    public Controller() {
        startGFX();
        startMouseInterpreter();
        aboveGrounders.add(new Engineer(this));

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
}
