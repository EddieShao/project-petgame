package com.example.petgame.PetGame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.petgame.MemoryTile.MemoryStartActivity;
import com.example.petgame.Pet.Pet;
import com.example.petgame.PetGame.PetGameSystem.PetGameDirector;
import com.example.petgame.PetGame.PetGameSystem.PetGameFacade;
import com.example.petgame.PetGameSettings.PetGameSettingsPopup;
import com.example.petgame.PetGameSettings.SettingsContainer;
import com.example.petgame.R;
import com.example.petgame.SelectPet.*;
import com.example.petgame.Shop.ShopActivity;
import com.example.petgame.Training.TrainingMainActivity;

/** The value constants of request codes for data transfer between Activities. */
enum RequestCodes {
    /** The ints instantiated to their given request code enums */
    PLAY_TRAINING_GAME_REQUEST(1),
    PLAY_MEMORY_TILE_GAME_REQUEST(2),
    OPEN_SHOP_REQUEST(3);

    /** The value of the RequestCodes. */
    private int requestCode;

    /** Getter for requestCode. */
    public int getCode() {
        return this.requestCode;
    }

    /** Private constructor for RequestCodes. */
    RequestCodes(int num) {
        this.requestCode = num;
    }
}

/** The on-screen activity of PetGame. */
public class PetGameActivity extends AppCompatActivity implements SettingsContainer, PetGameView {

    /** The presenter giving data inputs to display. */
    private PetGamePresenter presenter;

    /** The settings pop up window. */
    private PetGameSettingsPopup settingsPopup;

