package com.example.petgame.Training;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.petgame.R;

/** Where images and their information stores*/
public class BitmapBank {
	Bitmap background;

	//TODO: MAKE THIS MORE GENERAL
	Bitmap cat[];
	Bitmap catInAir[];
	Bitmap dog[];
	Bitmap hamster[];
	Bitmap hamsterInAir[];
	Bitmap dragon[];
	Bitmap boxes[];
	Bitmap planes[];
	Bitmap platform[];
	Bitmap heart[];
	Bitmap eggs[];


	public BitmapBank(Resources res) {
		int type = Constants.PET_TYPE.getValue();
		if (type != 0)
			background = BitmapFactory.decodeResource(res, R.drawable.background);
		else
			background = BitmapFactory.decodeResource(res, R.drawable.sky);
		background = scaleImage(background);

		//store pet graphics
		platform = new Bitmap[1];
		platform[0] = BitmapFactory.decodeResource(res, R.drawable.table);

		heart = new Bitmap[1];
		heart[0] = BitmapFactory.decodeResource(res, R.drawable.heart);

		if (type == 1){
			cat = new Bitmap[8];
			cat[0] = BitmapFactory.decodeResource(res,R.drawable.cat_walk1);
			cat[1] = BitmapFactory.decodeResource(res, R.drawable.cat_walk2);
			cat[2] = BitmapFactory.decodeResource(res, R.drawable.cat_walk3);
			cat[3] = BitmapFactory.decodeResource(res, R.drawable.cat_walk4);
			cat[4] = BitmapFactory.decodeResource(res, R.drawable.cat_walk5);
			cat[5] = BitmapFactory.decodeResource(res, R.drawable.cat_walk6);
			cat[6] = BitmapFactory.decodeResource(res, R.drawable.cat_walk7);
			cat[7] = BitmapFactory.decodeResource(res, R.drawable.cat_walk8);
			catInAir = new Bitmap[4];
			catInAir[1] = BitmapFactory.decodeResource(res, R.drawable.cat_jump1);
			catInAir[2] = BitmapFactory.decodeResource(res, R.drawable.cat_jump2);
			catInAir[3] = BitmapFactory.decodeResource(res, R.drawable.cat_jump3);
		}

		if (type == 2){
			dog = new Bitmap[6];
			dog[0] = BitmapFactory.decodeResource(res,R.drawable.dog_run1);
			dog[1] = BitmapFactory.decodeResource(res,R.drawable.dog_run2);
			dog[2] = BitmapFactory.decodeResource(res,R.drawable.dog_run3);
			dog[3] = BitmapFactory.decodeResource(res,R.drawable.dog_run4);
			dog[4] = BitmapFactory.decodeResource(res,R.drawable.dog_run5);
			dog[5] = BitmapFactory.decodeResource(res,R.drawable.dog_run6);
		}

		if (type == 3){
			hamster = new Bitmap[3];
			hamster[0] = BitmapFactory.decodeResource(res,R.drawable.ham_run1);
			hamster[1] = BitmapFactory.decodeResource(res,R.drawable.ham_run2);
			hamster[2] = BitmapFactory.decodeResource(res,R.drawable.ham_run3);

			hamsterInAir = new Bitmap[4];
			hamsterInAir[1] = BitmapFactory.decodeResource(res,R.drawable.ham_jump1);
			hamsterInAir[2] = BitmapFactory.decodeResource(res,R.drawable.ham_jump2);
			hamsterInAir[3] = BitmapFactory.decodeResource(res,R.drawable.ham_jump3);

		}

		if (type == 4){
			dragon = new Bitmap[4];
			dragon[0] = BitmapFactory.decodeResource(res,R.drawable.dragon1);
			dragon[1] = BitmapFactory.decodeResource(res,R.drawable.dragon2);
			dragon[2] = BitmapFactory.decodeResource(res,R.drawable.dragon3);
			dragon[3] = BitmapFactory.decodeResource(res,R.drawable.dragon4);
		}

		if (type != 0){
			boxes = new Bitmap[3];
			boxes[0] = BitmapFactory.decodeResource(res, R.drawable.box_small);
			boxes[1] = BitmapFactory.decodeResource(res, R.drawable.box_small);
			boxes[2] = BitmapFactory.decodeResource(res, R.drawable.boxes);
		}
		else{
			planes = new Bitmap[1];
			planes[0] = BitmapFactory.decodeResource(res, R.drawable.plane);
		}


		eggs = new Bitmap[3];
		eggs[0] = BitmapFactory.decodeResource(res, R.drawable.egg1);
		eggs[1] = BitmapFactory.decodeResource(res, R.drawable.egg2);
		eggs[2] = BitmapFactory.decodeResource(res, R.drawable.egg3);
	}

	/**getters and setters*/
	public Bitmap getHeart() {return heart[0];}

	public Bitmap getEgg(int type) {return eggs[type - 1];}

	public int getEggHeight(){return eggs[2].getHeight();}
	public int getEggWidth(){return eggs[2].getWidth();}

	public Bitmap getBox(int i) {
		return boxes[i];
	}
	public int  getHeartWidth(){
		return heart[0].getWidth();
	}

	public int  getHeartHeight(){
		return heart[0].getHeight();
	}

	public Bitmap getPlatform() {return platform[0]; }

	public int getPlatformWidth (){
		return platform[0].getWidth();
	}

	public int getPlatformHeight (){
		return platform[0].getHeight();
	}

	public int getBoxWidth(int i){
		return boxes[i].getWidth();
	}

	public int getBoxHeight(int i){
		return boxes[i].getHeight() ;
	}

	public Bitmap getCat(int frame){
		if (frame>=0){
			return cat[frame];
		}else{
			return catInAir[-frame];
		}
	}

	public int getCatWidth(){
		return cat[0].getWidth();
	}

	public int getCatHeight(){
		return cat[0].getHeight();
	}

	public Bitmap getDog(int frame){
		return dog[frame];
	}
	public int getDogWidth(){
		return dog[0].getWidth();
	}

	public int getDogHeight(){
		return dog[0].getHeight();
	}

	public Bitmap getHamster(int frame){
		if (frame>=0){
			return hamster[frame];
		}else{
			return hamsterInAir[-frame];
		}
	}

	public int getHamsterWidth(){
		return hamster[0].getWidth();
	}

	public int getHamsterHeight(){
		return hamster[0].getHeight();
	}

	public Bitmap getDragon(int frame){
		return dragon[frame];
	}
	public int getDragonWidth(){
		return dragon[0].getWidth();
	}

	public int getDragonHeight(){
		return dragon[0].getHeight();
	}

	//Return background bitmap
	public Bitmap getBackground() {
		return background;
	}

	//return background width
	public int getBackgroundWidth() {
		return background.getWidth();
	}

	//return background width
	public int getBackgroundHeight() {
		return background.getHeight();
	}

	public Bitmap scaleImage(Bitmap bitmap){
		float widthHeightRatio = getBackgroundWidth() / getBackgroundHeight();
		int scaledWidth = (int) widthHeightRatio * Constants.SCREEN_HEIGHT.getValue();
		return Bitmap.createScaledBitmap(bitmap, scaledWidth, Constants.SCREEN_HEIGHT.getValue(), false);
	}

}
