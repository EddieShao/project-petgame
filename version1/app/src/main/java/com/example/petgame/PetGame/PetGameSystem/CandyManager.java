package com.example.petgame.PetGame.PetGameSystem;

import android.os.Handler;

/** Manages free-train candy for the PetGame. */
class CandyManager {

    /** Interface to receive updates from the CandyManager. */
    interface CandyUpdateReceiver {

        /** Display the updated number of candies and change candy image state if necessary. */
        void displayCandy();

        /** Show the collect candy display. */
        void showCollectCandy();

        /** Hide the collect candy display. */
        void hideCollectCandy();

    }

    /** The PetGame this CandyManager is managing candy for. */
    private PetGame petGame;

    /** The receiver of updates from this CandyManager. */
    private CandyUpdateReceiver receiver;

    void setReceiver(CandyUpdateReceiver receiver) {
        this.receiver = receiver;
    }

    /** The Handler in charge of running random candy display popups for the PetGame. */
    private Handler runHandler;

    /** Create a new CandyManager to manage this given PetGame. */
    CandyManager(PetGame game) {
        this.petGame = game;
        this.runHandler = new Handler();
    }

    /** Return the number of remaining candies in this PetGame. */
    int getCandies() {
        return this.petGame.numCandies();
    }

    /** Add a piece of candy to the PetGame. */
    void addCandy() {
        this.petGame.addCandy();
        this.receiver.displayCandy();
    }

    /** If there are still candies left, consume a piece of candy and return true to indicate
     * success in consumption. Otherwise, return false to indicate there are no more candies. */
    boolean useCandy() {
        if (this.petGame.numCandies() > 0) {
            this.petGame.removeCandy();
            this.receiver.displayCandy();
            return true;
        } else {
            return false;
        }
    }

    private Runnable candyDisplyer = new Runnable() {
        /** Every 10 seconds, there's 25% change of having a piece of candy appear */
        @Override
        public void run() {
            receiver.hideCollectCandy();
            runHandler.postDelayed(candyDisplyer, 30000);
            double choice = Math.random();
            if (choice <= 0.25) {
                receiver.showCollectCandy();
            }
            runHandler.postDelayed(candyDisplyer, 1000);
        }
    };

    /** Begin randomly showing pieces of candy. */
    void startCandyCollection() {
        this.candyDisplyer.run();
    }

    /** Stop randomly showing pieces of candy. */
    void stopCandyCollection() {
        this.runHandler.removeCallbacks(this.candyDisplyer);
    }

}
