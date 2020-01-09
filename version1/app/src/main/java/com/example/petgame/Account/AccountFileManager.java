package com.example.petgame.Account;

import android.content.Context;

import com.example.petgame.Pet.Pet;
import com.example.petgame.Pet.PetFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/* The class which reads and writes the file.*/
class AccountFileManager implements UsersCredentials, AccountCreator, AccountUpdater,
        EasterEggCollector, EasterEggCheck, AccountEditor {

    /* Filename of the data file.*/
    private String accountFile;

    /* The Activity that instantiates the FileManager.*/
    private Context context;

    /* The Hashmap that stores information of all accounts.*/
    private HashMap<String, Account> allAccounts;

    /* The username of the current logged in account.
    Should be null if there is no logged in account.*/
    private String currentUser = null;

    /* Indices of the information line about pets in accountFile*/
    private static final int TYPE = 0;
    private static final int NAME = 1;
    private static final int HEALTH = 2;
    private static final int ENERGY = 3;
    private static final int YEAR = 4;
    private static final int MONTH = 5;
    private static final int DAY = 6;
    private static final int COLOR = 7;

    /* Pieces of information to store a pet.*/
    private static final int INFO_SIZE = 8;

    /* Indices of the information line about the account in accountFile.*/
    private static final int USERNAME = 0;
    private static final int PASSWORD = 1;
    private static final int MONEY = 2;
    private static final int NICKNAME = 3;
    private static final int EASTERNEGG = 4;
    private static final int CANDIES = 5;

    /* Pieces of information to store an account*/
    private static final int USER_INFO_SIZE = 6;

    /**
     * Construct this fileManager to read information from filename.
     *
     * @param context  the activity this fileManager is interacting with.
     * @param filename the filename of the file this class interact with.
     */
    AccountFileManager(Context context, String filename) {

        this.context = context;
        this.accountFile = filename;
        allAccounts = new HashMap<>();

        /* Create the file if the file does not exist.*/
        File file = context.getFileStreamPath(accountFile);
        if (file == null || !file.exists()) {
            createAccountFile();
        }
        // Load the information in file to Hashmap.
        readFromFile();
    }

    /**
     * Return true iff the username exists and password is correct. Return false otherwise.
     *
     * @param username username to be validated.
     * @param password password to be validated.
     * @return whether the account and the password is valid.
     */
    @Override
    public boolean validateUser(String username, String password) {

        // Read information from file before checking for validity.
        readFromFile();
        // Fetch the user with given username.
        Account account = findUser(username);

        if ((account != null) && (account.checkPassword(password))){
            setCurrentUser(username);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Add new Account and save it to file.
     * @param newAccount: the new account to be added.
     */
    @Override
    public void addNewAccountToFile(Account newAccount) {
        addUpdatedAccount(newAccount);
        addInfoToFile(newAccount);
    }

    /**
     * Check whether the username has already been taken.
     * Return true iff the username exists, false otherwise.
     *
     * @param username the given username to be checked.
     * @return whether the name exists.
     */
    @Override
    public boolean duplicatedUsername(String username) {
        readFromFile();
        return allUsernames().contains(username);
    }

    /* Save every information to file.*/
    @Override
    public void saveToFile() {
        updateInfoToFile();
    }

    /**
     * Record easter egg to the current account and save the information to file
     *
     * @param eggName the name of this piece of easter egg.
     */
    @Override
    public void addEasterEggPieceAndSave(String eggName){
        readFromFile();
        addEasterEgg(eggName);
        saveToFile();
    }

    /* Return true iff the every piece of easter egg has been collected*/
    @Override
    public boolean isUnlocked() {
        return findUser(currentUser).isUnlocked();
    }

    /* Return a copy of the list of pets the current user has.*/
    @Override
    public List<Pet> getAllPets() {
        return getCurrentUser().getAllPets();
    }

    /* Return the nickname of the current user.*/
    @Override
    public String getNickname() {
        return getCurrentUser().getNickname();
    }

    /* Return the amount of money the current user has.*/
    @Override
    public int getMoney() {
        return getCurrentUser().getMoney();
    }

    /* Updated the money in the current user to the given amount.*/
    @Override
    public void updateMoney(int amount) {
        getCurrentUser().updateMoney(amount);
        saveToFile();
    }

    /* Update the pet to the current user at the given position.*/
    @Override
    public void updatePet(Pet pet, int position) {
        getCurrentUser().updatePetToAccount(position, pet);
        saveToFile();
    }

    /* Add the new pet to the current user.*/
    @Override
    public void addPet(Pet pet) {
        getCurrentUser().addPet(pet);
        saveToFile();
    }

    /* Change the nickname of the current user.*/
    @Override
    public void changeNickname(String newNickname) {
        getCurrentUser().changeNickName(newNickname);
        saveToFile();
    }

    /* Change the password of the current user.*/
    @Override
    public void changePassword(String newPassword) {
        getCurrentUser().changePassword(newPassword);
        saveToFile();
    }

    /* Remove a given pet from the current user.*/
    @Override
    public void removePet(Pet pet) {
        getCurrentUser().removePet(pet);
        saveToFile();
    }

    /* Sort the list of pets of the current user by age, in ascending order.*/
    @Override
    public void sortPetsByAge() {
        getCurrentUser().sortPetsByAge();
    }

    /* Sort the list of pets of the current user by health, in ascending order.*/
    @Override
    public void sortPetsByHealth() {
        getCurrentUser().sortPetsByHealth();
    }

    /* Load the information in file to the Hashmap.*/
    @Override
    public void readFile() {
        readFromFile();
    }

    /* Getter for the number of candies the current account has.*/
    @Override
    public int getCandies() {
        return getCurrentUser().getCandies();
    }

    /* Change the number of candies the current account has to the given amount.*/
    @Override
    public void updateCandies(int amt){
        getCurrentUser().updateCandies(amt);
    }

    /* Getter for the username of the current account.*/
    @Override
    public String getUSERNAME() {
        return getCurrentUser().getUSERNAME();
    }

    /* Reset the current user to null.
    * IMPORTANT: Should only be called when the account is logged out.*/
    @Override
    public void resetCurrentUser(){
        currentUser = null;
    }

    /**
     * Return true iff pswd is the same as the password of the current user.
     *
     * @param pswd the password to be checked.
     * @return whether pswd is correct.*/
    @Override
    public boolean checkPassword(String pswd) {
        readFromFile();
        return getCurrentUser().checkPassword(pswd);
    }



    /* Create a new empty file in the phone with filename accountFile.
     *  Should only be called if the phone does not already have the file.*/
    private void createAccountFile() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(context.openFileOutput(accountFile, Context.MODE_PRIVATE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.println("null\n");
        writer.close();
    }


    /* Read information in data file to construct the Hashmap allAccounts.*/
    private void readFromFile() {

        Scanner scanner = null;
        try {
            scanner = new Scanner(context.openFileInput(accountFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Load the current user
        String currUser;
        if (scanner.hasNextLine()) {
            currUser = scanner.nextLine().trim();
            if (!currUser.equals("null")){
                this.currentUser = currUser;
            } else {
                this.currentUser = null;
            }
        } else {
            System.out.println("you're in trouble, man");
        }

        while (scanner.hasNextLine()) {
            if (scanner.nextLine().equals("ACCOUNT:")) {
                // Read information of this Account.
                String line1 = scanner.nextLine();
                String[] userInfo = line1.split(",");
                String username = userInfo[USERNAME];
                String password = userInfo[PASSWORD];
                int money = Integer.parseInt(userInfo[MONEY]);
                String nickname = userInfo[NICKNAME];
                String easternEgg = userInfo[EASTERNEGG];
                int candies = Integer.parseInt(userInfo[CANDIES]);
                // Re-construct the account.
                Account account = new Account(username, password, money, nickname, easternEgg, candies);
                // Store the account into Hashmap allAccounts.
                allAccounts.put(username, account);

                String line = scanner.nextLine();
                // Keep reading information until reach the end of account information.
                while (!line.equals("ENDACCOUNT")) {
                    // Information about the pet this account has.
                    String[] petInfo = line.split(",");
                    String type = petInfo[TYPE];
                    String name = petInfo[NAME];
                    int health = Integer.parseInt(petInfo[HEALTH]);
                    int energy = Integer.parseInt(petInfo[ENERGY]);
                    int year = Integer.parseInt(petInfo[YEAR]);
                    int month = Integer.parseInt(petInfo[MONTH]);
                    int day = Integer.parseInt(petInfo[DAY]);
                    String color = petInfo[COLOR];

                    // Reconstruct(Load) the pet to Account.
                    Pet pet = new PetFactory().constructPet(type, name, health, energy, year, month, day, color);
                    account.addPet(pet);

                    // Enter next pet or the end of the account
                    line = scanner.nextLine();
                }
            }
        }
    }

    /* Write the information in Hashmap allAccounts to the file.*/
    private void updateInfoToFile() {

        StringBuilder updatedContent = new StringBuilder();

        // Update the current user.
        updatedContent.append(currentUser);
        updatedContent.append("\n");

        for (String name : allAccounts.keySet()) {
            // Write the basic info about the Account.
            Account account = allAccounts.get(name);

            String[] userInfo = new String[USER_INFO_SIZE];

            updatedContent.append("ACCOUNT:\n");
            userInfo[USERNAME] = account.getUSERNAME();
            userInfo[PASSWORD] = account.getPassword();
            userInfo[MONEY] = String.valueOf(account.getMoney());
            userInfo[NICKNAME] = account.getNickname();
            userInfo[EASTERNEGG] = String.valueOf(account.getEasterEgg());
            userInfo[CANDIES] = String.valueOf(account.getCandies());

            String userInfoLine = String.join(",", userInfo) + "\n";
            updatedContent.append(userInfoLine);

            // Store information of each pet.
            for (Pet pet : account.getAllPets()) {
                // String array to store the information of pet.
                String[] petInfo = new String[INFO_SIZE];
                // Each piece of information.
                petInfo[TYPE] = pet.getClass().getSimpleName();
                petInfo[NAME] = pet.getName();
                petInfo[HEALTH] = String.valueOf(pet.getHealth());
                petInfo[ENERGY] = String.valueOf(pet.getEnergy());
                petInfo[YEAR] = String.valueOf(pet.getBirthDate()[0]);
                petInfo[MONTH] = String.valueOf(pet.getBirthDate()[1]);
                petInfo[DAY] = String.valueOf(pet.getBirthDate()[2]);
                petInfo[COLOR] = pet.getCOLOR();

                String infoLine = String.join(",", petInfo) + "\n";
                updatedContent.append(infoLine);
            }
            // Mark the end of this account.
            updatedContent.append("ENDACCOUNT\n");
        }

        // Write to file
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(context.openFileOutput(accountFile, Context.MODE_PRIVATE));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.println(updatedContent.toString());
        writer.close();
    }

    /* Append information of the newAccount at the end of the file.*/
    private void addInfoToFile(Account newAccount) {

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(context.openFileOutput(accountFile, Context.MODE_APPEND));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // The default format of a new account:
        String newInfo = "ACCOUNT:\n" +
                newAccount.getUSERNAME() + "," +
                newAccount.getPassword() + "," +
                newAccount.getMoney() + "," +
                newAccount.getNickname() + "," +
                newAccount.getEasterEgg() + "," +
                newAccount.getCandies() + "\n" +
                // Mark the end of this account.
                "ENDACCOUNT\n";

        writer.println(newInfo);
        writer.close();
    }

    /**
     * Return all the username existed.
     * So that user will not created accounts with duplicated username.
     *
     * @return List of all usernames in file.
     */
    private List<String> allUsernames() {
        return new ArrayList<>(allAccounts.keySet());
    }

    /**
     * Add updatedAccount to the Hashmap allAccounts.
     *
     * @param updatedAccount the updated account.
     */
    private void addUpdatedAccount(Account updatedAccount) {
        // Put the updatedAccount in the Hashmap.
        allAccounts.put(updatedAccount.getUSERNAME().trim(), updatedAccount);
    }

    /* Set the current user to the given username.
    * IMPORTANT: Should only be called when the user logged in.*/
    private void setCurrentUser(String username){
        if (currentUser == null) {
            currentUser = username;
            updateInfoToFile();
        }
    }

    /* Return the account based on the current user's username.*/
    private Account getCurrentUser(){
        return findUser(currentUser);
    }

    /**
     * Return the Account of the given username from the Hashmap.
     *
     * @param username username given to find the user.
     * @return the account of the given username.
     */
    private Account findUser(String username) {
        return allAccounts.get(username);
    }

    /**
     * Record easter egg to the current account that is logged in.
     *
     * @param eggName the name of this piece of easter egg.
     */
    private void addEasterEgg(String eggName) {
        findUser(currentUser).addEasterEgg(eggName);
    }


}
