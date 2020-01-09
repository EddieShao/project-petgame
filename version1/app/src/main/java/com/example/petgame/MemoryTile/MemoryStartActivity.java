package com.example.petgame.MemoryTile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petgame.Account.AccountFileManagerFactory;
import com.example.petgame.Account.EasterEggCollector;
import com.example.petgame.MemoryTile.TileManagers.SizeSpecs;
import com.example.petgame.R;

public class MemoryStartActivity extends AppCompatActivity {
    private int moneyCollected = 0;
    private Spinner modeSpinner, sizeSpinner;
    private TextView text;
    private ImageView image;

    private final String[] MODES = {"Normal", "Surprise", "Nightmare"};
    private final String[] SIZES = SizeSpecs.labels();

    private EasterEggCollector easterEggCollector;

    /**
     * Name of calculated payoff that is passed back to PetGame.
     */
    public static final String MONEY = "Return money";
    public static final String MODE = "Mode";
    public static final String SIZE = "size";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_start);

        text = findViewById(R.id.textBody);
        image = findViewById(R.id.imageView);

        modeSpinner = findViewById(R.id.spinnerMode);
        this.setUpSpinner(modeSpinner, MODES);

        sizeSpinner = findViewById(R.id.spinnerSize);
        this.setUpSpinner(sizeSpinner, SIZES);

        easterEggCollector = new AccountFileManagerFactory().buildEasterEggCollector(this, R.string.DATAFILE);
    }

    /**
     * Fill spinner with items of contents.
     *
     * @param spinner  a component for choosing items
     * @param contents a list of items
     */
    private void setUpSpinner(Spinner spinner, String[] contents) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, contents);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, MemoryTileActivity.class);

        intent.putExtra(MODE, modeSpinner.getSelectedItem().toString());
        intent.putExtra(SIZE, sizeSpinner.getSelectedItem().toString());

        this.startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            int newMoney = data.getIntExtra(MemoryTileActivity.MONEY, -1);
            Specials special = (Specials) data.getSerializableExtra(MemoryTileActivity.SPECIAL);

            if (newMoney != -1) {
                moneyCollected += newMoney;
            }

            // Easter egg check
            if (special == null) {
                image.setVisibility(View.INVISIBLE);
                String t = String.format(getString(R.string.memory_start_feedback), newMoney);
                text.setText(t);
            } else {
                image.setVisibility(View.VISIBLE);

                switch (special) {
                    case Bitcoin:
                        image.setImageDrawable(this.getDrawable(R.drawable.tile_bitcoin));
                        text.setText(this.getText(R.string.memory_start_special));
                        break;
                    case PeePeePooPoo:
                        image.setImageDrawable(this.getDrawable(R.drawable.tile_pppp));
                        text.setText(this.getText(R.string.memory_start_bad));
                        break;
                    case EasterSurprise:
                    case EasterNightmare:
                        image.setImageDrawable(this.getDrawable(R.drawable.tile_trophy));
                        text.setText(this.getText(R.string.memory_start_special));
                        break;
                    case HiddenLandmine:
                        image.setImageDrawable(this.getDrawable(R.drawable.gravestone));
                        text.setText(this.getText(R.string.memory_start_special));
                }
            }
            if (special == Specials.EasterSurprise) {
                System.out.println(special);
                easterEggCollector.addEasterEggPieceAndSave("Play1");
            } else if (special == Specials.EasterNightmare) {
                System.out.println(special);
                easterEggCollector.addEasterEggPieceAndSave("Play2");
            }
        }
    }

    public void exitGame(View view) {
        Intent intent = new Intent();
        intent.putExtra(MONEY, moneyCollected);
        this.setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
