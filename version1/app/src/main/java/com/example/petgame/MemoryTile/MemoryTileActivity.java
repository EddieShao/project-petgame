package com.example.petgame.MemoryTile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petgame.MemoryTile.TileManagers.SizeSpecs;
import com.example.petgame.MemoryTile.TileManagers.TileManagerFactory;
import com.example.petgame.MemoryTile.TileManagers.TilesManager;
import com.example.petgame.R;

/**
 * The activity for memory tile game.
 */
public class MemoryTileActivity extends AppCompatActivity implements MemoryTileView {

    /**
     * A grid of memory tiles.
     */
    private GridView memoGrid;

    /**
     * Displays number of "remaining fail trials".
     */
    private TextView memoLives;

    /**
     * Fills the GridView with memory tiles when the activity starts.
     */
    private MemoGridAdapter memoGridAdapter;

    private MemoryTilePresenter presenter;

    /**
     * Name of calculated payoff that is passed back to PetGame.
     */
    public static final String MONEY = "Return money";
    public static final String SPECIAL = "Special event";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_tile);

        // Reference for components.
        // TODO: divider of grid?
        memoGrid = findViewById(R.id.memoTileGrid);
        memoLives = findViewById(R.id.textView4);

        Intent intent = getIntent();
        String mode = intent.getStringExtra(MemoryStartActivity.MODE);
        String size = intent.getStringExtra(MemoryStartActivity.SIZE);

        presenter = new MemoryTilePresenter(this, new CoverTilesInteractor(),
                new TileManagerFactory().createTilesManager(mode, size));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    /**
     * Update the number display of lives.
     *
     * @param lives chances left before game ends.
     */
    @Override
    public void livesUpdate(String lives) {
        memoLives.setText(lives);
    }

    /**
     * Set the width and contents of memoGrid.
     *
     * @param map the content of the game.
     */
    @Override
    public void setTiles(boolean[][] map) {
        memoGrid.setNumColumns(map[0].length);
        memoGridAdapter = new MemoGridAdapter(this, map, presenter::updateAfterClick);
        memoGrid.setAdapter(memoGridAdapter);
    }

    /**
     * Finish the game and go back to MemoryStartActivity.
     *
     * @param payoff  the amount of money received from gameplay.
     * @param special special events activated.
     */
    @Override
    public void endGame(int payoff, Specials special) {
        if (special != null) {
            Toast.makeText(this, "You've found " + special.toString(), Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent();
        intent.putExtra(MONEY, payoff);
        intent.putExtra(SPECIAL, special);
        this.setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
