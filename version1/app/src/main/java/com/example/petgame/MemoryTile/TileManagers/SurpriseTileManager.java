package com.example.petgame.MemoryTile.TileManagers;

import com.example.petgame.MemoryTile.Specials;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The surprise mode of memory tile game.
 * Some tiles are assigned as "surprises";
 * Game terminates immediately when they're clicked.
 */
class SurpriseTileManager extends MemoryTileManager {

    /**
     * List of "surprise" tiles' coordinates.
     */
    private List<int[]> specials;

    /**
     * Variables from parent class.
     */
    private final int WIDTH;
    private final int HEIGHT;
    private final boolean[][] moneyMap;

    /**
     * Indicator of activated surprise / easter egg.
     */
    private Specials activatedSpecial;

    SurpriseTileManager(String size) {
        super(size);

        WIDTH = getWIDTH();
        HEIGHT = getHEIGHT();
        moneyMap = getMoneyMap();

        Random random = new Random();
        specials = new ArrayList<>();

        // every tile has 10% chance being a surprise.
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (random.nextInt(10) == 0) {
                    int[] location = {i, j};
                    System.out.println(i + " " + j);
                    specials.add(location);
                }
            }
        }
    }

    @Override
    public void updateInfo(int position) {
        int h = position / WIDTH;
        int w = position % WIDTH;

        super.updateInfo(position);

        // check if a special tile is clicked.
        for (int[] special : specials) {
            if (special[0] == h && special[1] == w) {
                if (moneyMap[h][w]) {
                    activatedSpecial = Specials.Bitcoin;
                } else {
                    activatedSpecial = Specials.PeePeePooPoo;

                    // Easter egg: first click fails
                    if (super.getTrialSuccess() == 0 && super.getTrialFail() == 1) {
                        activatedSpecial = Specials.EasterSurprise;
                    }
                }
            }
        }
    }

    @Override
    public boolean shouldEnd() {
        return super.shouldEnd() || activatedSpecial != null;
    }

    @Override
    public Specials getSpecial() {
        return activatedSpecial;
    }
}
