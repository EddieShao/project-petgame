package com.example.petgame.PetGame;

import android.content.Context;

import com.example.petgame.Account.AccountFileManagerFactory;
import com.example.petgame.Account.EasterEggCollector;
import com.example.petgame.Pet.Pet;
import com.example.petgame.PetGame.PetGameSystem.PetGameFacade;
import com.example.petgame.PetGame.PetGameSystem.Values;
import com.example.petgame.PetGameSettings.SettingsReceiver;
import com.example.petgame.R;

/** Interacts with the pet game system to deliver game updates to the UI. */
class PetGameInteractor implements SettingsReceiver, GameUpdateReceiver {

    /** The PetGameFacade this PetGameInteractor uses to perform PetGame interactions. */
    private PetGameFacade petGame;

    /** The receiver up game updates from this PetGameInteractor. */
    private PetGameInteractionReceiver receiver;

    /** The manager of collecting and storing easter eggs found within the PetGame. */
    private EasterEggCollector eggCollector;

    /** Setter for receiver. */
    void setReceiver(PetGameInteractionReceiver receiver) {
        this.receiver = receiver;
    }

    /** The position index of the Pet in the PetGame for reference in external storage. */
    private final int PET_POSITION;

    /** Getter for PET_POSITION. */
    int getPET_POSITION() {
        return this.PET_POSITION;
    }

    /**
     * Create a new PetGameInteractor for the given PetGameFacade. Also stores the position of the
     * Pet in the SelectPetActivity for retrieval later.
     *
     * @param game The PetGameFacade this PetGameInteractor will interact with.
     * @param position The position of the Pet in the SelectPetActivity.
     */
    PetGameInteractor(PetGameFacade game, int position, Context context) {
        this.petGame = game;
        this.petGame.setReceiver(this);
        this.PET_POSITION = position;

        AccountFileManagerFactory managerFactory = new AccountFileManagerFactory();
        this.eggCollector = managerFactory.buildEasterEggCollector(context, R.string.DATAFILE);
    }

    /** Return the Pet currently in the PetGame. */
    Pet getPet() {
        return this.petGame.getPet();
    }

    /** Return the Account's balance. */
    int getBalance() {
        return this.petGame.getBalance();
    }

    /** Play the background music for this PetGame. */
    void playMusic(Context context) {
        this.petGame.playMusic(context);
    }

    /** Stop the background music for this PetGame. */
    void stopMusic() {
        this.petGame.stopMusic();
    }

    /** Start the PetGame's health drain and energy recovery. */
    void startHealthEnergyIncrementation() {
        this.petGame.startHealthDrain();
        this.petGame.startEnergyRecovery();
    }

    /** Stop the PetGame's health drain and energy recovery. */
    void stopHealthEnergyIncrementation() {
        this.petGame.stopHealthDrain();
        this.petGame.stopEnergyRecovery();
    }

    /**
     * Attempt to spend a free-train candy or some of the Pet's energy to play the TrainingGame.
     * Return true iff the payment was successful.
     *
     * @return true iff transaction of candy or Pet's energy was successful.
     */
    boolean payForTraining() {
        if (!this.petGame.useCandy()) {
            if (this.petGame.getPet().getEnergy() < Values.TRAINING_COST.getValue()) {
                return false;
            } else {
                this.petGame.updateEnergy(-Values.TRAINING_COST.getValue());
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * Update the Pet's energy and Account's balance with the given amounts.
     *
     * @param energy The updated energy of the Pet
     * @param money The updated balance of the Account.
     */
    void updateFromShop(int energy, int money) {
        this.petGame.updateEnergy(energy - this.petGame.getPet().getEnergy());
        this.petGame.updateBalance(money - this.petGame.getBalance());
    }

    /**
     * Update the Account balance by the given amount. If moneyGained > 0, we increase the balance.
     * Otherwise, if moneyGained < 0, we spend money.
     *
     * @param moneyGained The amount of money to spend or give to the Account balance.
     */
    void updateBalance(int moneyGained) {
        this.petGame.updateBalance(moneyGained);
    }

    /**
     * Update the health of the Pet by the given amount. If healthGained > 0, we increase health.
     * Otherwise, if healthGained < 0, we lose health.
     *
     * @param healthGained The amount of health to increase/decrease the Pet's health by.
     */
    void updateHealth(int healthGained) {
        this.petGame.updateHealth(healthGained);
    }

    /** Perform this Pet's interaction event from the given Context. */
    void performInteraction(Context context) {
        this.petGame.performInteraction(context);
        // if the Pet has been clicked 20 times, collect the egg!
        if (this.getClicks() == 20) {
            this.collectEgg("Pet2");
        }
    }

    /** Return the number of candies left in the Account. */
    int getCandies() {
        return this.petGame.getCandies();
    }

    /** Add a piece of candy to the Account. */
    void addCandy() {
        this.petGame.addCandy();
    }

    /** Begin randomly showing pieces of candy. */
    void startCandyCollection() {
        this.petGame.startCandyCollection();
    }

    /** Stop randomly showing pieces of candy. */
    void stopCandyCollection() {
        this.petGame.stopCandyCollection();
    }

    /** Collect the easter egg with the given name key. */
    void collectEgg(String name) {
        this.eggCollector.addEasterEggPieceAndSave(name);
    }

    /** Return the number of times the Pet has been clicked. */
    int getClicks() {
        return this.petGame.getClicks();
    }


    // implementation of SettingsReceiver

    @Override
    public void toggleHardMode() {
        this.petGame.toggleHardMode();
    }

    @Override
    public void toggleMuted() {
        this.petGame.toggleMuted();
    }

    @Override
    public boolean inHardMode() {
        return this.petGame.inHardMode();
    }

    @Override
    public boolean isMuted() {
        return this.petGame.isMuted();
    }

    @Override
    public void displayBackground(int backgroundID) {
        this.receiver.displayBackground(backgroundID);
    }


    // implementation of GameUpdateReceiver

    @Override
    public void displayHealth() {
        this.receiver.displayHealth();
    }

    @Override
    public void displayEnergy() {
        this.receiver.displayEnergy();
    }

    @Override
    public void displayBalance() {
        this.receiver.displayBalance();
    }

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
