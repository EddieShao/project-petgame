package com.example.petgame.Training;

import java.util.Random;

public class Platform {
    private int x, y;
    private Random random;
    private int type;

    public Platform(int x, int y) {
        this.x = x;
        this.y = y;
        randomType();

    }

    public void randomType(){
        double r = Math.random();
        if (r <0.33){
            type =  0;
        }
        else if (r< 0.66){
            type =  1;
        }
        else{
            type = 2;
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

