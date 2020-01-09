package com.example.petgame.Training;

public class DragonTraining extends PetTraining {

	public DragonTraining(){
		super();
		maxFrame = 3;
		y = Constants.GROUND.getValue() - AppConstants.getBitmapBank().getDragonHeight();
		max_jump = 5;
	}

	@Override
	public void jump() {
		velocity = -80;
	}
}
