package com.example.petgame.Training;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.petgame.MainActivity;
import com.example.petgame.PetGame.Keys;
import com.example.petgame.R;

/**
 * the MainActivity of Training game
 * where the player click to the play icon to start the game
 */
public class TrainingMainActivity extends Activity {

	public static final String HEALTH_REWARD = "healthReward";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_training);
		AppConstants game = new AppConstants();


		int type = getType(this.getIntent().getStringExtra(Keys.PET_TYPE.getKey()));
		Constants.PET_TYPE.setValue(type);
		//Constants.PET_TYPE.setValue(4); // for testing
		Log.i("Pet type","is initialized as" + "DOG");

		game.initialize(this.getApplicationContext());
	}

	private int getType(String type) {
		switch (type.toUpperCase()){
			case ("CAT"): return 1;
			case ("DOG"): return 2;
			case ("HAMSTER"): return 3;
			case ("DRAGON"): return 4;
			default: throw  new TypeNotPresentException(type,null);
		}

	}

	public void startGame(View view){
		Log.i("TrainingPlayButton", "clicked");
		Intent intent = new Intent(this, StartGame.class);
		startActivityForResult(intent, 123);
//		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 123 && resultCode == Activity.RESULT_OK){
			Intent backToMain = new Intent(this, MainActivity.class);
			// pack a copy of the current Pet and the Account money to pass back
			backToMain.putExtra(HEALTH_REWARD, data.getIntExtra(StartGame.HEALTH_REWARD, -1));

			// set the status of this activity as okay to send back to Account as a result
			this.setResult(Activity.RESULT_OK, backToMain);
			// finish this activity (perform intent to go back to Account)
			this.finish();
		}
	}

	/**
	 * return to the petGame page
	 * @param view
	 */
	public void returnToMain(View view) {
		// create the intent to return to the Account page
		Intent backToMain = new Intent(this, MainActivity.class);
		// pack a copy of the current Pet and the Account money to pass back
		backToMain.putExtra(HEALTH_REWARD, 0);

		// set the status of this activity as okay to send back to Account as a result
		this.setResult(Activity.RESULT_OK, backToMain);
		// finish this activity (perform intent to go back to Account)
		this.finish();
	}
}
