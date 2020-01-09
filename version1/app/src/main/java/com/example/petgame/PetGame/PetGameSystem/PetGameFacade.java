package com.example.petgame.PetGame.PetGameSystem;

import android.content.Context;
import android.os.SystemClock;

import com.example.petgame.Pet.Pet;
import com.example.petgame.PetGame.GameUpdateReceiver;

/** Accessible set of functions to access back-end processes of the PetGame. */
public class PetGameFacade implements HealthManager.HealthUpdateReceiver,
        EnergyManager.EnergyUpdateReceiver, PetInteractor.PetInteractionReceiver,
        AccountBalanceManager.BalanceUpdateReceiver, CandyManager.CandyUpdateReceiver {

    /** Time elapsed since last on-screen click event. */
    private long elapsedClickTime = 0;

    /** The receiver of game updates from this PetGameFacade. */
    private GameUpdateReceiver receiver;

    /** Setter for the game updates receiver. */
    public void setReceiver(GameUpdateReceiver receiver) {
        this.receiver = receiver;
    }

    /** The managers of each component of the PetGame. */
    private HealthManager healthManager;
    private EnergyManager energyManager;
    private PetInteractor petInteractor;
    private AccountBalanceManager accountBalanceManager;
    private OptionsManager optionsManager;
    private CandyManager candyManager;

    /**
     * Create a new facade of the PetGame with the given managers.
     *
     * @param hM The manager of the PetGame's health.
     * @param eM The manager of the PetGame's energy.
     * @param pI The manager of the Pet and PetGame's interactions.
     * @param aBM The manager of the associated Account's balance.
     * @param oM The manager of the
     */
    PetGameFacade(HealthManager hM, EnergyManager eM, PetInteractor pI,
                         AccountBalanceManager aBM, OptionsManager oM, CandyManager cM) {
        this.healthManager = hM;
        this.energyManager = eM;
        this.petInteractor = pI;
        this.accountBalanceManager = aBM;
        this.optionsManager = oM;
        this.candyManager = cM;
    }


    // HealthManager functions

    /** Start draining the health of the Pet. */
    public void startHealthDrain() {
        this.healthManager.startHealthDrain();
    }

    /** Stop draining the health of the Pet. */
    public void stopHealthDrain() {
        this.healthManager.stopHealthDrain();
    }

    /**
     * If amount > 0, increase the Pet's health by that amount. Otherwise, if amount < 0, decrease
     * the Pet's health by that amount.
     *
     * @param amount The amount to change the Pet's health by.
     */
    public void updateHealth(int amount) {
        this.healthManager.updateHealth(amount);
    }


    // EnergyManager functions

    /** Start recovering the energy of the Pet. */
    public void startEnergyRecovery() {
        this.energyManager.startEnergyRecovery();
    }

    /** Stop recovering the energy of the Pet. */
    public void stopEnergyRecovery() {
        this.energyManager.stopEnergyRecovery();
    }

    /**
     * If amount > 0, regenerate the Pet's energy by that amount. Otherwise, if amount < 0, drain
     * the Pet's energy by that amount.
     *
     * @param amount The amount to update the Pet's energy by.
     */
    public void updateEnergy(int amount) {
        this.energyManager.updateEnergy(amount);
    }


    // PetInteractor functions

    /** Return the Pet of this PetGameFacade's PetGame. */
    public Pet getPet() {
        return this.petInteractor.getPet();
    }

    /** Return the number of times the Pet has been clicked. */
    public int getClicks() {
        return this.petInteractor.getClicks();
    }

    /** Perform the Pet's interaction action. */
    public void performInteraction(Context context) {
        // double click prevention
        if (SystemClock.elapsedRealtime() - this.elapsedClickTime < 1000) {
            return;
        }
        this.elapsedClickTime = SystemClock.elapsedRealtime();

        this.petInteractor.performInteraction();
        this.optionsManager.playInteractSound(context);
    }


    // AccountBalanceManager functions

    /** Return the balance of the Account associated with the PetGame. */
    public int getBalance() {
        return this.accountBalanceManager.getBalance();
    }

    /**
     * Update the Account balance by the given amount. If the given amount is non-negative, we are
     * gaining money. Otherwise, if the given amount is negative, we are spending money. If balance
     * is insufficient for spending, the transaction does not go through.
     *
     * @param amount The amount of money to update to the Account balance.
     */
    public void updateBalance(int amount) {
        this.accountBalanceManager.updateBalance(amount);
    }


    // OptionsManager functions

    /** Toggle the hard mode option of the PetGame. */
    public void toggleHardMode() {
        this.optionsManager.toggleHardMode();
    }

    /** Toggle the muted option of the PetGame. */
    public void toggleMuted() {
        this.optionsManager.toggleMuted();
    }

    /** Returns true iff the PetGame is in hard mode. */
    public boolean inHardMode() {
        return this.optionsManager.inHardMode();
    }

    /** Returns true iff the PetGame is muted. */
    public boolean isMuted() {
        return this.optionsManager.isMuted();
    }

    /** Play the PetGame's music from this given Context. */
    public void playMusic(Context context) {
        this.optionsManager.playMusic(context);
    }

    /** Terminate the PetGame's music. */
    public void stopMusic() {
        this.optionsManager.stopMusic();
    }


    // CandyManager funtions

    /** Return the number of candies remaining in the PetGame. */
    public int getCandies() {
        return this.candyManager.getCandies();
    }

    /** Add a piece of candy to the Account. */
    public void addCandy() {
        this.candyManager.addCandy();
    }

    /** Begin randomly showing pieces of candy. */
    public void startCandyCollection() {
        this.candyManager.startCandyCollection();
    }

    /** Stop randomly showing pieces of candy. */
    public void stopCandyCollection() {
        this.candyManager.stopCandyCollection();
    }

    /**
     * If there are candies available, use up one piece.
     *
     * @return true iff a piece of candy was successfully used.
     */
    public boolean useCandy() {
        return this.candyManager.useCandy();
    }


    // implementation of HealthManager.HealthUpdateReceiver

    @Override
    public void displayHealth() {
        this.receiver.displayHealth();
    }

    // implementation of EnergyManager.EnergyUpdateReceiver

    @Override
    public void displayEnergy() {
        this.receiver.displayEnergy();
    }

    // implementation of AccountBalanceManager.BalanceUpdateReceiver

    @Override
    public void displayBalance() {
        this.receiver.displayBalance();
    }

    // implementation of PetInteractor.PetInteractionReceiver

    @Override
    public void doInteractAction() {
        this.receiver.doInteractAction();
    }

    @Override
    public void doPassiveAction() {
        this.receiver.doPassiveAction();
    }

    @Override
    public void displayCandy() {
        this.receiver.displayCandy();
    }

    @Override
    public void showCollectCandy() {
        this.receiver.showCollectCandy();
    }

    @Override
    public void hideCollectCandy() {
        this.receiver.hideCollectCandy();
    }

}
