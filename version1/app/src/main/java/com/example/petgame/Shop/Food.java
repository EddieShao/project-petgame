package com.example.petgame.Shop;

/** Keys for the food selection available in the ShopInteractor/ */
public enum Food {

    CHICKEN(80, 40),
    PIZZA(60, 30),
    DONUT(20, 10);

    /** The [energy cost] value of this Food item. */
    private int[] food;

    /** Getter for food. */
    int[] getFood() {
        return this.food;
    }

    Food(int energy, int cost) {
        this.food = new int[]{energy, cost};
    }

}
