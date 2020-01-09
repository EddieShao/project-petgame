package com.example.petgame.Shop;

/** The back-end processes of the shop system. */
class ShopInteractor {

    /** Observer of this ShopInteractor's processes. */
    interface PurchaseReceiver {

        /** Notify this PurchaseReceiver that a transaction has been processed. */
        void notifyPurchase();

    }

    /** The money available from the Account. */
    private int money;

    /** Getter for money. */
    int getMoney() {
        return money;
    }

    /** The current energy of the Pet (to be updated when exiting the shop). */
    private int energy;

    /** Getter for energy. */
    int getEnergy() {
        return this.energy;
    }

    /**
     * Create a new ShopInteractor with the given balance and Pet energy.
     *
     * @param money The current balance of the Account.
     * @param energy The current energy level of the Pet.
     */
    ShopInteractor(int money, int energy) {
        this.money = money;
        this.energy = energy;
    }

    /**
     * Make the transaction to Purchase the given Food item. Update energy and balance afterwards.
     * If funds are insufficient, do not make the purchase.
     *
     * @param item The Food item to attempt a purchase on.
     */
    void purchaseItem(Food item, PurchaseReceiver presenter) {
        // obtain the energy and cost of the Food item.
        int energy = item.getFood()[0];
        int cost = item.getFood()[1];

        if (this.money >= cost && this.energy < 100) {
            // we have enough money and the Pet is missing some energy, make the transaction
            this.money -= cost;
            this.energy += energy;

            // level off the energy at max
            if (this.energy > 100) {
                this.energy = 100;
            }

            // log that purchase was successful
            System.out.println(" -- purchase successful: energy and money updated");
            // a transaction has been made, tell the front-end about it
            presenter.notifyPurchase();
        } else {
          // log that purchase was not successful
          System.out.println(" XX purchase not successful: insufficient balance OR energy is full");
        }
    }

}
