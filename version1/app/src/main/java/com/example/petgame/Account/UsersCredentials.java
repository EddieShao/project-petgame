package com.example.petgame.Account;

public interface UsersCredentials {
    /* Interface responsible for validate user credentials*/

    /* Return true iff the username exists and the password is correct.*/
    boolean validateUser(String username, String password);

}
