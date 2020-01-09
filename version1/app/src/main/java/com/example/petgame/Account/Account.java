package com.example.petgame.Account;

import com.example.petgame.Pet.Pet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* An account to login the game*/
public class Account {

    /* Initial money for each account. A constant*/
    private static final int INITIAL_MONEY = 50;

    /* List of Pets this Account has.*/
    private ArrayList<Pet> allPets = new ArrayList<>();

    /* The remaining money this Account has*/
    private int money;

    /* The USERNAME of this Account, which is unique to each account and cannot be changed.
    Users are using the USERNAME to login*/
    private final String USERNAME;

    /* The password of this Account*/
    private String password;

    /* The nickname of this account.*/
    private String nickname;

    /* The EasterEggManager which manage information of easter eggs of this account*/
    private EasterEggManager easterEggManager;

    /* Number of candies of this account.*/
    private int candies;

    /**
     * Construct a brand new account.
     *
     * @param username USERNAME of this new account.
     * @param password password of this new account.
     * @param nickname nickname of this new account, which will be displayed in the game.
     */
    public Account(String username, String password, String nickname) {
        this.USERNAME = username;
        this.password = password;
        this.money = INITIAL_MONEY;
        this.nickname = nickname;
        this.easterEggManager = new EasterEggManager();
        this.candies = 0;

    }

    /**
     * Construct the account with given information.
     * Call this constructor when re-constructing Account from the data file.
     *
     * @param username USERNAME of this account.
     * @param password password of this account.
     * @param money    remaining money of this account.
     * @param nickname nickname of this account.
     */
    public Account(String username, String password, int money, String nickname, String easternEgg, int candies) {
        this.USERNAME = username;
        this.password = password;
        this.money = money;
        this.nickname = nickname;
        this.easterEggManager = new EasterEggManager(easternEgg);
        this.candies = candies;

    }

    /**
     * Add the given pet to the list of pets this account has.
     *
     * @param pet the pet to be added to the list of pets of this account.
     */
    void addPet(Pet pet) {
        allPets.add(pet);
    }

    /**
     * Remove the given pet from the list of pets this account has.
     *
     * @param pet the pet to be removed.
     */
    void removePet(Pet pet) {
        allPets.remove(pet);
    }

    /**
     * Change the nick name of this account.
     *
     * @param newNickName the new nickname.
     */
    void changeNickName(String newNickName) {
        this.nickname = newNickName;
    }

    /**
     * Change the password of this account.
     *
     * @param newPassword the new password
     */
    void changePassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * Change the money of this account to the given amount.
     *
     * @param updatedAmount the new amount of money
     */
    void updateMoney(int updatedAmount) {
        money = updatedAmount;
    }

    /* A getter of the USERNAME.*/
    String getUSERNAME() {
        return USERNAME;
    }

    /* A getter of the password.*/
    String getPassword() {
        return password;
    }

    /* A getter of the money.*/
    int getMoney() {
        return money;
    }

    /* A getter of the nickname */
    String getNickname() {
        return nickname;
    }

    /* Return true iff pswd is the same as this account's.*/
    boolean checkPassword(String pswd){
        return pswd.equals(this.password);
    }

    String getEasterEgg(){
        return easterEggManager.getEasterEgg();
    }

    void addEasterEgg(String eggName){
        easterEggManager.addEasterEgg(eggName);
    }

    boolean isUnlocked(){
        return easterEggManager.isUnlocked();
    }

    /* Return a copy of the list of pets to protect the list.*/
    List<Pet> getAllPets() {
        return new ArrayList<>(allPets);
    }

    /**
     * Update the pet at the given position in allPets to the updatedPet
     * When PetGame is finished, update the pet to this Account.
     *
     * @param position   the position of the to-be-updated pet in allPets.
     * @param updatedPet the updatedPet the PetGame passed back.
     */
    void updatePetToAccount(int position, Pet updatedPet) {
        allPets.set(position, updatedPet);
    }

    void sortPetsByHealth() {
        Collections.sort(allPets, new SortByHealth());
    }

    void sortPetsByAge() {
        Collections.sort(allPets, new SortByAge());
    }

    int getCandies(){
        return candies;
    }

    void updateCandies(int amount){
        candies = amount;
    }

}

class SortByHealth implements Comparator<Pet> {
    @Override
    public int compare(Pet pet1, Pet pet2) {
        // Sort in ascending order by health.
        return pet1.getHealth() - pet2.getHealth();
    }
}

class SortByAge implements Comparator<Pet> {
    @Override
    public int compare(Pet pet1, Pet pet2) {
        // Sort in ascending order by age.
        return pet1.getAgeInDays() - pet2.getAgeInDays();
    }
}
