package com.example.petgame.Shop;

/** Presents information for the ShopActivity to display. */
public class ShopPresenter implements ShopInteractor.PurchaseReceiver {

    /** The ShopView to display data onto. */
    private ShopView shopView;

    /** The ShopInteractor that will perform purchase requests from this ShopPresenter. */
    private ShopInteractor shopInteractor;

    /** Getter for shopInteractor. */
    ShopInteractor getShopInteractor() {
        return this.shopInteractor;
    }

    /**
     * Create a new ShopPresenter
     *
     * @param parent The parent that invoked this ShopPresenter
     * @param interactor The ShopInteractor this ShopPresenter will deal with.
     */
    ShopPresenter(ShopView parent, ShopInteractor interactor) {
        this.shopView = parent;
        this.shopInteractor = interactor;
    }

    /** Pass the transaction request to the ShopInteractor. */
    void purchaseItem(Food item) {
        this.shopInteractor.purchaseItem(item, this);
    }

    @Override
    public void notifyPurchase() {
        // update the energy and money remaining
        this.shopView.displayEnergy();
        this.shopView.displayMoney();
    }
}
