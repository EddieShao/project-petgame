package com.example.petgame.Training;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.petgame.Account.AccountFileManagerFactory;
import com.example.petgame.Account.EasterEggCollector;
import com.example.petgame.R;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class is in charge of drawing the objects according to their specific state
 * and letting the objects know when there is a special situation.
 */

public class GameEngine {

	private BackGroundImage backGroundImage;
	private PetTraining pet;
	private PetTrainingFactory ptf;
	private JumpManager jumpManager;
	private int gameState = 1;
	private ArrayList<Box> boxes;
	private Platform platform;
	private Random random;
	private int score;
	private int scoring_obstacles;
	private Paint scorePaint;
	private int level;
	private int hearts = 1;
	private int screenHeight, screenWidth;
	private EasterEggCollector collectorEgg;
	private Heart heart;
	private EasterEgg egg;


	/**
	 * initialization of Game Engine
	 */
	public GameEngine(Context context) {
		this.backGroundImage = new BackGroundImage();
		screenHeight = Constants.SCREEN_HEIGHT.getValue();
		screenWidth  = Constants.SCREEN_WIDTH.getValue();
		/** where boxes initiate*/
        level = Constants.GROUND.getValue();
        /** Factory returns wanted PetTraining type*/
        ptf = new PetTrainingFactory();
		pet = ptf.getPet();
		if (Constants.PET_TYPE.getValue() == 3) hearts += 2;
		jumpManager = new JumpManager();
		jumpManager.addObserver(pet);
		//0 = not stated; 1 = playing; 2 = game over
		gameState = 1;
		//initiate boxes
		boxes = new ArrayList<>();
		random = new Random();
		for (int i =0; i < Constants.NUM_OF_BOXES.getValue(); i++){
			int x = screenWidth + i * Constants.DISTANCE_BTW_BOXES.getValue();
			int y = Constants.GROUND.getValue();
			Box b = new Box(x, y);
			int height = AppConstants.BitmapBank.getBoxHeight(b.getType()) * 3 /4;
			b.setY(Constants.GROUND.getValue()  - height);
			boxes.add(b);
		}
		platform = new Platform(screenWidth,
				Constants.GROUND.getValue() + 2 * Constants.JUMP_VELOCITY.getValue());
		score = 0;
		scoring_obstacles = 0;
		scorePaint = new Paint();
    	scorePaint.setColor(Color.WHITE);
    	scorePaint.setTextAlign(Paint.Align.LEFT);
    	scorePaint.setTextSize(100);
		AccountFileManagerFactory afmf = new AccountFileManagerFactory();
		collectorEgg = afmf.buildEasterEggCollector(context, R.string.DATAFILE);
		collectorEgg.addEasterEggPieceAndSave("Train1");
		heart = new Heart(0,0);
		egg = new EasterEgg(0, 0, 1);
	}

	/**
	 * updates and draws background image on canvas
	 * moves the image from right to left, and recursively
	 * @param canvas
	 */
	public void updateAndDrawBgd(Canvas canvas){
		int bgdWidth = AppConstants.BitmapBank.getBackgroundWidth();
		if (gameState == 1){

			backGroundImage.setX(backGroundImage.getX() - backGroundImage.getVelocity());
			if (backGroundImage.getX() < -bgdWidth){
				backGroundImage.setX(0);
			}

		}
		canvas.drawBitmap(AppConstants.BitmapBank.getBackground(),
				backGroundImage.getX(), backGroundImage.getY(), null);
		if (backGroundImage.getX() < -(bgdWidth - screenWidth)){
			canvas.drawBitmap(AppConstants.BitmapBank.getBackground(),
					backGroundImage.getX() + bgdWidth,
					backGroundImage.getY(),null);
		}
	}

	/**
	 * Changes the y coordinate of the cat
	 * detects whether it is in air or on ground
	 * @param canvas
	 */
	public void updateAndDrawPet(Canvas canvas){
		if (gameState == 1){
			if (jumpManager.getState() || pet.isInAir()){ // if in air, then fall by gravity
				freeFall();
			}else{
				pet.setVelocity(0);
			}
			// might want to move this to PetTraining as a method

			int currentFrame = pet.getCurrentFrame();
			canvas.drawBitmap(ptf.getFrame(currentFrame), pet.getX(), pet.getY(), null);
			currentFrame = pet.nextFrame();
			pet.setCurrentFrame(currentFrame);

		}
	}

