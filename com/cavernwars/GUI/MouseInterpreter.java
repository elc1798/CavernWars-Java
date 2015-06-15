package com.cavernwars.GUI;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.cavernwars.Controller;

public class MouseInterpreter implements MouseListener , MouseMotionListener {

    private Controller session;
    private Rectangle cursor;
    private Rectangle trapzone;
    private int buttonHoverID;
    private boolean placing;
    private int placingID;
    private int x;
    private int y;

    public MouseInterpreter(Controller session) {
        this.session = session;
        cursor = new Rectangle(mouseX() , mouseY() , 2 , 2);
        buttonHoverID = -1;
        placing = false;
        placingID = -1;
        trapzone = new Rectangle(5 , 540 , 455 , 55);
    }

    public int mouseX() {
        return x;
    }

    public int mouseY() {
        return y;
    }

    public int updateMouseLocation() {
        cursor = new Rectangle(mouseX() , mouseY() , 1 , 1);
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
        if (!placing) {
            switch(updateMouseLocation()) {
            case 0:
                if (session.UGLevels[1] > 5.0 && session.UGLevels[2] > 5.0 && session.UGLevels[3] > 5.0 && session.UGLevels[4] > 5.0) {
                    if (session.playerMoney > 1000 + 100 * (int)session.UGLevels[0]) {
                        session.b0.spawn();
                    }
                }
                break;
            case 1:
                if (session.playerMoney > 150 + 60 * (int)session.UGLevels[1]) {
                    session.b1.spawn();
                }
                break;
            case 2:
                if (session.playerMoney > 100 + 50 * (int)session.UGLevels[2]) {
                    session.b2.spawn();
                }
                break;
            case 3:
                if (session.playerMoney > 75 + 25 * (int)session.UGLevels[3]) {
                    session.b3.spawn();
                }
                break;
            case 4:
                if (session.playerMoney > 120 + 40 * (int)session.UGLevels[4]) {
                    session.b4.spawn();
                }
                break;
            case 5:
                if (session.playerMoney > 1000) {
                    placingID = 5;
                    placing = true;
                }
                break;
            case 6:
                if (session.playerMoney > 3000) {
                    placingID = 6;
                    placing = true;
                }
                break;
            case -1:
                // Do nothing
                break;
            default:
                // Do nothing
                break;
            }
        } else {
            placing = false;
            cursor = new Rectangle(mouseX() , mouseY() , 2 , 2);
            if (cursor.intersects(trapzone)) {
                if (placingID == 5) {
                    session.b5.spawn(mouseX() , 550);
                } else if (placingID == 6) {
                    session.b6.spawn(mouseX() , 550);
                }
            }
        }
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

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

}
