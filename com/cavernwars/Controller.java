package com.cavernwars;

import javax.swing.JFrame;

import com.cavernwars.GUI.GfxRenderer;
import com.cavernwars.GUI.MouseInterpreter;

@SuppressWarnings("serial")
public class Controller extends JFrame {

    private GfxRenderer graphicsSession;
    private MouseInterpreter mouseSession;

    public Controller() {
        startGFX();
        startMouseInterpreter();
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
