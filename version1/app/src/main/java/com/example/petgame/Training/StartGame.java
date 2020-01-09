package com.example.petgame.Training;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.petgame.MainActivity;

/**
 * Prepare GameView and a button for exiting
 */
public class StartGame extends Activity implements View.OnClickListener {

    public static final String HEALTH_REWARD = "healthReward";
    GameView gameView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        FrameLayout game = new FrameLayout(this);
        gameView = new GameView (this);
        LinearLayout gameWidgets = new LinearLayout (this);

        Button endGameButton = new Button(this);
        TextView myText = new TextView(this);

        endGameButton.setWidth(300);
        endGameButton.setText("Start Game");

        gameWidgets.addView(myText);
        gameWidgets.addView(endGameButton);

        game.addView(gameView);
        game.addView(gameWidgets);


        //gameView = new GameView(this);
        setContentView(game);
        endGameButton.setText("Exit");
        endGameButton.setOnClickListener(this);

    }


    //for testing
//    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(this, TrainingMainActivity.class);
//        startActivity(intent);
//        this.finish();
//    }

    @Override
    public void onClick(View v) {
        // create the intent to return to the Account page
        Intent backToMain = new Intent(this, MainActivity.class);
        // pack a copy of the current Pet and the Account money to pass back
        backToMain.putExtra(HEALTH_REWARD, Constants.REWARD.getValue());

        // set the status of this activity as okay to send back to Account as a result
        this.setResult(Activity.RESULT_OK, backToMain);
        // finish this activity (perform intent to go back to Account)
        this.finish();
    }
}
