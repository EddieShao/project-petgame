package com.example.petgame.PetGameSettings;

/** A key for assigning properties to the settings configuration file. */
public enum Property {

    BACKGROUND("background"),
    HARD_MODE("hard_mode"),
    MUTED("muted");

    /** The String value of this Property. */
    private String property;

    /** Getter for property. */
    String getProperty() {
        return this.property;
    }

    Property(String value) {
        this.property = value;
    }

}
