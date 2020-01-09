package com.example.petgame.Shop;

/** Functionality of the front-end of a shop system for the PetGame. */
public interface ShopView {

    /** Display the total energy to be gained when returning to the PetGame.
     * (to be called when this ShopView starts and whenever an item in purchased). */
    void displayEnergy();

    /** Display the money left in the Account.
     * (to be called when this ShopView starts and whenever an item is purchased). */
    void displayMoney();

    /** Return to the PetGameActivity that invoked this ShopView. */
    void returnToPetGame();

    /** Display the items for sale in this ShopView. */
    void displayItems();

    /**
     * Purchase the selected Food item.
     *
     * @param item The Food item to purchase.
     */
    void purchaseItem(Food item);

}
