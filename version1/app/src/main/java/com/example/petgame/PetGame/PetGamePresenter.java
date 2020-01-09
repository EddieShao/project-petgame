package com.example.petgame.PetGame;

import android.content.Context;

import com.example.petgame.Pet.Pet;

/** Presents data updates from the PetGameFacade to the PetGameView. */
public class PetGamePresenter implements PetGameInteractionReceiver {

    /** The PetGameView this PetGamePresenter is presenting data to. */
    private PetGameView view;

    /** The interactor this PetGamePresenter is presenting information from. */
    private PetGameInteractor interactor;

    /**
     * Create a new PetGamePresenter with connections to given PetGameView and PetGameInteractor.
     *
     * @param view The PetGameView to display information to.
     * @param interactor The interactor performing PetGame functions for this PetGamePresenter.
     */
    PetGamePresenter(PetGameView view, PetGameInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    /** Ask the interactor to return the Pet in the current PetGame. */
    Pet getPet() {
        return this.interactor.getPet();
    }

    /** Ask the interactor to stop the background music. */
    void stopMusic() {
        this.interactor.stopMusic();
    }

    /** Ask the interactor to play background music from the given Context. */
    void playMusic(Context context) {
        this.interactor.playMusic(context);
    }

    /**
     * Ask the Interactor to either use a free-train candy or pay energy to play the TrainingGame.
     * Return true iff the transaction was successful.
     *
     * @return true iff payment was completed and energy transfer goes through.
     */
    boolean payForTraining() {
        return this.interactor.payForTraining();
    }

    /**
     * Ask the Interactor to update the Pet's energy and Account's balance with the given amounts.
     *
     * @param energy The updated energy of the Pet
     * @param money The updated balance of the Account.
     */
    void updateFromShop(int energy, int money) {
        this.interactor.updateFromShop(energy, money);
    }

    /** Ask the interactor to update the balance by the given amount. */
    void updateBalance(int moneyGained) {
        this.interactor.updateBalance(moneyGained);
    }

    /** Ask the interactor to update the Pet's health by the given amount. */
    void updateHealth(int healthGained) {
        this.interactor.updateHealth(healthGained);
    }

    /** Ask the interactor to perform the Pet's interaction event. */
    void performInteraction(Context context) {
        this.interactor.performInteraction(context);
    }

    /** Ask the interactor to return the Account balance. */
    int getBalance() {
        return this.interactor.getBalance();
    }

    /** Ask the interactor to start the PetGame's health drain and energy recovery. */
    void startHealthEnergyIncrementation() {
        this.interactor.startHealthEnergyIncrementation();
    }

    /** Ask the interactor to stop the PetGame's health drain and energy recovery. */
    void stopHealthEnergyIncrementation() {
        this.interactor.stopHealthEnergyIncrementation();
    }

    /** Return the number of candies left in the Account. */
    int getCandies() {
        return this.interactor.getCandies();
    }

    /** Ask the interactor to add a piece of candy to the Account. */
    void addCandy() {
        this.interactor.addCandy();
    }

    /** Begin randomly showing pieces of candy. */
    void startCandyCollection() {
        this.interactor.startCandyCollection();
    }

    /** Stop randomly showing pieces of candy. */
    void stopCandyCollection() {
        this.interactor.stopCandyCollection();
    }

    /** Ask the interactor to return the Pet position. */
    int getPetPosition() {
        return this.interactor.getPET_POSITION();
    }

    /** Ask the interactor to collect the egg with the given name key. */
    void collectEgg(String name) {
        this.interactor.collectEgg(name);
    }

    /** Ask the interactor to return the number of times the Pet has been clicked. */
    int getClicks() {
        return this.interactor.getClicks();
    }

    // implementation of PetGameInteractionReceiver

    @Override
    public void displayHealth() {
        this.view.displayHealth();
    }

    @Override
    public void displayEnergy() {
        this.view.displayEnergy();
    }

    @Override
    public void displayBalance() {
        this.view.displayBalance();
    }

    @Override
    public void doInteractAction() {
        this.view.doInteractAction();
    }

    @Override
    public void doPassiveAction() {
        this.view.doPassiveAction();
    }

    @Override
    public void displayCandy() {
        this.view.displayCandy();
    }

    @Override
    public void displayBackground(int backgroundID) {
        this.view.displayBackground(backgroundID);
    }

    @Override
    public void showCollectCandy() {
        this.view.showCollectCandy();
    }

    @Override
    public void hideCollectCandy() {
        this.view.hideCollectCandy();
    }

}
