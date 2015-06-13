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

    public Controller() {
        startGFX();
        startMouseInterpreter();
        aboveGrounders.add(new Engineer(this));
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
