package com.example.petgame.Training;

import java.util.Random;

public class Obstacle {

	protected int x, y;
	private Random random;

	public Obstacle(int x, int y) {
		this.x = x;
		this.y = y;
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
