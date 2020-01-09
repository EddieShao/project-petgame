package com.example.petgame.MemoryTile;

import com.example.petgame.MemoryTile.TileManagers.TilesManager;

class MemoryTilePresenter {
    private MemoryTileView memoView;
    private CoverTilesInteractor coverTilesInteractor;
    private TilesManager tilesManager;

    MemoryTilePresenter(MemoryTileView memoView, CoverTilesInteractor coverTilesInteractor, TilesManager tilesManager) {
        this.memoView = memoView;
        this.coverTilesInteractor = coverTilesInteractor;

        // Initialize the game contents.
        this.tilesManager = tilesManager;
    }

    void onResume() {
        if (memoView != null) {
            memoView.livesUpdate(tilesManager.getLivesLeft());
            memoView.setTiles(tilesManager.getMoneyMap());
        }
        coverTilesInteractor.coverTiles();
    }

    void onDestroy() {
        memoView = null;
    }

    /**
     * Updates the game and screen display after a tile is clicked.
     *
     * @param position the identifier for the clicked tile.
     */
    void updateAfterClick(int position) {
        tilesManager.updateInfo(position);

        memoView.livesUpdate(tilesManager.getLivesLeft());

        if (tilesManager.shouldEnd()) {
            endGame();
        }
    }

    void endGame() {
        memoView.endGame(tilesManager.getPayoff(), tilesManager.getSpecial());
    }
}
