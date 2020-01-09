package com.example.petgame.PetGameSettings;

import android.content.Context;

import com.example.petgame.R;

/** Interacts with a settings receiver to toggle settings depending on input from presenter. */
class PetGameSettingsInteractor {

    /** The receiver of inputs from this PetGameSettingsInteractor. */
    private SettingsReceiver receiver;

    /** The manager for the settings configuration file. */
    private SettingsFileManager fileManager;

    /** Create a new PetGameSettingsInteractor for the given SettingsReceiver.
     *
     * @param receiver Receives, as input, the output of methods in this PetGameSettingsInteractor.
     */
    PetGameSettingsInteractor(SettingsReceiver receiver, Context context) {
        this.receiver = receiver;
        this.fileManager = new SettingsFileManager(context);

        // obtain initial settings from the config file
        String[] initProps = this.fileManager.readFromFile();

        // set initial toggles and background settings
        if (initProps[0].equals(Background.BEDROOM.getBackground())) {
            this.setBackground(R.drawable.bedroom);
        } else if (initProps[0].equals(Background.LIVING_ROOM.getBackground())) {
            this.setBackground(R.drawable.living_room);
        } else {
            this.setBackground(R.drawable.garden);
        }

        if (initProps[1].equals("true")) {
            // the game is in hard mode, toggle that setting
            this.toggleHardMode();
        }

        if (initProps[2].equals("true")) {
            // the game is muted, toggle that setting
            this.toggleMuted();
        }
    }

    /** Returns whether or not the receiver is in hard mode. */
    boolean inHardMode() {
        return this.receiver.inHardMode();
    }

    /** Toggle the receiver's hard mode option. */
    void toggleHardMode() {
        this.receiver.toggleHardMode();
        this.fileManager.writeToFile(Property.HARD_MODE, this.receiver.inHardMode());
    }

    /** Returns whether or not the receiver is muted. */
    boolean isMuted() {
        return this.receiver.isMuted();
    }

    /** Toggle the receiver's mute option. */
    void toggleMuted() {
        this.receiver.toggleMuted();
        this.fileManager.writeToFile(Property.MUTED, this.receiver.isMuted());
    }

    /**
     * Set the receiver's background option to the given Drawable ID.
     *
     * @param backgroundID The ID of the Drawable background image.
     * @throws IllegalArgumentException Upon obtaining an invalid background ID value.
     */
    void setBackground(int backgroundID) {
        this.receiver.displayBackground(backgroundID);
        this.fileManager.writeToFile(Property.BACKGROUND, backgroundID);
    }

}
