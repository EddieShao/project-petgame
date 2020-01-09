package com.example.petgame.PetGame.PetGameSystem;

import android.os.Handler;

import com.example.petgame.Pet.Pet;

/** Manages interactions between the user and the physical Pet in the PetGame. */
public class PetInteractor {

    /** Interface for receiving Pet interaction updates from the PetInteractor. */
    interface PetInteractionReceiver {

        /** Perform this PetInteractionReceiver's interact action. */
        void doInteractAction();

        /** Perform this PetInteractionReceiver's passive action. */
        void doPassiveAction();

    }

    /** The receiver of Pet interaction updates from this PetInteractor. */
    private PetInteractionReceiver receiver;

    /** The total number of times the Pet was interacted with. */
    private int clicks;

    /** Getter for the number of clicks on the Pet. */
    int getClicks() {
        return this.clicks;
    }

    /** Setter for the pet interaction update receiver. */
    void setReceiver(PetInteractionReceiver receiver) {
        this.receiver = receiver;
    }

    /** The Pet in the PetGame this PetInteractor is managing. */
    private Pet pet;

    /** Getter for pet. */
    Pet getPet() {
        return this.pet;
    }

    /** The run handler in charge of delaying Pet actions. */
    private Handler runHandler;

    /**
     * Create a new PetInteractor for the given game
     *
     * @param game The PetGame to manage interactions for.
     */
    PetInteractor(PetGame game) {
        this.pet = game.getPet();
        this.runHandler = new Handler();
        this.clicks = 0;
    }

    /** Perform the PetGame's Pet's interaction action. */
    void performInteraction() {
        // pet has been clicked once
        this.clicks++;
        // perform interaction action
        this.receiver.doInteractAction();
        // wait a bit before doing the passive action
        this.runHandler.postDelayed(this.receiver::doPassiveAction, 1000);
    }

}