    /** The data Views of this PetGameActivity. */
    private TextView petName;
    private TextView petHealth;
    private TextView petEnergy;
    private TextView accountMoney;
    private TextView petAge;
    private ImageView pet;
    private ImageView candy;
    private TextView numCandies;
    private TextView easterEggMessage;
    private ImageView collectCandy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_pet_game);

        // obtain parceled information from Intent
        Intent intent = getIntent();
        Pet pet = intent.getParcelableExtra(PetAdapter.SELECT_PET);  // Pet
        int position = intent.getIntExtra(PetAdapter.PET_POSITION, -1);  // position
        int money = intent.getIntExtra(PetAdapter.ACCOUNT_MONEY, -1);  // money
        int candies = intent.getIntExtra(PetAdapter.CANDIES, -1);  // candies

        // create a PetGameBuilder to build and set up the PetGame
        // note: this starts playing the music too
        if (pet != null) {
            PetGameFacade game = PetGameDirector.buildPetGame(pet, money, candies);
            PetGameInteractor interactor = new PetGameInteractor(game, position, this);
            this.presenter = new PetGamePresenter(this, interactor);
            interactor.setReceiver(this.presenter);

            // play the music!
            this.presenter.playMusic(this);

            // create a new settings pop up window (this also sets up the settings button)
            // NOTE: we must create this AFTER playing the music, as we may pause music on creaton
            this.settingsPopup = new PetGameSettingsPopup(this, interactor);
        }

        // set all initial Views
        this.petName = findViewById(R.id.pet_name);
        this.petHealth = findViewById(R.id.pet_health);
        this.petEnergy = findViewById(R.id.pet_energy);
        this.accountMoney = findViewById(R.id.account_money);
        this.petAge = findViewById(R.id.pet_age);
        this.pet = findViewById(R.id.pet);
        this.numCandies = findViewById(R.id.num_candy);
        this.candy = findViewById(R.id.candy);
        this.easterEggMessage = findViewById(R.id.collect_egg_message);
        this.collectCandy = findViewById(R.id.collect_candy);

        // set up all remaining interactive Views
        this.findViewById(R.id.open_shop).setOnClickListener(view -> this.openShop());
        this.findViewById(R.id.back_to_account).setOnClickListener(view -> this.returnToAccount());
        this.findViewById(R.id.play_training_game).setOnClickListener(view -> this.playTrainingGame());
        this.findViewById(R.id.play_memory_tile_game).setOnClickListener(view -> this.playMemoryTileGame());
        this.pet.setOnClickListener(view -> this.performInteraction());
        this.collectCandy.setOnClickListener(view -> this.presenter.addCandy());
        this.findViewById(R.id.candy).setOnClickListener(view -> this.collectEgg());

        // display all data about the PetGame and set up all interactions
        this.displayName();
        this.displayHealth();
        this.displayEnergy();
        this.displayBalance();
        this.displayAge();
        this.doPassiveAction();
        this.displayCandy();
        this.easterEggMessage.setVisibility(View.INVISIBLE);

        // start repeating task of draining health and recovering energy
        this.presenter.startHealthEnergyIncrementation();
        this.presenter.startCandyCollection();
    }

    /** Collect the easter egg corresponding to the given key. */
    private void collectEgg() {
        this.presenter.collectEgg("Pet1");
        // display notification
        this.easterEggMessage.setVisibility(View.VISIBLE);
        this.easterEggMessage.postDelayed(() -> easterEggMessage.setVisibility(View.INVISIBLE), 1000);
    }

    /** Run processes right before termination of this PetGameActivity. */
    @Override
    public void onDestroy() {
        // stop incrementing health/energy of the Pet
        this.presenter.stopHealthEnergyIncrementation();
        this.presenter.stopCandyCollection();
        System.out.println(" *** Terminating Activity: PetGameActivity...");
        super.onDestroy();
    }

    @Override
    public void onPause() {
        // stop incrementing health/energy of the Pet
        this.presenter.stopHealthEnergyIncrementation();
        this.presenter.stopCandyCollection();
        super.onPause();
    }

    /** Go back to the SelectPetActivity that started this Activity. */
    public void returnToAccount() {
        // create the intent to return to the pet selection page
        Intent backToAccount = new Intent(this, SelectPetActivity.class);
        // parcel the pet, money, and position into it
        backToAccount.putExtra(Keys.PET.getKey(), this.presenter.getPet());
        backToAccount.putExtra(Keys.MONEY.getKey(), this.presenter.getBalance());
        backToAccount.putExtra(Keys.POSITION.getKey(), this.presenter.getPetPosition());
        backToAccount.putExtra(Keys.NUM_CANDY.getKey(), this.presenter.getCandies());

        // stop the music :(
        this.presenter.stopMusic();
        // set the status of this activity as okay to send back to Account as a result
        this.setResult(Activity.RESULT_OK, backToAccount);
        // finish this activity (perform intent to go back to Account)
        this.finish();
    }

    /** If the Pet in this PetGame has enough energy, play the TrainingGame. */
    public void playTrainingGame() {
        if (this.presenter.payForTraining()) {
            // pause health drain and energy recovery
            this.presenter.stopHealthEnergyIncrementation();
            this.presenter.stopCandyCollection();
            // create intent to start TrainingGame
            Intent startTraining = new Intent(this, TrainingMainActivity.class);
            // put Pet class type into intent
            startTraining.putExtra(Keys.PET_TYPE.getKey(), this.presenter.getPet().getClass().getSimpleName());
            // start a training game with expectation for return
            this.startActivityForResult(startTraining, RequestCodes.PLAY_TRAINING_GAME_REQUEST.getCode());
        }
    }

    public void playMemoryTileGame() {
        // pause health drain and energy recovery
        this.presenter.stopHealthEnergyIncrementation();
        this.presenter.stopCandyCollection();
        // start a memory tile game with expectation for return
        this.startActivityForResult(new Intent(this, MemoryStartActivity.class),
                                    RequestCodes.PLAY_MEMORY_TILE_GAME_REQUEST.getCode());
    }

    public void openShop() {
        Intent goToShop = new Intent(this, ShopActivity.class);
        // pack the Account money and Pet energy to send
        goToShop.putExtra(Keys.ENERGY.getKey(), this.presenter.getPet().getEnergy());
        goToShop.putExtra(Keys.MONEY.getKey(), this.presenter.getBalance());
        // pause health drain and energy recovery
        this.presenter.stopHealthEnergyIncrementation();
        this.presenter.stopCandyCollection();
        // start activity with expectation for return
        this.startActivityForResult(goToShop, RequestCodes.OPEN_SHOP_REQUEST.getCode());
    }

    /**
     * Upon returning to this activity from startActivityForResult, update any corresponding data.
     *
     * @param requestCode The request code of the intent.
     * @param resultCode The success status of the Activity that initiated the given Intent.
     * @param resultIntent The actual Intent containing the returned data.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RequestCodes.PLAY_TRAINING_GAME_REQUEST.getCode()) {
                // this is the TrainingGame callback

                // extract the health gained from the Intent
                int healthGained = resultIntent.getIntExtra(TrainingMainActivity.HEALTH_REWARD,-1);
                // update health if extraction successful
                this.presenter.updateHealth(healthGained);
            } else if (requestCode == RequestCodes.PLAY_MEMORY_TILE_GAME_REQUEST.getCode()) {
                // this is the MemoryTileGame callback

                // extract the money from the Intent
                int moneyGained = resultIntent.getIntExtra(MemoryStartActivity.MONEY, -1);
                // update the balance
                this.presenter.updateBalance(moneyGained);
            } else if (requestCode == RequestCodes.OPEN_SHOP_REQUEST.getCode()) {
                // this is the ShopActivity callback

                // un-parcel the money and energy
                int energy = resultIntent.getIntExtra(Keys.ENERGY.getKey(), -1);
                int money = resultIntent.getIntExtra(Keys.MONEY.getKey(), -1);
                // update the energy and balance
                this.presenter.updateFromShop(energy, money);
            }
        }

        // resume health drain and energy recovery
        this.presenter.startHealthEnergyIncrementation();
        this.presenter.startCandyCollection();

        // update the age of the Pet
        // we do this to make sure the displayed pet age is relatively up to date
        // there's no proper place to call this, so I just call it whenever we come back here
        this.displayAge();
    }

    /** Display the name of the Pet in this PetGame. */
    private void displayName() {
        this.petName.setText(this.presenter.getPet().getName());
    }

    /** Display the age of the Pet in days. */
    private void displayAge() {
        int ageInDays = this.presenter.getPet().getAge().getDays();
        this.petAge.setText(String.valueOf(ageInDays));
    }

    /** Perform the Pet's interaction action. */
    private void performInteraction() {
        this.presenter.performInteraction(this);

        if (this.presenter.getClicks() == 20) {
            this.easterEggMessage.setVisibility(View.VISIBLE);
            this.easterEggMessage.postDelayed(() -> easterEggMessage.setVisibility(View.INVISIBLE), 1000);
        }
    }

    // implementation of SettingsContainer

    @Override
    public LayoutInflater getInflater() {
        return this.getLayoutInflater();
    }

    @Override
    public void setUpShowSettings() {
        this.findViewById(R.id.settings).setOnClickListener(view -> {
            this.presenter.stopHealthEnergyIncrementation();
            this.presenter.stopCandyCollection();
            this.settingsPopup.showSettings();
        });
    }

    @Override
    public void onDismissSettings() {
        this.presenter.startHealthEnergyIncrementation();
        this.presenter.startCandyCollection();
    }

    @Override
    public Context getSelfAsContext() {
        return this;
    }

    // implementation of PetGameView

    @Override
    public void displayHealth() {
        if (!this.presenter.getPet().isAlive()) {
            // the Pet is dead, return to the account page
            this.returnToAccount();
        } else {
            // the Pet is still alive, update display and continue the game
            this.petHealth.setText(String.valueOf(this.presenter.getPet().getHealth()));
        }
    }

    @Override
    public void displayEnergy() {
        this.petEnergy.setText(String.valueOf(this.presenter.getPet().getEnergy()));
    }

    @Override
    public void displayBalance() {
        String balance = "$" + this.presenter.getBalance();
        this.accountMoney.setText(balance);
    }

    @Override
    public void doInteractAction() {
        // set the interaction action version of this Pet
        this.pet.setImageDrawable(this.getDrawable(this.presenter.getPet().getDrawableID(false, true)));
    }

    @Override
    public void doPassiveAction() {
        // set the passive action version of the Pet
        this.pet.setImageDrawable(this.getDrawable(this.presenter.getPet().getDrawableID(true, true))
        );
    }

    @Override
    public void displayBackground(int backgroundID) {
        View background = this.findViewById(R.id.background);
        background.setBackground(this.getDrawable(backgroundID));
    }

    /** Display the number of candies available and the correct candy image depending on the number
     * of candies. If there are no candies available, set the gray-scaled version. Otherwise, set
     * the colourful version. */
    @Override
    public void displayCandy() {
        // display the image
        if (this.presenter.getCandies() == 0) {
            this.candy.setImageDrawable(getDrawable(R.drawable.candy_gray));
        } else {
            this.candy.setImageDrawable(getDrawable(R.drawable.candy));
        }

        // display the number of candies
        this.numCandies.setText(String.valueOf(this.presenter.getCandies()));
    }

    @Override
    public void showCollectCandy() {
        this.collectCandy.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCollectCandy() {
        this.collectCandy.setVisibility(View.INVISIBLE);
    }

}
