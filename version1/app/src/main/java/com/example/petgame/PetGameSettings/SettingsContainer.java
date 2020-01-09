package com.example.petgame.PetGameSettings;

import android.content.Context;
import android.view.LayoutInflater;

/** Functionality required to allow a PetGameSettingsPopup to exist. */
public interface SettingsContainer {

    /**
     * Obtain the LayoutInflater of this PetGameSettingsPopup.
     * (We need this method because the Context type of this PetGameSettingsPopup is unknown.)
     *
     * @return The LayoutInflater of this PetGameSettingsPopup.
     */
    LayoutInflater getInflater();

    /** Set up the interaction in this SettingsContainer to open the settings. */
    void setUpShowSettings();

    /** Performs following operations on the dismissal of the settings popup. */
    void onDismissSettings();

    /** Return this SettingsContainer casted as a Context. */
    Context getSelfAsContext();

}
