package com.example.petgame.Training;

public class BackGroundImage {

	private int bgImageX, bgImageY, bgImageVelocity;

	public BackGroundImage(){
		bgImageX = 0;
		bgImageY = 0;
		bgImageVelocity = 16;
	}

	public int getX() {
		return bgImageX;
	}

	public void setX(int bgImageX) {
		this.bgImageX = bgImageX;
	}

	public int getY() {
		return bgImageY;
	}

	public void setY(int bgImageY) {
		this.bgImageY = bgImageY;
	}

	public int getVelocity() {
		return bgImageVelocity;
	}

	public void setVelocity(int bgImageVelocity) {
		this.bgImageVelocity = bgImageVelocity;
	}
}
