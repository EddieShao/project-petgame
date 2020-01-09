package com.example.petgame.Training;

public class HamsterTraining extends PetTraining {

	public HamsterTraining(){
		super();
		maxFrame = 2;

		y = Constants.GROUND.getValue() - AppConstants.getBitmapBank().getHamsterHeight();
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
		super.jump();
		currentFrame = -1;
		Constants.HAMSTER_BOUNCE.setValue(1);
	}
}
