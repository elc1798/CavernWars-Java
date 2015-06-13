package com.cavernwars.GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.cavernwars.Controller;

public class MouseInterpreter implements MouseListener {

    Controller session;

    public MouseInterpreter(Controller session) {
        this.session = session;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.printf("%3d %3d\n" , e.getX() , e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
