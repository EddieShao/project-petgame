package com.example.petgame.PetGameSettings;

/** An application that can receive inputs of PetGameSettingsInteractor outputs. */
public interface SettingsReceiver {

    /** Toggle this SettingsReceiver's hard mode option. */
    void toggleHardMode();

    /** Toggle this SettingsReceiver's music/sound volume mute option. */
    void toggleMuted();

    /** Getter for whether or not hard mode is turned on. */
    boolean inHardMode();

    /** Getter for whether or not music/sound is muted. */
    boolean isMuted();

    /**
     * Set the background of this SettingsReceiver as the Drawable with the given ID.
     *
     * @param backgroundID The ID of the Drawable background image.
     * @throws IllegalArgumentException Upon obtaining an invalid background ID value.
     */
    void displayBackground(int backgroundID);

}