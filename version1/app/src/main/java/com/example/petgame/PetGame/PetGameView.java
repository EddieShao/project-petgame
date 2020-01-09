package com.example.petgame.PetGame;

/** Interface for the PetGameView. Precondition of use: must extend Context. */
public interface PetGameView {

    /** Display the health onto this PetGameView. */
    void displayHealth();

    /** Display the energy onto this PetGameView. */
    void displayEnergy();

    /** Display the Account balance onto this PetGameView. */
    void displayBalance();

    /** Perform this PetGameView's interaction action. */
    void doInteractAction();

    /** Perform this PetGameView's passive action. */
    void doPassiveAction();

    /** Set the background of this PetGameView as the given color. */
    void displayBackground(int backgroundID);

    /** Display the number of candies remaining onto this PetGameView. */
    void displayCandy();

    /** Show the collect candy display. */
    void showCollectCandy();

    /** Hide the collect candy display. */
    void hideCollectCandy();

}
