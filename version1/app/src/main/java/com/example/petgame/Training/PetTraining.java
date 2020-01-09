package com.example.petgame.Training;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

/**
 * Parent class for CatTraining ( and DogTraining, HamsterTraining in Phase two)
 * Defines basic information and actions of the pet during the training game
 */
public class PetTraining implements Observer {


//	protected boolean first_jump = true;
//	protected boolean second_jump = true;
	protected int x, y, currentFrame, velocity;
	protected boolean inAir;
	int maxFrame;
	protected int max_jump;

	protected int jump_count;

	public PetTraining() {
		x = 50;
		y = Constants.GROUND.getValue();
		currentFrame = 0;
		maxFrame = 4;
		velocity = 0;
		inAir = false;
		max_jump = 1;
		jump_count = 0;
	}

	/** return true if the pet can jump (not exceeding its inAir cap)*/
	public boolean canJump(){
		return jump_count< max_jump;
	}

	/** return the index of the next frame, for animation purposes*/
	public int nextFrame(){
		if (currentFrame >= maxFrame){
			return 0;
		}
		return currentFrame + 1;
	}

	/** change the constants that cause the pet to jump*/
	public void jump(){
		velocity = Constants.JUMP_VELOCITY.getValue();
		//currentFrame = -1;
		inAir = true;
		jump_count +=1;
		Log.i("Jump", "with velocity "+ velocity);
	}


	@Override
	public void update(Observable o, Object arg) {
		if ((Boolean) arg && canJump()){ // jump triggered
			jump();
		}else if(!(Boolean) arg) { // hit ground triggered
			jump_count = 0;
			velocity = 0;
			inAir = false;
		}
	}

	public boolean isClass(String type){
		return type.equalsIgnoreCase(this.getClass().getSimpleName());
	}

	//getters and setters

	public int getMax_jump() {
		return max_jump;
	}

	public void setMax_jump(int max_jump) {
		this.max_jump = max_jump;
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

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public boolean isInAir() {
		return inAir;
	}

	public void setInAir(boolean inAir) {
		this.inAir = inAir;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

}
