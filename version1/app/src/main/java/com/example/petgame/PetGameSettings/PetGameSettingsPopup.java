package com.example.petgame.PetGameSettings;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Switch;

import com.example.petgame.R;

/** The physical PopupWindow UI for the game's settings system. */
public class PetGameSettingsPopup {

    /** The amount of pixels to offset the y component of the settings pop-up down by. */
    private final static int Y_OFFSET = 60;

    /** The layout of this PetGameSettingsPopup. */
    private View layout;

    /** The PopupWindow that is the settings window. */
    private PopupWindow settings;

    /** The thing containing this settings popup. */
    private SettingsContainer container;

    /** The interactor that carries out all the back-end settings operations for the PetGame. */
    private PetGameSettingsInteractor interactor;

    /**
     * Create a new PetGameSettingsPopup with the given container as the root.
     *
     * @param container The root of this SettingsContainer.
     */
    public PetGameSettingsPopup(SettingsContainer container, SettingsReceiver receiver) {
        // inflate the layout in preparation of settings creation
        this.layout = container.getInflater().inflate(R.layout.popup_settings, null);
        // create a new interactor for this settings popup
        this.interactor = new PetGameSettingsInteractor(receiver, container.getSelfAsContext());
        // set the container as the given container
        this.container = container;

        // create a new settings window pop up
        this.settings = new PopupWindow(this.layout,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                false);

        // set up Views of settings window
        Button back = this.layout.findViewById(R.id.close_settings);  // back button
        back.setOnClickListener(v -> {
            this.settings.dismiss();
            this.container.onDismissSettings();
        });

        container.setUpShowSettings();  // container's open settings button

        this.setUpSwitches();  // hard mode and muted option switches

        this.setUpBackgrounds();  // background selection
    }

    /** Set up the hard mode and muted option switches in this popup. Initially toggle them on iff
     * the receiver already has their hard mode or muted option toggled on. */
    private void setUpSwitches() {
        Switch hardMode = this.layout.findViewById(R.id.toggle_hard_mode);  // hard mode switch
        if (this.interactor.inHardMode()) {  // toggle on if hard mode is on
            hardMode.toggle();
        }
        hardMode.setOnCheckedChangeListener((buttonView, isChecked) -> this.toggleHardMode());

        Switch muted = this.layout.findViewById(R.id.toggle_mute);  // toggle mute switch
        if (this.interactor.isMuted()) {  // toggle on if sound already muted
            muted.toggle();
        }
        muted.setOnCheckedChangeListener((buttonView, isChecked) -> this.toggleMuted());
    }

    /** Set up the selection options for changing the background of the receiver. */
    private void setUpBackgrounds() {
        // bedroom option
        ImageView bedroom = this.layout.findViewById(R.id.set_bedroom);
        bedroom.setOnClickListener(view -> this.setBackground(R.drawable.bedroom));
        // living room option
        ImageView livingRoom = this.layout.findViewById(R.id.set_living_room);
        livingRoom.setOnClickListener(view -> this.setBackground(R.drawable.living_room));
        // garden option
        ImageView garden = this.layout.findViewById(R.id.set_garden);
        garden.setOnClickListener(view -> this.setBackground(R.drawable.garden));
    }

    /**
     * Asks the interactor to set its SettingsReceiver's background to the Drawable with given ID.
     *
     * @param backgroundID The ID of the Drawable to set as the background.
     */
    private void setBackground(int backgroundID) {
        this.interactor.setBackground(backgroundID);
    }

    /** Toggle the SettingsReceiver's hard mode option. */
    private void toggleHardMode() {
        this.interactor.toggleHardMode();
    }

    /** Toggle the SettingsReceiver's music/sound mute option. */
    private void toggleMuted() {
        this.interactor.toggleMuted();
    }

    /** Display the settings window onto the container layout. */
    public void showSettings() {
        // display the popup in the center of the layout with a slight offset downwards.
        this.settings.showAtLocation(this.layout, Gravity.CENTER, 0, PetGameSettingsPopup.Y_OFFSET);
    }

}
