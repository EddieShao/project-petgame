package com.example.petgame.MemoryTile;

public interface MemoryTileView {
    void livesUpdate(String lives);

    void setTiles(boolean[][] map);

    void endGame(int payoff, Specials special);
}
