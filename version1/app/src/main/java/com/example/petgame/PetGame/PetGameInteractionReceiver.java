package com.example.petgame.PetGame;

/** Interface to receive updates from the PetGameInteractor. */
public interface PetGameInteractionReceiver {

    /** Update the health statistic of this PetGameInteractionReceiver. */
    void displayHealth();

    /** Update the energy statistic of this PetGameInteractionReceiver. */
    void displayEnergy();

    /** Update the balance statistic of this PetGameInteractionReceiver. */
    void displayBalance();

    /** Perform this PetGameInteractionReceiver's interact Pet action */
    void doInteractAction();

    /** Perform this PetGameInteractionReceiver's passive Pet action. */
    void doPassiveAction();

    /** Update the candy statistics of this PetGameInteractionReceiver. */
    void displayCandy();

    /**
     * Set the background as the image with the given ID.
     *
     * @param backgroundID The ID of the Drawable to set as the background.
     * @throws IllegalArgumentException When backgroundID is not a valid Drawable ID.
     */
    void displayBackground(int backgroundID);

    /** Show the collect candy display. */
    void showCollectCandy();

    /** Hide the collect candy display. */
    void hideCollectCandy();

}
