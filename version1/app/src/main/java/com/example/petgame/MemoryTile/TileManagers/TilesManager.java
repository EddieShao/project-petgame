package com.example.petgame.MemoryTile.TileManagers;

import com.example.petgame.MemoryTile.Specials;

public interface TilesManager {
    boolean[][] getMoneyMap();

    String getLivesLeft();

    void updateInfo(int position);

    boolean shouldEnd();

    int getPayoff();

    Specials getSpecial();
}
