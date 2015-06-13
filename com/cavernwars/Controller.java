package com.cavernwars;

import javax.swing.JFrame;

import com.cavernwars.GUI.GfxRenderer;

@SuppressWarnings("serial")
public class Controller extends JFrame {

    public Controller() {
        startGFX();
    }

    private void startGFX() {
        add(new GfxRenderer());
        setSize(800 , 600);
        setTitle("CavernWars");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
}
