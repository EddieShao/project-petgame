package com.example.petgame.MemoryTile.TileManagers;

import com.example.petgame.MemoryTile.Specials;

import java.util.Random;

class NightmareTileManager extends MemoryTileManager {

    private final int WIDTH;
    private boolean specialEnd;

    NightmareTileManager(String size) {
        super(size);
        WIDTH = super.getWIDTH();
        specialEnd = false;
    }

    @Override
    public void updateInfo(int position) {
        super.updateInfo(position);
        int h = position / WIDTH;
        int w = position % WIDTH;
        if (!super.getTile(h, w)) {
            Random random = new Random();
            if (random.nextInt(2) == 0) {
                specialEnd = true;
            }
        }
    }

    @Override
    public boolean shouldEnd() {
        return super.shouldEnd() || specialEnd;
    }

    @Override
    public int getPayoff() {
        if (specialEnd) {
            return -100;
        } else {
            return super.getPayoff();
        }
    }

    @Override
    public Specials getSpecial() {
        if (super.getTrialFail() == 0) {
            return Specials.EasterNightmare;
        } else if (specialEnd) {
            return Specials.HiddenLandmine;
        } else {
            return null;
        }
    }
}
