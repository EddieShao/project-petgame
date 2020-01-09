package com.example.petgame.Training;

import android.util.Log;

public enum Constants {
	/** The Constants initialized to their given key enums. */
	SCREEN_WIDTH(0), //screen's width
	SCREEN_HEIGHT(0), //screen's height
	PET_TYPE(0),// 1 = CAT, 2 = DOG, 3 = HAMSTER
	GROUND(0), // y coordinate for ground level
	GRAVITY(10), // velocity of natural falling
	JUMP_VELOCITY(-90), // velocity upwards when pet jump
	BASE_JUMP_VELOCITY(-90),
	POWER_JUMP_VELOCITY(-140),
	HAMSTER_BOUNCE(0),
	BOUNCE_VELOCITY(-50),
	NUM_OF_BOXES(3), // number of boxes recycling
	OBJECT_VELOCITY(30), // velocity of obstacles and platforms moving leftward
	DISTANCE_BTW_BOXES(0), // base distance between each box
	REWARD(0); // health reward for playing the game

	/** The value of the Constants*/
	private int value;

	/** private constructor for Constant*/
	Constants(int val){
		this.value = val;
	}

	/** getter for constants' value*/
	public int getValue(){
		return value;
	}

	/** setter for constants' value*/
	public void setValue(int value) {
		this.value = value;
		Log.i("Constant", this + " changed to " + this. value);
	}
}
