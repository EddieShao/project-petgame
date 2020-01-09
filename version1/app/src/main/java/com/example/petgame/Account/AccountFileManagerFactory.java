package com.example.petgame.Account;

import android.content.Context;

/* This class is a builder class that is used to construct new AccountManagers.*/
public class AccountFileManagerFactory {

    /**
     *  Build a UsersCredentials with information from the file with given filename.
     *
     *  @param context the context where the manager is created, should be an Activity object.
     *  @return the new constructed UsersCredentials.
     */
    public UsersCredentials buildUsersCredentials(Context context, String filename){
        return new AccountFileManager(context, filename);
    }

    /**
     *  Build an AccountUpdater with information from the file with given filename.
     *
     *  @param context the context where the manager is created, should be an Activity object.
     *  @return the new constructed AccountUpdater.
     */
    public AccountUpdater buildAccountUpdater(Context context, String filename){
        return new AccountFileManager(context, filename);
    }

    /**
     *  Build an AccountCreator with information from the file with given filename.
     *
     *  @param context the context where the manager is created, should be an Activity object.
     *  @return the new constructed AccountCreator.
     */
    public AccountCreator buildAccountCreator(Context context, String filename){
        return new AccountFileManager(context, filename);
    }

    /**
     *  Build a EasterEggCollector with information from the file with given filename.
     *
     *  @param context the context where the manager is created, should be an Activity object.
     *  @return the new constructed EasterEggCollector.
     */
    public EasterEggCollector buildEasterEggCollector(Context context, int filename){
        return new AccountFileManager(context, context.getString(filename));
    }

    /**
     *  Build a EasterEggCheck with information from the file with given filename.
     *
     *  @param context the context where the manager is created, should be an Activity object.
     *  @return the new constructed EasterEggCheck.
     */
    public EasterEggCheck buildEasterEggCheck(Context context, String filename){
        return new AccountFileManager(context, filename);
    }

    /**
     *  Build a AccountEditor with information from the file with given filename.
     *
     *  @param context the context where the manager is created, should be an Activity object.
     *  @return the new constructed AccountEditor.
     */
    public AccountEditor buildAccountEditor(Context context, String filename){
        return new AccountFileManager(context, filename);
    }

}
