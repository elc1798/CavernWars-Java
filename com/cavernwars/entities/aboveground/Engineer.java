package com.cavernwars.entities.aboveground;

import java.awt.image.BufferedImage;

import com.cavernwars.Controller;
import com.cavernwars.entities.Entity;

public class Engineer extends Entity {

    private int x;
    private int y;
    private int health;
    private int speed;
    private BufferedImage sprite;
    private Controller session;
    private int[][] path;

    private int pathCounter = 0;

    public Engineer(Controller c) {
        session = c;
        /*
         * Each element in 'path' is formated like such:
         * {xcoor to start at , ycoor to start at , seconds to reach next destination}
         * -1 is used to denote an infinite amount of time.
         */
        path = new int[][]{
                {100 , 80 , 10},
                {500 , 80 , 0},
                {510 , 120 , 1},
                {450 , 160 , 8},
                {5 , 160 , 6},
                {5 , 235 , 10},
                {500 , 235 , 6},
                {500 , 310 , 10},
                {5 , 310 , 6},
                {5 , 400 , 10},
                {500 , 400 , 6},
                {500 , 425 , 10},
                {5 , 425 , 8},
                {5 , 550 , 10},
                {430 , 550 , -1}
        };
        this.setSprite("/resources/TestSprite.png");
        this.setX(path[0][0]);
        this.setY(path[0][1]);
        this.setHealth(1);
        this.setSpeed(1);
    }

    @Override
    public void move() {
        if (pathCounter + 1 < path.length && Math.abs((double) getX() - path[pathCounter +1][0]) < 5.0 && Math.abs((double) getX() - path[pathCounter +1][0]) < 5.0) {
            setX(path[pathCounter + 1][0]);
            setY(path[pathCounter + 1][1]);
        }
        // Out of bounds?? This is a hacky fix :D
        if (getX() < 0) {
            setX(5);
        }
        if (getX() > 515) {
            setX(500);
        }
        if (pathCounter + 1 < path.length && getX() == path[pathCounter + 1][0] && getY() == path[pathCounter + 1][1]) {
            pathCounter++;
            System.out.printf("%3d , %3d , %3d , %3d\n" , path[pathCounter][0] , path[pathCounter][1] , getX() , getY());
        }
        if (path[pathCounter][2] != -1) {
            if (path[pathCounter][2] == 0) {
                setX(path[pathCounter + 1][0]);
                setY(path[pathCounter + 1][1]);
            } else {
                int dx , dy;
                dx = (path[pathCounter + 1][0] - path[pathCounter][0]) / path[pathCounter][2] * getSpeed() / 10;
                dy = (path[pathCounter + 1][1] - path[pathCounter][1]) / path[pathCounter][2] * getSpeed() / 10;
                setX(getX() + dx);
                setY(getY() + dy);
            }
        }
    }
}
