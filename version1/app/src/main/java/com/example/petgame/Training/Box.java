package com.example.petgame.Training;

import java.util.Random;

/**
 * Obstacle
 */
public class Box extends Obstacle{

//	private int x, y;
//	private Random random;
	private int type;

	public Box(int x, int y) {
		super(x,y);
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

}

