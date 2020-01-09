package com.example.petgame.Account;

import java.util.HashMap;

class EasterEggManager {
    /* Class responsible for storing and collecting easter eggs.*/

    /* The collection of every easter egg piece this user has.
    The boolean indicates whether this piece has been collected.*/
    private HashMap<String, Boolean> easterEgg = new HashMap<>();

    /* Names of each piece of easter egg.*/
    private static final String[] EGGNAMES= {"Pet1", "Pet2", "Train1", "Train2", "Train3", "Play1", "Play2"};

    /* Constructor of the EasterEggManager.
    * Create a brand new EasterEggManager start with nothing collected.*/
    EasterEggManager(){
        for (int i=0;i<7;i++){
            easterEgg.put(EGGNAMES[i], false);
        }
    }

    /* Constructor of the EasterEggManager.
    * Re-construct the EasterEggManager based on the information String egg indicates.*/
    EasterEggManager(String egg){
        for (int i=0;i<7;i++){
            boolean eggVal = egg.charAt(i) == '1';
            easterEgg.put(EGGNAMES[i], eggVal);
        }

    }

    /* Convert the Hashmap of easter eggs to a string of 1s and 0s.*/
    String getEasterEgg(){
        StringBuilder result = new StringBuilder();
        for (String key : EGGNAMES){
            result.append(easterEgg.get(key) ? "1" : "0");
        }
        return result.toString();
    }

    /* Return true if and only if each piece of easter egg has been collected.*/
    boolean isUnlocked(){
        for (String key : easterEgg.keySet()){
            if (!easterEgg.get(key)){
                return false;
            }
        }
        return true;
    }

    /* Record the piece of easter egg with given eggName to the collection Hashmap.*/
    void addEasterEgg(String eggName){
        easterEgg.put(eggName, true);
    }
}
