package com.example.petgame.PetGame.PetGameSystem;

/** Manages the Account associated with the PetGame's balance. */
class AccountBalanceManager {

    /** Interface to receive Account balance updates from the AccountBalanceManager. */
    interface BalanceUpdateReceiver {

        /** Update the balance held in this BalanceUpdateReceiver. */
        void displayBalance();

    }

    /** The PetGame this AccountBalanceManager is managing money for. */
    private PetGame petGame;

    /** The receiver of Account balance updates from this AccountBalanceManager. */
    private BalanceUpdateReceiver receiver;

    /** Setter for receiver. */
    void setReceiver(BalanceUpdateReceiver receiver) {
        this.receiver = receiver;
    }

    /** Getter for balance. */
    int getBalance() {
        return this.petGame.getBalance();
    }

    /**
     * Create a new AccountBalanceManager for the given PetGame.
     *
     * @param game The PetGame to manage Account balance for.
     */
    AccountBalanceManager(PetGame game) {
        this.petGame = game;
    }

    /**
     * Update the Account balance by the given amount. If the given amount is non-negative, we are
     * gaining money. Otherwise, if the given amount is negative, we are spending money. If balance
     * is insufficient for spending, the transaction does not go through.
     *
     * @param amount The amount of money to update to the Account balance.
     */
    void updateBalance(int amount) {
        if (amount >= 0 || this.petGame.getBalance() >= -amount) {
            // either we are earning money OR we have enough money to spend
            this.petGame.updateBalance(amount);  // update with the given amount of money
            this.receiver.displayBalance();
        }

        // otherwise, we are spending money, but don't have enough money to spend
        // therefore, transaction does not go through
    }

}
