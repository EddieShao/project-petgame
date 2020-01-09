package com.example.petgame.Training;

/**
 * Inherit from PetTraining
 * Cats have the ability to double jump
 */
public class CatTraining extends PetTraining{
	private int [] jump_velocities = {-110, -70};


	public CatTraining() {
		super();
		maxFrame = 7;
		y = Constants.GROUND.getValue() - AppConstants.getBitmapBank().getCatHeight();
		max_jump = 2;
	}


	@Override
	public int nextFrame() {
		if(currentFrame >= 0  && currentFrame < maxFrame){
			return currentFrame +1;
		} else if (inAir == true){
			if (velocity < -20 ){
				return -1;
			}
			else if (velocity <= 25  && velocity >= -20){
				return  -2;
			}else{
				return -3;
			}
		} else {
			return 0;
		}
	}

	@Override
	public void jump() {
		velocity = jump_velocities[jump_count];
		currentFrame = -1;
		inAir = true;
		jump_count +=1;
	}
}
