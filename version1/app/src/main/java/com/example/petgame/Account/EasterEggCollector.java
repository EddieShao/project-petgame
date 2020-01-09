package com.example.petgame.Account;


public interface EasterEggCollector {
    /* Interface responsible for collecting easter egg pieces.*/

    /* Save info to file.*/
    void saveToFile();

    /* Check whether the condition is met.*/
    boolean isUnlocked();

    /* Collect the egg piece and save it to file.*/
    void addEasterEggPieceAndSave(String eggName);

}
