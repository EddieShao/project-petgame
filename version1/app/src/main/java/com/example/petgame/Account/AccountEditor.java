package com.example.petgame.Account;

public interface AccountEditor {
    /* Interface responsible to edit account's profile, such as nickname and password.*/

     /* Check the password of the current user.*/
    boolean checkPassword(String pswd);

    /* Getter for the current account's nickname.*/
    String getNickname();

}
