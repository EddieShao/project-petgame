package com.example.petgame.SelectPet;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petgame.MessageBox.MessageBoxBuilder;
import com.example.petgame.Pet.Pet;
import com.example.petgame.PetGame.PetGameActivity;
import com.example.petgame.R;

import java.util.List;

/* This class is only dealing with the ListView(RecyclerView) in the SelectPetActivity
 * The PetAdapter serves as a medium which displays the list of pets the account has to screen.*/
public class PetAdapter extends RecyclerView.Adapter<PetAdapter.ViewHolder> {

    /* Keys used in Intent to pass information.*/
    public static final String SELECT_PET = "pet";
    public static final String PET_POSITION = "pet_position";
    public static final String ACCOUNT_MONEY = "money";
    public static final String CANDIES = "candies";

    /* Used to startActivityForResult.*/
    static final int PASS_PET_TO_PETGAME = 2;

    /* The list of pets which will be displayed in SelectPetActivity screen.*/
    private List<Pet> allPetsView;
    /* The Activity that is using this class*/
    private SelectPetActivity context;

    /* Construct the PetAdapter with allPets.*/
    PetAdapter(List<Pet> allPets, SelectPetActivity context) {
        this.allPetsView = allPets;
        this.context = context;
    }

    /* Create each row in the view.*/
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        View petView = inflater.inflate(R.layout.item_pet, parent, false);

        return new ViewHolder(context, petView);
    }

    /* Bind information of each pet to each row in the RecyclerView.*/
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pet pet = allPetsView.get(position);

        // Fetch the widget on screen.
        TextView name = holder.petName;
        TextView age = holder.petAge;
        TextView health = holder.petHealth;
        Button start = holder.startGame;
        TextView type = holder.petType;
        ImageView avatar = holder.petAvatar;

        // Display the information on screen.
        String ageText = String.format(context.getString(R.string.pet_display_age), pet.getAgeInDays());
        String healthText = String.format(context.getString(R.string.pet_display_health), pet.getHealth());
        String startText = context.getString(R.string.pet_display_startgame);

        name.setText(pet.getName());
        age.setText(ageText);
        health.setText(healthText);
        start.setText(startText);

        // Display the type of the pet.
        type.setText(pet.getClass().getSimpleName().toUpperCase());

        // Display the avatar image of the pet.
        avatar.setImageDrawable(holder.findAvatar());

        // Do not start game if the pet is dead.
        if (!pet.isAlive()) {
            start.setText(R.string.pet_display_dead);
            name.setTextColor(Color.GRAY);
        }

    }

    @Override
    public int getItemCount() {
        return allPetsView.size();
    }

    /* The view which displays each row, information of each pet, of the RecyclerView*/
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView petName;
        private TextView petAge;
        private TextView petHealth;
        private Button startGame;
        private ImageButton removePet;
        private TextView petType;
        private ImageView petAvatar;
        private SelectPetActivity context;

        ViewHolder(SelectPetActivity context, View itemView) {
            super(itemView);

            petName = itemView.findViewById(R.id.petname);
            petAge = itemView.findViewById(R.id.age);
            petHealth = itemView.findViewById(R.id.health);

            petType = itemView.findViewById(R.id.type);

            petAvatar = itemView.findViewById(R.id.petAvatar);

            startGame = itemView.findViewById(R.id.startGame);
            removePet = itemView.findViewById(R.id.removePet);
            this.context = context;


            startGame.setOnClickListener(this);
            removePet.setOnClickListener(this);
        }

        /* Return pet selected by the user.*/
        private Pet findPet() {// return Pet selected
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                return allPetsView.get(position);// Pet pet;
            }
            return null;
        }

        /* Return the correct avatar image of the pet. This is a private helper function*/
        private Drawable findAvatar() {
            Pet pet = findPet();
            if (pet.isAlive()) {
                return context.getDrawable(pet.getDrawableID(false, false));
            } else {
                return context.getDrawable(R.drawable.gravestone);
            }
        }

        /* Action when the button of this row is pressed.*/
        @Override
        public void onClick(View view) {
            Pet pet = findPet();

            if (view.getId() == R.id.startGame) {
                // If the button pressed is startGame.

                Intent intent = new Intent(context, PetGameActivity.class);
                if (!pet.isAlive()) {
                    // Pop up a death summary of the pet.
                    deathSummary(pet);
                    return;
                }

                // Information sent to PetGameActivity.
                intent.putExtra(SELECT_PET, pet);
                intent.putExtra(ACCOUNT_MONEY, context.getUserMoney());
                intent.putExtra(PET_POSITION, getAdapterPosition());
                intent.putExtra(CANDIES, context.getCandies());
                // Start the activity of PetGameActivity.
                context.startActivityForResult(intent, PASS_PET_TO_PETGAME);

            } else if (view.getId() == R.id.removePet) {
                // If the button pressed is removePet.

                // Display message box to inform user.
                MessageBoxBuilder dialogBuilder = new MessageBoxBuilder(context);

                if (pet.isAlive()) {
                    dialogBuilder.showYesNoMessageBox(R.string.delete_pet_title,
                            R.string.delete_pet_message, R.string.delete_pet_confirm, R.string.delete_pet_cancel,
                            () -> {
                                context.removeUserPet(pet);
                                context.updateView();
                            },
                            () -> {
                            });
                } else {
                    dialogBuilder.showMessageBox(R.string.delete_pet_fail_title,
                            R.string.delete_pet_fail_message);
                }

            }
        }

        /* Helper function used to display the pop-up death summary of dead pets.*/
        private void deathSummary(Pet pet){
            MessageBoxBuilder dialogBuilder = new MessageBoxBuilder(this.context);

            // Creating the view of the pet death summary.
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.death_summary, null);
            ImageView portrait = v.findViewById(R.id.dead_picture);
            portrait.setImageDrawable(context.getDrawable(
                    pet.getDrawableID(true, true)));

            // Display the death summary.
            dialogBuilder.showCustomizedMessageBox(R.string.pet_died_title, v);

        }
    }
}
