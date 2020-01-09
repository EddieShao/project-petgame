package com.example.petgame.Shop;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.petgame.PetGame.Keys;
import com.example.petgame.PetGame.PetGameActivity;
import com.example.petgame.R;

/** The UI for the shop system. Can only be started by the PetGameActivity. */
public class ShopActivity extends AppCompatActivity implements ShopView {

    /** The ShopPresenter presenting data for this ShopActivity. */
    private ShopPresenter shopPresenter;

    /** The tentative Views of this ShopActivity. */
    private TextView energy;
    private TextView money;

    /** The Views of the item selection in the shop system. */
    private TextView chicken;
    private TextView pizza;
    private TextView donut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_shop);

        // obtain the Pet's energy and Account's money from the Intent
        Intent out = getIntent();
        int money = out.getIntExtra(Keys.MONEY.getKey(), -1);
        int energy = out.getIntExtra(Keys.ENERGY.getKey(), -1);

        // set up the Views of this ShopActivity relating to the shop
        this.energy = this.findViewById(R.id.energy);
        this.money = this.findViewById(R.id.money);
        this.chicken = this.findViewById(R.id.chicken_data);
        this.pizza = this.findViewById(R.id.pizza_data);
        this.donut = this.findViewById(R.id.donut_data);
        this.findViewById(R.id.back).setOnClickListener(view -> returnToPetGame());
        this.findViewById(R.id.buy_chicken).setOnClickListener(view -> purchaseItem(Food.CHICKEN));
        this.findViewById(R.id.buy_pizza).setOnClickListener(view -> purchaseItem(Food.PIZZA));
        this.findViewById(R.id.buy_donut).setOnClickListener(view -> purchaseItem(Food.DONUT));

        // create a new ShopPresenter for this ShopActivity with the money and energy given,
        // then initially display all data
        this.shopPresenter = new ShopPresenter(this, new ShopInteractor(money, energy));
        this.displayEnergy();
        this.displayMoney();
        this.displayItems();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // implementation of ShopView

    @Override
    public void displayEnergy() {
        String energyText = "E: " + shopPresenter.getShopInteractor().getEnergy();
        this.energy.setText(energyText);
    }

    @Override
    public void displayMoney() {
        String moneyText = "$" + shopPresenter.getShopInteractor().getMoney();
        this.money.setText(moneyText);
    }

    @Override
    public void returnToPetGame() {
        Intent backToPetGame = new Intent(this, PetGameActivity.class);
        // pack the energy and money for return
        backToPetGame.putExtra(Keys.MONEY.getKey(), shopPresenter.getShopInteractor().getMoney());
        backToPetGame.putExtra(Keys.ENERGY.getKey(), shopPresenter.getShopInteractor().getEnergy());
        // set the result as OK and finish
        this.setResult(Activity.RESULT_OK, backToPetGame);
        this.finish();
    }

    @Override
    public void displayItems() {
        String chickenText = "+" + Food.CHICKEN.getFood()[0] + "  :  -$" + Food.CHICKEN.getFood()[1];
        String pizzaText = "+" + Food.PIZZA.getFood()[0] + "  :  -$" + Food.PIZZA.getFood()[1];
        String donutText = "+" + Food.DONUT.getFood()[0] + "  :  -$" + Food.DONUT.getFood()[1];
        this.chicken.setText(chickenText);
        this.pizza.setText(pizzaText);
        this.donut.setText(donutText);
    }

    @Override
    public void purchaseItem(Food item) {
        shopPresenter.purchaseItem(item);
    }

}
