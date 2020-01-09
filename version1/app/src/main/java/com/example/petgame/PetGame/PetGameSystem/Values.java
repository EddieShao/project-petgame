package com.example.petgame.PetGame.PetGameSystem;

/** The value constants for various processes in this PetGameActivity.
 * Use and toggle these values to balance game difficulty and functionality. */
public enum Values {

    /** The integers instantiated to their given value enums. */
    TRAINING_COST(30),
    NORMAL_HEALTH_DRAIN_DELAY(3000),
    NORMAL_ENERGY_RECOVERY_DELAY(4000),
    HARD_HEALTH_DRAIN_DELAY(500),
    HARD_ENERGY_RECOVERY_DELAY(64000);

    /** The value of the Values object. */
    private int value;

    /** Getter for value. */
    public int getValue() {
        return this.value;
    }

    /** Create a new Values object. */
    Values(int num) {
        this.value = num;
    }
}