	/**
	 * have obstacles move from right to left with random sizes and appearanaces
	 * score points when the pet jump past a box
	 * end the game when there is a collision
	 * @param canvas
	 */
	public void updateAndDrawBoxes(Canvas canvas){
		if (gameState == 1){
		    if (level == Constants.GROUND.getValue() && ((boxes.get(scoring_obstacles).getX() < pet.getX() + ptf.getWidth())
		        && (boxes.get(scoring_obstacles).getY()< pet.getY() + ptf.getHeight())))
            {

            	hearts -= 1;
            	scoring_obstacles ++;
				if (scoring_obstacles > Constants.NUM_OF_BOXES.getValue() - 1){
					scoring_obstacles = 0;
				}

            } else if (boxes.get(scoring_obstacles).getX() < pet.getX() -
					AppConstants.BitmapBank.getBoxWidth(scoring_obstacles)+ ptf.getWidth() * 3 / 4){
				score += 100;
				scoring_obstacles ++;
				if (scoring_obstacles > Constants.NUM_OF_BOXES.getValue() - 1){
					scoring_obstacles = 0;
				}
			}


			for (int i = 0; i < Constants.NUM_OF_BOXES.getValue(); i++){
				Box b = boxes.get(i);
				if (b.getX() < -AppConstants.BitmapBank.getBoxWidth(b.getType())){
					b.randomType();
					b.setX(b.getX() + Constants.NUM_OF_BOXES.getValue() * Constants.DISTANCE_BTW_BOXES.getValue());

				}
				b.setX(b.getX() - Constants.OBJECT_VELOCITY.getValue());
				int height = AppConstants.BitmapBank.getBoxHeight(b.getType()) * 3 /4;
				canvas.drawBitmap(AppConstants.BitmapBank.getBox(b.getType()),
						b.getX(),b.getY(),null);
			}
			canvas.drawText("Point: " + score, screenWidth/2, 110, scorePaint );

		}



	}

	public void updateAndDrawPlatforms(Canvas canvas){
		if (gameState == 1){
			if (platform.getX() < -AppConstants.BitmapBank.getPlatformWidth()){
				int distance = getRandomDistance();
				platform.setX(distance);
				boolean generateAHeart = generateHeart();
				if (generateAHeart){
						heart.settDrawable(true);
						heart.setX(distance + AppConstants.getBitmapBank().getPlatformWidth() / 2);
						heart.setY(platform.getY() + Constants.JUMP_VELOCITY.getValue());
				}
				boolean generateAnEgg = generateEgg();
				if (generateAnEgg){
						egg.setType(generateEggType());
						egg.settDrawable(true);
						egg.setX(distance + AppConstants.getBitmapBank().getPlatformWidth() * 3 / 5);
						egg.setY(platform.getY() + 5 * Constants.JUMP_VELOCITY.getValue());

				}

			}

			if (pet.getX() + (ptf.getWidth() / 4)> platform.getX() + AppConstants.BitmapBank.getPlatformWidth()){
			    level = Constants.GROUND.getValue();
			    pet.setInAir(true);
            } else if (pet.getX() + ptf.getWidth() >= platform.getX() && pet.getY() + ptf.getHeight() <= platform.getY()){
			    level = platform.getY();
            } else {
				level = Constants.GROUND.getValue();
			}
			platform.setX(platform.getX() - Constants.OBJECT_VELOCITY.getValue());
			heart.setX(heart.getX()-Constants.OBJECT_VELOCITY.getValue());
//				int height = AppConstants.BitmapBank.getBoxHeight(platform.getType()) * 3 /4;
			egg.setX(egg.getX() - Constants.OBJECT_VELOCITY.getValue());
			platform.setY(Constants.GROUND.getValue()  -
					AppConstants.BitmapBank.getPlatformHeight());
			canvas.drawBitmap(AppConstants.BitmapBank.getPlatform(),
					platform.getX(), platform.getY(),null);

		}

	}

