package com.example.petgame.CreatePet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.petgame.Account.AccountFileManagerFactory;
import com.example.petgame.Account.EasterEggCheck;
import com.example.petgame.MessageBox.MessageBoxBuilder;
import com.example.petgame.Pet.Pet;
import com.example.petgame.Pet.PetFactory;
import com.example.petgame.R;

public class CreatePetActivity extends AppCompatActivity {

    /* Create Pet Screen.*/

    public static final String NEW_PET = "new_pet";

    /* Widgets in this screen.*/
    private EditText name;
    private Spinner color;
    private Spinner type;

    /* Manager which save info to and read from file.
    * Only have access to EasterEggCheck methods.*/
    private EasterEggCheck manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pet);

        name = findViewById(R.id.newpetname);
        color = findViewById(R.id.petColor);
        type = findViewById(R.id.petType);

        // Create the manager by Factory.
        manager = new AccountFileManagerFactory().buildEasterEggCheck(this, getString(R.string.DATAFILE));

        // Check whether the easter egg is unlocked.
        int petTypesID;
        if (!manager.isUnlocked()) {
            petTypesID = R.array.pet_types;
        } else {
            petTypesID = R.array.pet_types_unlocked;
        }

        // Set the array of pet types to the spinner widget.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                petTypesID, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        type.setAdapter(adapter);
        
    }
    /* Create the pet and send it back to SelectPetActivity.*/
    public void confirmCreation(View view){

        // Fetch user inputs.
        String petName = name.getText().toString();
        String petColor = color.getSelectedItem().toString();
        String petType = type.getSelectedItem().toString();

        // Generate pop-up message box.
        MessageBoxBuilder dialog = new MessageBoxBuilder(this);

        // Check for empty inputs.
        if (petName.equals("")||petColor.equals("")||petType.equals("")){
            // Pop out message box to remind user that the input is empty.
            dialog.showMessageBox(R.string.empty_input_title, R.string.empty_input_content);
            return;
        }
        // Construct a new pet.
        Pet pet = new PetFactory().constructPet(petType, petName, petColor);

        // Send the new pet back to SelectPetActivity
        Intent result = new Intent();
        result.putExtra(NEW_PET, pet);
        setResult(Activity.RESULT_OK, result);
        finish();
    }

    /* Returns to SelectPetActivity without result and do not create anything.*/
    public void cancelCreation(View view){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    /* When the back button at the bottom is pressed*/
    @Override
    public void onBackPressed(){
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    private void unlockEasterEgg(){
        if (manager.isUnlocked()) {
            MessageBoxBuilder dialogBuilder = new MessageBoxBuilder(this);
            dialogBuilder.showMessageBox(R.string.unlock_easteregg_title,
                    R.string.unlock_easteregg_message);
        }
    }
}
