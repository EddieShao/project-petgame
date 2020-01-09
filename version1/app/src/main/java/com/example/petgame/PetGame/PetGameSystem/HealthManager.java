package com.example.petgame.PetGame.PetGameSystem;

import android.os.Handler;

/** Manages health for the PetGame. */
class HealthManager {

    /** Interface for receiving health updates from the HealthManager. */
    interface HealthUpdateReceiver {

        /** Update the health of this HealthUpdateReceiver. */
        void displayHealth();

    }

    /** The PetGame this HealthManager is managing health for. */
    private PetGame petGame;

    /** The receiver of any updates this HealthManager carries out. */
    private HealthUpdateReceiver receiver;

    /** Setter for the health update receiver. */
    void setReceiver(HealthUpdateReceiver receiver) {
        this.receiver = receiver;
    }

    /** The run handler in charge of running health drain repeating tasks. */
    private Handler runHandler;

    /**
     * Create a new HealthManager for the given PetGame.
     *
     * @param petGame The PetGame to manage health for.
     */
    HealthManager(PetGame petGame) {
        this.petGame = petGame;
        this.runHandler = new Handler();
    }

    /**
     * If amount > 0, increase the Pet's health by that amount. Otherwise, if amount < 0, decrease
     * the Pet's health by that amount.
     *
     * @param amount The amount to change the Pet's health by.
     */
    void updateHealth(int amount) {
        int initHealth = this.petGame.getPet().getHealth();  // initial health for comparison
        this.petGame.getPet().updateHealth(amount);

        if (!this.petGame.getPet().isAlive()) {
            // if the Pet dies, terminate process
            this.receiver.displayHealth();
        } else if (initHealth != this.petGame.getPet().getHealth()) {
            // health has changed, notify receiver
            this.receiver.displayHealth();
        }
    }

    /** Drains the health of the Pet on call to run. */
    private Runnable healthDrainer = new Runnable() {
        /** Run the following processes on a repeating task. */
        @Override
        public void run() {
            // delay by some time depending on difficulty settings
            if (petGame.inHardMode()) {
                runHandler.postDelayed(healthDrainer, Values.HARD_HEALTH_DRAIN_DELAY.getValue());
            } else {
                runHandler.postDelayed(healthDrainer, Values.NORMAL_HEALTH_DRAIN_DELAY.getValue());
            }
            // drain the health of the Pet by a little bit before repeating
            updateHealth(-1);
        }
    };

    /** Start draining the Pet's health. */
    void startHealthDrain() {
        this.healthDrainer.run();
    }

    /** Stop draining the Pet's health. */
    void stopHealthDrain() {
        this.runHandler.removeCallbacks(healthDrainer);
    }

}
