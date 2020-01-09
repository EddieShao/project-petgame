package com.example.petgame.Training;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Updates the canvas with a delay
 */
public class GameThread extends Thread {

	SurfaceHolder surfaceHolder; //Surface object reference
	boolean isRunning; //Flag to detect whether thread is running or not
	long startTime, loopTime; //loop start time and loop duration
	long DELAY = 60;

	public GameThread(SurfaceHolder surfaceHolder) {
		this.surfaceHolder = surfaceHolder;
		isRunning = true;
	}

	@Override
	public void run() {
		while(isRunning){
			startTime = SystemClock.uptimeMillis();
			//locking the canvas
			Canvas canvas = surfaceHolder.lockCanvas(null);
			if (canvas != null){
				synchronized (surfaceHolder){
					AppConstants.getGameEngine().updateAndDrawBgd(canvas);
					AppConstants.getGameEngine().updateAndDrawPlatforms(canvas);
					AppConstants.getGameEngine().updateAndDrawBoxes(canvas);
					AppConstants.getGameEngine().updateAndDrawHearts(canvas);
					AppConstants.getGameEngine().updateAndDrawPet(canvas);
					AppConstants.getGameEngine().updateAndDrawEggs(canvas);
					//unlocking canvas
					surfaceHolder.unlockCanvasAndPost(canvas);
					if(AppConstants.getGameEngine().getGameState() == 2){
						setIsRunning(false);
					}
				}
			}

			loopTime = SystemClock.uptimeMillis() - startTime;

			if (loopTime < DELAY){
				try{
					Thread.sleep(DELAY - loopTime);
				}catch (InterruptedException e){
					Log.e("Interrupted", "Interrupted while sleeping");
				}
			}
		}
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setIsRunning(boolean running) {
		isRunning = running;
	}


}
