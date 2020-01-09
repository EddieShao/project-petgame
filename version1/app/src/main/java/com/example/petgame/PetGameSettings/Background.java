package com.example.petgame.PetGameSettings;

/** Values for the background property of the settings. */
public enum Background {

    BEDROOM("bedroom"),
    LIVING_ROOM("living_room"),
    GARDEN("garden");

    /** The String value of this Background. */
    private String background;

    /** Getter for background. */
    String getBackground() {
        return this.background;
    }

    Background(String value) {
        this.background = value;
    }

}
