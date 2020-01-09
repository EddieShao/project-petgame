package com.example.petgame.Training;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.petgame.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {
    TextView tvScore, tvPersonalBest;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        int score = getIntent().getExtras().getInt("score");
        SharedPreferences pref = getSharedPreferences("MyPref", 0);
        int scoreSP = pref.getInt("scoreSP", 0);
        SharedPreferences.Editor editor = pref.edit();
        if (score>scoreSP){
            scoreSP = score;
            editor.putInt("scoreSp", scoreSP);
            editor.commit();
        }
        tvScore = findViewById(R.id.tvScore);
        tvPersonalBest = findViewById(R.id.tvPersonalBest);
        tvScore.setText(""+score);
        tvPersonalBest.setText(""+scoreSP);


    }
    public void restart(View view){
        Intent intent = new Intent(GameOver.this, TrainingMainActivity.class);
        startActivity(intent);
        finish();
    }



}

