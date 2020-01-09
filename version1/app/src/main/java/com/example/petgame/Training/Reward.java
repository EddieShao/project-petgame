package com.example.petgame.Training;

import java.util.Random;

public class Reward {
    protected int x, y;
    private Random random;
    protected int type;
    protected boolean drawable;


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getDrawable() {
        return this.drawable;
    }

    public void settDrawable(boolean drawable) {
        this.drawable = drawable;
    }

}

class Heart extends Reward{

    private boolean drawable;
    public Heart (int x, int y){
        this.x = x;
        this.y = y;
        this.drawable = true;
    }


}

class EasterEgg extends Reward{
    public EasterEgg (int x, int y, int type){
        this.x = x;
        this.y = y;
        this.type = type;
        this.drawable = true;
    }

    public int getType() {return this.type;}
    public void setType(int type) {this.type = type;}

}
