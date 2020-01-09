package com.example.petgame.Account;

public interface AccountCreator {
    /* A interface responsible for creating a new Account.*/

    /* Check whether username is already existed*/
    boolean duplicatedUsername(String username);

    /* Save newAccount to file.*/
    void addNewAccountToFile(Account newAccount);
}
