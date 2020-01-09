package com.example.petgame.MessageBox;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import com.example.petgame.R;


/* The Builder that construct pop-up dialogs with given content and title*/
public class MessageBoxBuilder {

    private AlertDialog.Builder builder;

    public MessageBoxBuilder(Context context){
        super();
        this.builder = new AlertDialog.Builder(context);
    }

    /* Use this method if you are using id to fetch string values from resources.
    * Display the pop-up window with the given title and message.*/
    public void showMessageBox(int title, int message){

        builder.setMessage(message).setTitle(title);
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /* Fetch string values from resources with given id.
     * Display the pop-up window with the given title, message, buttons which runs given function.*/
    public void showYesNoMessageBox(int title, int message, int yesButtonText, int noButtonText,
                                    Runnable yesFunction, Runnable noFunction){

        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton(yesButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                yesFunction.run();
            }
        });
        builder.setNegativeButton(noButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                noFunction.run();
            }
        });

        builder.create().show();
    }

    /* Fetch string value from resources by id.
    * Display pop-up message based on given title and the given customized view.*/
    public void showCustomizedMessageBox(int title, View view){
/*
        LayoutInflater inflater = LayoutInflater.from(context);*/
        builder.setView(view);

        builder.setTitle(title);

        AlertDialog d = builder.create();
        d.show();
    }


}
