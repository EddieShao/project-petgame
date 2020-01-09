package com.example.petgame.PetGame;

/** Interface for receiving game updates from the PetGameFacade. */
public interface GameUpdateReceiver {

    /** Display the current health statistic. */
    void displayHealth();

    /** Display the current energy statistic. */
    void displayEnergy();

    /** Display the current Account's balance. */
    void displayBalance();

    /** Do the interaction action. */
    void doInteractAction();

    /** Do the passive action. */
    void doPassiveAction();

    /** Display the current number of candies. */
    void displayCandy();

    /** Show the collect candy display. */
    void showCollectCandy();

    /** Hide the collect candy display. */
    void hideCollectCandy();

}
