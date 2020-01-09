package com.example.petgame.PetGame.PetGameSystem;

import android.content.Context;
import android.media.MediaPlayer;

/** Manages the options for the PetGame. */
class OptionsManager {

    /** The PetGame this OptionsManager is managing settings options for. */
    private PetGame petGame;

    /** The MediaPlayer in charge of playing background music. */
    private MediaPlayer musicPlayer;

    OptionsManager(PetGame game) {
        this.petGame = game;
    }

    /** Toggle the PetGame's hard mode option. */
    void toggleHardMode() {
        this.petGame.toggleHardMode();
    }

    /** Determine if the PetGame is in hard mode or not. */
    boolean inHardMode() {
        return this.petGame.inHardMode();
    }

    /** Toggle the volume mute option for the PetGame. */
    void toggleMuted() {
        this.petGame.toggleMuted();
        if (this.petGame.isMuted()) {
            this.musicPlayer.pause();
        } else {
            this.musicPlayer.start();
        }
    }

    /** Determine whether this PetGame has sounds muted or not. */
    boolean isMuted() {
        return this.petGame.isMuted();
    }

    /**
     * Play the sound corresponding to when the Pet gets clicked.
     *
     * @param context The context containing the audio.
     */
    void playInteractSound(Context context) {
        if (!this.petGame.isMuted()) {
            MediaPlayer interactSoundPlayer = MediaPlayer.create(context,
                    this.petGame.getPet().getSoundID());
            interactSoundPlayer.start();
        }
    }

    /** Play the PetGame's music from the given Context and store it in this OptionsManager. */
    void playMusic(Context context) {
        this.musicPlayer = MediaPlayer.create(context, petGame.getMusicID());
        this.musicPlayer.setLooping(true);
        this.musicPlayer.start();
    }

    /**
     * Terminate the soundtrack of the game.
     *
     * Precondition: this.musicPlayer is already playing.
     * */
    void stopMusic() {
        this.musicPlayer.stop();
        this.musicPlayer.release();
    }

}
