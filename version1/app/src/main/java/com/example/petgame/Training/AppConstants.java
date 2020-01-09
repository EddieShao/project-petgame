package com.example.petgame.Training;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import java.util.concurrent.ThreadLocalRandom;

// nextInt is normally exclusive of the top value,
// so add 1 to make it inclusive


/**
 * This class store all the necessary constants and information that are mostly consistent throught out the training game
 */
//TODO: change class name
public class AppConstants {
	static BitmapBank BitmapBank;
	static GameEngine gameEngine;

	static Context gameActivityContext;


	public void initialize(Context context){
		setScreenSize(context);
		BitmapBank = new BitmapBank(context.getResources());
		setGameConstants();
		gameEngine = new GameEngine(context);
		gameActivityContext = new GameActivity();
	}

	public  void setGameConstants(){
		Constants.GROUND.setValue(Constants.SCREEN_HEIGHT.getValue() * 3 /4);
		Constants.DISTANCE_BTW_BOXES.setValue(Constants.SCREEN_WIDTH.getValue());
	}



	//Return BitmapBank instance

	public static BitmapBank getBitmapBank() {
		return BitmapBank;
	}

	public static GameEngine getGameEngine() {
		return gameEngine;
	}

	private void setScreenSize(Context context){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		int width = metrics.widthPixels;
		int height = metrics.heightPixels;
		Constants.SCREEN_WIDTH .setValue(width);
		Constants.SCREEN_HEIGHT.setValue(height);
	}


}