	public void updateAndDrawHearts(Canvas canvas){
		if (gameState == 1){


			if (heart.getDrawable()) {
				if (level != Constants.GROUND.getValue()) {
					if (pet.getX() + ptf.getWidth() > heart.getX() && (pet.getY() < heart.getY())){
						hearts += 1;
						heart.settDrawable(false);
					}
				}
				canvas.drawBitmap(AppConstants.getBitmapBank().getHeart(), heart.getX(), heart.getY(), null);
			}
			if (hearts > 0) {
				int heartX = 20;
				int heartY = screenHeight - 2 * AppConstants.BitmapBank.getHeartHeight();
				int betweenHearts = AppConstants.BitmapBank.getHeartHeight() * 3 / 2;
				for (int i = 0; i < hearts; i++) {
					canvas.drawBitmap(AppConstants.BitmapBank.getHeart(),
							heartX + i * betweenHearts, heartY, null);

				}
			}else if (hearts == 0) {
				gameState = 2;
				Log.i("collision", "collision detected between pet and box");
//                AppConstants a = new AppConstants();
//                Context b = a.gameActivityContext;
//				Context context = AppConstants.gameActivityContext;
//				Intent intent = new Intent(GameView.this, GameOver.class);
//				intent.putExtra("score",score);
//				context.startActivity(intent);
//				((Activity)context).finish();
				canvas.drawText("GAMEOVER ", screenWidth/4,
						screenHeight/2, scorePaint );
				canvas.drawText("your point: "+ score, screenWidth/4,
						screenHeight/5 * 3 , scorePaint);
				Constants.REWARD.setValue(15 + Math.round(score/800));
				canvas.drawText("health: "+ Constants.REWARD.getValue(), screenWidth/4,
						screenHeight /10 * 7 , scorePaint);
				Log.i("game state", "game over");
			}

		}
	}

	public void updateAndDrawEggs(Canvas canvas){
		if (gameState == 1){
			if (egg.getDrawable()){
				if (level != Constants.GROUND.getValue()&& pet.isInAir()){
					if (pet.getX() + ptf.getWidth() > heart.getX()
							&& (pet.getY() < heart.getY())){
						collectorEgg.addEasterEggPieceAndSave("Train"+egg.getType());
						egg.settDrawable(false);
					}

				}
				canvas.drawBitmap(AppConstants.getBitmapBank().getEgg(egg.getType()), egg.getX(), egg.getY(), null);
			}
		}

	}

	private void freeFall(){
		pet.setVelocity(pet.getVelocity() + Constants.GRAVITY.getValue());

		// if pet reaches level/ground, set state to on ground
		if (pet.getY() + pet.getVelocity() >= level - ptf.getHeight()){
			pet.setY(level  - ptf.getHeight() );
			jumpManager.setState(false);
			pet.setInAir(false);
			if (Constants.HAMSTER_BOUNCE.getValue() == 1){
				Constants.JUMP_VELOCITY.setValue(Constants.BOUNCE_VELOCITY.getValue());
				AppConstants.getGameEngine().getJumpManager().setState(true);
				Constants.JUMP_VELOCITY.setValue(Constants.BASE_JUMP_VELOCITY.getValue());
				Constants.HAMSTER_BOUNCE.setValue(0);
			}
			Log.i("HIT GROUND", "pet (" + (pet.getY() + ptf.getHeight()) +")  hit level (" + level + ")");
		}else{
			pet.setY(pet.getY() + pet.getVelocity());
		}
	}

	public PetTraining getPet() {
		return pet;
	}

	public int getGameState() {
		return gameState;
	}

	public JumpManager getJumpManager() {
		return jumpManager;
	}

	public  int getRandomDistance(){
		int randomNum = ThreadLocalRandom.current().nextInt(Constants.SCREEN_WIDTH.getValue(),
				Constants.SCREEN_HEIGHT.getValue()* 3 + 1);
		return randomNum;
	}

	public boolean generateHeart(){
		int randomNum = ThreadLocalRandom.current().nextInt(1, 2);
		if (randomNum == 1){
			return true;
		} else{
			return false;
		}

	}

	public boolean generateEgg(){
		int randomNum = ThreadLocalRandom.current().nextInt(1, 2);
		if (randomNum == 1){
			return true;
		} else{
			return false;
		}

	}

	public int generateEggType(){
		int randomType = ThreadLocalRandom.current().nextInt(1, 4);
		return randomType;
	}
}
