package com.example.petgame.PetGame.PetGameSystem;

import com.example.petgame.Pet.Pet;
import com.example.petgame.R;

/** The data model of the pet game system. */
public class PetGame {

    /** The Pet to take care of in this PetGame. */
    private Pet Pet;

    /** Getter for Pet. */
    public Pet getPet() {
        return this.Pet;
    }

    /** This PetGame starter's Account's money. */
    private int balance;

    /** The number of free-train candies the Account associated with this PetGame has. */
    private int candies;

    /** Return the number of candies in the Account of this PetGame. */
    int numCandies() {
        return this.candies;
    }

    /** Add a piece of candy to the candies. */
    void addCandy() {
        this.candies++;
    }

    /** Remove a piece of candy from the candies. */
    void removeCandy() {
        this.candies--;
    }

    /** Getter for the account balance. */
    int getBalance() {
        return this.balance;
    }

    /**
     * Updates the account balance by the given amount. Positive values increase balance while
     * negative values spend from balance.
     *
     * Precondition: amount >= 0 || -amount < balance
     */
    void updateBalance(int amount) {
        this.balance += amount;
    }

    /** Whether or not hard mode is enabled. */
    private boolean hardMode;

    /** Getter for hardMode. */
    boolean inHardMode() {
        return this.hardMode;
    }

    /** Toggle this PetGame's hard mode option. */
    void toggleHardMode() {
        this.hardMode = !this.hardMode;
    }

    /** Whether or not music/sound is muted. */
    private boolean muted;

    /** Getter for muted. */
    boolean isMuted() {
        return this.muted;
    }

    /** Mute or un-mute this PetGame. */
    void toggleMuted() {
        this.muted = !this.muted;
    }

    /** The ID of the background music for the PetGame. */
    private int musicID;

    /** Getter for the music ID. */
    int getMusicID() {
        return this.musicID;
    }

    /**
     * Start a PetGame with the given Pet.
     *
     * @param pet The Pet to play the PetGame with.
     * @param accMoney The current balance in the Account for this PetGame instance.
     */
    public PetGame(Pet pet, int accMoney, int candies) {
        this.Pet = pet;
        this.balance = accMoney;
        this.hardMode = false;
        this.muted = false;
        this.musicID = R.raw.bg_music;
        this.candies = candies;
    }

}
