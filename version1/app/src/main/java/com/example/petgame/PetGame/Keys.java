package com.example.petgame.PetGame;

/** The keys for grabbing various values from Intents that Activities invoke. */
public enum Keys {
    /** The Strings initialized to their given key enums. */
    PET("pet"),
    MONEY("money"),
    POSITION("position"),
    ENERGY("energy"),
    MADE_PURCHASE("made_purchase"),
    PET_TYPE("pet_type"),
    NUM_CANDY("num_candy");

    /** The value of the Keys. */
    private String key;

    /** Getter for key. */
    public String getKey() {
        return this.key;
    }

    /** Private constructor for Keys. */
    Keys(String key) {
        this.key = key;
    }
}
