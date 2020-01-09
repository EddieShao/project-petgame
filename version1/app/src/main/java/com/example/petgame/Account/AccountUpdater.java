package com.example.petgame.Account;

import com.example.petgame.Pet.Pet;

import java.util.List;

public interface AccountUpdater {
    /* Interface responsible for updating information of account.*/

    /* Save information to file.*/
    void saveToFile();

    /* Reset current user to null. Should only be called when account logged out.*/
    void resetCurrentUser();

    /* Return a list of every pet the current logged in user has.*/
    List<Pet> getAllPets();

    /* Return the nickname of the current user.*/
    String getNickname();

    /* Return the balance of the current user.*/
    int getMoney();

    /* Change the balance of the current user to the given amount.*/
    void updateMoney(int amount);

    /* Update the pet of the current user at the given position.*/
    void updatePet(Pet pet, int position);

    /* Add the new pet to the current user.*/
    void addPet(Pet pet);

    /* Changed the user's nickname.*/
    void changeNickname(String newNickname);

    /* Changed the user's password.*/
    void changePassword(String newPassword);

    /* Remove the given pet from current user.*/
    void removePet(Pet pet);

    /* Sort the pets of the current user by age, in ascending order.*/
    void sortPetsByAge();

    /* Sort the pets of the current user by health, in ascending order.*/
    void sortPetsByHealth();

    /* Load information from the data file.*/
    void readFile();

    /* Return number of candies*/
    int getCandies();

    /* Change the number of candies to the given amount.*/
    void updateCandies(int amt);

    /* Getter for the username of the current user.*/
    String getUSERNAME();
}
