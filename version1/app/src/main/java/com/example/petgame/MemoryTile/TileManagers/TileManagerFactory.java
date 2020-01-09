package com.example.petgame.MemoryTile.TileManagers;

/**
 * Creates memory tile game manager given the mode and size wanted.
 */
public class TileManagerFactory {
    public TilesManager createTilesManager(String mode, String size) {
        switch (mode) {
            case "Normal":
                return new MemoryTileManager(size);
            case "Surprise":
                return new SurpriseTileManager(size);
            case "Nightmare":
                return new NightmareTileManager(size);
            default:
                return null;
        }
    }
}
