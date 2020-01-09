package com.example.petgame.Training;

public class DogTraining extends PetTraining {

	public DogTraining(){
		super();
		y = Constants.GROUND.getValue() - AppConstants.getBitmapBank().getDogHeight();
		maxFrame = 5;
	}
}
