package com.example.petgame.MemoryTile.TileManagers;

import com.example.petgame.MemoryTile.Specials;
import com.example.petgame.R;

import java.util.Random;

/**
 * The regular memory tile game.
 */
class MemoryTileManager implements TilesManager {

    /**
     * The total number of allowed fail trials.
     */
    private final int LIVES;

    /**
     * The total number of money locations on the map.
     */
    private final int MONEYNUM;

    /**
     * Number of columns in the game.
     */
    private final int HEIGHT;

    /**
     * Number of columns in the game.
     */
    private final int WIDTH;

    /**
     * The number of tiles with money clicked.
     */
    private int trialSuccess = 0;

    /**
     * The number of tiles without money clicked.
     */
    private int trialFail = 0;

    /**
     * The tile map with money locations.
     */
    private boolean[][] moneyMap;

    /**
     * The multiplier for payoffs.
     */
    private final int PAYMULTI;

    int getTrialSuccess() {
        return trialSuccess;
    }

    int getTrialFail() {
        return trialFail;
    }

    int getHEIGHT() {
        return HEIGHT;
    }

    int getWIDTH() {
        return WIDTH;
    }

    boolean getTile(int h, int w) {
        return moneyMap[h][w];
    }

    @Override
    public boolean[][] getMoneyMap() {
        return moneyMap;
    }

    @Override
    public String getLivesLeft() {
        return String.valueOf(LIVES - trialFail);
    }

    MemoryTileManager(String size) {
        // extract values from SizeSpecs
        SizeSpecs sizeSpecs = SizeSpecs.valueOfLabel(size);
        this.LIVES = sizeSpecs.lives;
        this.HEIGHT = sizeSpecs.height;
        this.WIDTH = sizeSpecs.width;
        this.MONEYNUM = sizeSpecs.moneyNum;
        this.PAYMULTI = sizeSpecs.payMulti;

        this.moneyMap = new boolean[HEIGHT][WIDTH];
        for (int h = 0; h < HEIGHT; h++) {
            for (int w = 0; w < WIDTH; w++) {
                this.moneyMap[h][w] = false;
            }
        }

        // Generates the money locations.
        Random random = new Random();
        int randomH, randomW;
        for (int m = 0; m < MONEYNUM; m++) {
            do {
                randomH = random.nextInt(HEIGHT);
                randomW = random.nextInt(WIDTH);
            } while (moneyMap[randomH][randomW]);

            this.moneyMap[randomH][randomW] = true;
        }
    }

    /**
     * Record the number of success / fail.
     *
     * @param position the identifier for the clicked tile.
     */
    @Override
    public void updateInfo(int position) {
        int h = position / WIDTH;
        int w = position % WIDTH;
        if (moneyMap[h][w]) {
            trialSuccess++;
        } else {
            trialFail++;
        }
    }

    @Override
    public boolean shouldEnd() {
        return trialFail >= LIVES || trialSuccess >= MONEYNUM;
    }

    @Override
    public int getPayoff() {
        return (trialSuccess - trialFail) * PAYMULTI;
    }

    @Override
    public Specials getSpecial() {
        return null;
    }
}
