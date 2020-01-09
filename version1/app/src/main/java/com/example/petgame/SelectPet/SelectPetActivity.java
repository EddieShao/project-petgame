package com.example.petgame.SelectPet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petgame.Account.AccountFileManagerFactory;
import com.example.petgame.Account.AccountUpdater;
import com.example.petgame.CreatePet.CreatePetActivity;
import com.example.petgame.EditAccount.EditAccountActivity;
import com.example.petgame.MainActivity;
import com.example.petgame.PetGame.Keys;
import com.example.petgame.Pet.Pet;
import com.example.petgame.R;

import java.util.List;

/* Screen after login account.
 * Screen or the Selecting Pet Screen.*/
public class SelectPetActivity extends AppCompatActivity {

    /* Used to startActivityForResult.*/
    public static final int CREATE_NEW_PET = 4;
    public static final int MANAGE_ACCOUNT = 5;

    /* Indicates how the pets are sorted.*/
    private static final int RANDOM_SORT = 0;
    private static final int HEALTH_SORT = 1;
    private static final int AGE_SORT = 2;

    /* Manager that save and read file.*/
    private AccountUpdater manager;

    /* Widget on this screen.*/
    private RecyclerView allPetsView;
    private TextView name;
    private TextView money;
    private Button sort;
    private TextView firstPet;

    /* Indicate how the pets are sorted currently.*/
    private int sortStatus = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pet);

        manager = new AccountFileManagerFactory().buildAccountUpdater(this, getString(R.string.DATAFILE));

        name = findViewById(R.id.accountName);
        money = findViewById(R.id.accountMoney);
        sort = findViewById(R.id.sort);
        firstPet = findViewById(R.id.first_pet);

        sort.setText(R.string.random_sort);

        allPetsView = findViewById(R.id.allPetsView);

        updateView();

        if (!getIntent().getStringExtra(MainActivity.LOGIN).equals(manager.getUSERNAME())){
            // This should NEVER happen code-wisely.
            // Only happened if user re-login too fast after just logged out.
            // Or the user did not log out properly.
            Toast message = Toast.makeText(this, R.string.login_fail_message, Toast.LENGTH_SHORT);
            message.show();
            finish();
        } else {
            Toast message = Toast.makeText(this, R.string.success_login, Toast.LENGTH_LONG);
            message.show();
        }
    }

    /* Update the view on screen.*/
    void updateView(){
        sortPets();
        List<Pet> pets = manager.getAllPets();

        if (pets.isEmpty()){
            // Hide sorting option if there's no pet
            sort.setVisibility(View.INVISIBLE);
            // Remind user to create pet.
            firstPet.setVisibility(View.VISIBLE);
        } else {
            // Reveal the sort option.
            sort.setVisibility(View.VISIBLE);
            // Hide the reminder.
            firstPet.setVisibility(View.INVISIBLE);
        }

        // Creates the list of pets.
        PetAdapter petAdapter = new PetAdapter(pets, this);
        allPetsView.setAdapter(petAdapter);
        allPetsView.setLayoutManager(new LinearLayoutManager(this));

        String displayedName = getString(R.string.nickname_greeting);
        String displayedMoney = "$" + manager.getMoney();
        name.setText(String.format(displayedName, manager.getNickname()));
        money.setText(displayedMoney);
    }

    /* Sort the pet order based on the sortStatus.*/
    private void sortPets(){
        if (sortStatus == AGE_SORT){
            manager.sortPetsByAge();
            sort.setText(R.string.age_sort);
            manager.saveToFile();
        } else if (sortStatus == HEALTH_SORT){
            manager.sortPetsByHealth();
            sort.setText(R.string.health_sort);
            manager.saveToFile();
        }
    }


    @Override
    protected void onActivityResult(int aRequestCode, int aResultCode, Intent aData){
        if (aRequestCode == PetAdapter.PASS_PET_TO_PETGAME && aResultCode == Activity.RESULT_OK){

            //Update the manager to the most updated info in file.
            manager.readFile();

            System.out.println("***Return from PetGame***");

            // Update money after the user finished the game.
            int money = aData.getIntExtra(Keys.MONEY.getKey(), -1);
            manager.updateMoney(money);
            // Update the pet information after the user finished the game.
            Pet pet = aData.getParcelableExtra(Keys.PET.getKey());
            int position = aData.getIntExtra(Keys.POSITION.getKey(), -1);
            manager.updatePet(pet, position);

            int candies = aData.getIntExtra(Keys.NUM_CANDY.getKey(), -1);
            manager.updateCandies(candies);

            // Update every modified information to file and on screen.
            manager.saveToFile();
            updateView();

        } else if (aRequestCode == CREATE_NEW_PET && aResultCode == Activity.RESULT_OK){

            // Add the new pet information after the user created the pet.
            Pet pet = aData.getParcelableExtra(CreatePetActivity.NEW_PET);
            manager.addPet(pet);

            // Update every modified information to file and on screen.
            manager.saveToFile();
            updateView();

            String welcomeNewPet = String.format(getString(R.string.welcome_new_pet),pet.getName());
            Toast clickedMessage = Toast.makeText(this, welcomeNewPet, Toast.LENGTH_LONG);
            clickedMessage.show();

        } else if (aRequestCode == MANAGE_ACCOUNT){
            if (aResultCode == Activity.RESULT_OK){

                // Fetch the changed nickname and password from EditAccountActivity
                String newPassword = aData.getStringExtra(EditAccountActivity.NEW_PASSWORD);
                String newNickname = aData.getStringExtra(EditAccountActivity.NEW_NICKNAME);

                // Changed the modified information
                if (newNickname != null){
                    manager.changeNickname(newNickname);
                    if (newPassword != null){
                        manager.changePassword(newPassword);
                    }
                }

                // Update every modified information to file and on screen.
                updateView();
                manager.saveToFile();
                Toast message = Toast.makeText(this, R.string.edit_account_succeed,
                        Toast.LENGTH_SHORT);
                message.show();
            } /*else if (aResultCode == Activity.RESULT_CANCELED);*/

        }

    }

    /* Jump to CreatePetActivity screen.*/
    public void newPet(View view){
        Intent intent = new Intent(this, CreatePetActivity.class);
        startActivityForResult(intent, CREATE_NEW_PET);
    }

    /* Jump to EditAccountActivity screen.*/
    public void manageAccount(View view){
        Intent intent = new Intent(this, EditAccountActivity.class);
        startActivityForResult(intent, MANAGE_ACCOUNT);
    }

    /* Change the sortStatus.*/
    public void toggleSortingMethod(View view){
        if (sortStatus == RANDOM_SORT){
            sortStatus = HEALTH_SORT;
        } else if (sortStatus == HEALTH_SORT){
            sortStatus = AGE_SORT;
        } else if (sortStatus == AGE_SORT){
            sortStatus = HEALTH_SORT;
        }
        updateView();
    }

    /* Log out; Jump back to MainActivity screen.*/
    public void logout(View view){
        finish();
    }

    /* Whenever the user destroy this activity, it indicates that they logged out.*/
    @Override
    public void onDestroy() {
        // Logged off user.
        manager.resetCurrentUser();
        manager.saveToFile();
        System.out.println("***SelectPetActivity Destroyed***");
        super.onDestroy();
    }

    /* Helper function for PetAdapter to access the money of user.*/
    int getUserMoney(){
        return manager.getMoney();
    }

    /* Helper function for PetAdapter to remove pet from user.*/
    void removeUserPet(Pet pet){
        manager.removePet(pet);
    }

    int getCandies(){
        return manager.getCandies();
    }

}
