package com.cavernwars;

import java.awt.EventQueue;

public class CavernWars {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Controller GAME = new Controller();
                GAME.setVisible(true);
            }
        });
    }
}
