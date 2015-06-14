package com.cavernwars.GUI;

import java.awt.MouseInfo;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.cavernwars.Controller;

public class MouseInterpreter implements MouseListener {

    private Controller session;
    private Rectangle cursor;
    private int buttonHoverID;

    public MouseInterpreter(Controller session) {
        this.session = session;
        cursor = new Rectangle(mouseX() , mouseY() , 2 , 2);
        buttonHoverID = -1;
    }

    public int mouseX() {
        return MouseInfo.getPointerInfo().getLocation().x;
    }

    public int mouseY() {
        return MouseInfo.getPointerInfo().getLocation().y;
    }

    public int updateMouseLocation() {
        cursor = new Rectangle(mouseX() , mouseY() , 2 , 2);
        if (cursor.intersects(session.b0.box)) {
            buttonHoverID = 0;
        } else if (cursor.intersects(session.b1.box)) {
            buttonHoverID = 1;
        } else if (cursor.intersects(session.b2.box)) {
            buttonHoverID = 2;
        } else if (cursor.intersects(session.b3.box)) {
            buttonHoverID = 3;
        } else if (cursor.intersects(session.b4.box)) {
            buttonHoverID = 4;
        } else if (cursor.intersects(session.b5.box)) {
            buttonHoverID = 5;
        } else if (cursor.intersects(session.b6.box)) {
            buttonHoverID = 6;
        } else {
            buttonHoverID = -1;
        }
        return buttonHoverID;
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
