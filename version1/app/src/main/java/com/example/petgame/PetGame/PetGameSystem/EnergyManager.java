package com.example.petgame.PetGame.PetGameSystem;

import android.os.Handler;

/** Manages energy for the PetGame. */
class EnergyManager {

    /** Interface for receiving energy updates from the EnergyManager. */
    interface EnergyUpdateReceiver {

        /** Update the energy of this EnergyUpdateReceiver. */
        void displayEnergy();

    }

    /** The PetGame this EnergyManager is managing energy for. */
    private PetGame petGame;

    /** The receiver of any updates this HealthManager carries out. */
    private EnergyUpdateReceiver receiver;

    /** Setter for the energy update receiver. */
    void setReceiver(EnergyUpdateReceiver receiver) {
        this.receiver = receiver;
    }

    /** The run handler in charge of running energy recovery repeating tasks. */
    private Handler runHandler;

    /**
     * Create a new EnergyManager for the given PetGame.
     *
     * @param petGame The PetGame to manage energy for.
     */
    EnergyManager(PetGame petGame) {
        this.petGame = petGame;
        this.runHandler = new Handler();
    }

    /**
     * If amount > 0, regenerate the Pet's energy by that amount. Otherwise, if amount < 0, drain
     * the Pet's energy by that amount.
     *
     * @param amount The amount to update the Pet's energy by.
     */
    void updateEnergy(int amount) {
        int initEnergy = this.petGame.getPet().getEnergy(); // initial energy for comparison
        this.petGame.getPet().updateEnergy(amount);

        if (initEnergy != this.petGame.getPet().getEnergy()) {
            // energy has changed, notify receiver
//            this.petGame.performNotifyObservers();
            this.receiver.displayEnergy();
        }
    }

    /** Recovers the energy of the Pet on call to run. */
    private Runnable energyRecoverer = new Runnable() {
        /** Run the following processes on a repeating task. */
        @Override
        public void run() {
            // delay by some time depending on difficulty settings
            if (petGame.inHardMode()) {
                runHandler.postDelayed(energyRecoverer, Values.HARD_ENERGY_RECOVERY_DELAY.getValue());
            } else {
                runHandler.postDelayed(energyRecoverer, Values.NORMAL_ENERGY_RECOVERY_DELAY.getValue());
            }
            // recover a bit of the Pet's energy before repeating
            updateEnergy(1);
        }
    };

    /** Start recovering the Pet's energy. */
    void startEnergyRecovery() {
        this.energyRecoverer.run();
    }

    /** Stop recovering the Pet's energy. */
    void stopEnergyRecovery() {
        runHandler.removeCallbacks(energyRecoverer);
    }

}
