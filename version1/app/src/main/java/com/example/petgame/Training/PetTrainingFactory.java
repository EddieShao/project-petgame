package com.example.petgame.Training;


import android.graphics.Bitmap;


public class PetTrainingFactory {
	//use getShape method to get object of type shape
	private int type = Constants.PET_TYPE.getValue();

	public PetTraining getPet(){
		if(type == 0){
			return null;
		} else if(type == 1){
			return new CatTraining();
		} else if(type == 2){
			return new DogTraining();

		} else if(type == 3){
			return new HamsterTraining();
		} else if(type == 4){
			return new DragonTraining();
		}
		return null;
	}

	public Bitmap getFrame(int frame){
		switch (type){
			case (1): return AppConstants.BitmapBank.getCat(frame);
			case (2): return AppConstants.BitmapBank.getDog(frame);
			case (3): return AppConstants.BitmapBank.getHamster(frame);
			case (4): return AppConstants.BitmapBank.getDragon(frame);
			default: throw  new TypeNotPresentException("type", null);
		}
	}

	public int getHeight(){
		switch (type){
			case (1): return AppConstants.BitmapBank.getCatHeight();
			case (2): return AppConstants.BitmapBank.getDogHeight();
			case (3): return AppConstants.BitmapBank.getHamsterHeight();
			case (4): return AppConstants.BitmapBank.getDragonHeight();
			default: throw  new TypeNotPresentException("type", null);
		}
	}

	public int getWidth() {
		switch (type){
			case (1): return AppConstants.BitmapBank.getCatWidth();
			case (2): return AppConstants.BitmapBank.getDogWidth();
			case (3): return AppConstants.BitmapBank.getHamsterWidth();
			case (4): return AppConstants.BitmapBank.getDragonWidth();
			default: throw  new TypeNotPresentException("type", null);
		}
	}
}
