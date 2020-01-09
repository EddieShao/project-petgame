package com.example.petgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.petgame.Account.AccountFileManagerFactory;
import com.example.petgame.Account.UsersCredentials;
import com.example.petgame.MessageBox.MessageBoxBuilder;
import com.example.petgame.NewAccountCreation.CreateAccountActivity;
import com.example.petgame.R;
import com.example.petgame.SelectPet.SelectPetActivity;

import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class MainActivity extends AppCompatActivity {

    /* Login Screen.*/

    /* Used to put information on intent.*/
    public static final String LOGIN = "login";

    /* Widget on this screen.*/
    private EditText name;
    private EditText pswd;

    /* Manager that save and read file.*/
    private UsersCredentials manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.username);
        pswd = findViewById(R.id.password);

        manager = new AccountFileManagerFactory().buildUsersCredentials(this, getString(R.string.DATAFILE));

    }


    /* Jump to the SelectPetActivity if the username and password are correct.*/
    public void login(View view){

        name = findViewById(R.id.username);
        pswd = findViewById(R.id.password);
        String username= name.getText().toString();
        String password= pswd.getText().toString();

        if (!manager.validateUser(username, password)){// WRONG PASSWORD
            // Pop out message to remind user that the username or password is incorrect.
            MessageBoxBuilder dialogBuilder = new MessageBoxBuilder(this);
            dialogBuilder.showMessageBox(R.string.wrong_password_title, R.string.wrong_password_content);
            return;
        }

        Intent intent = new Intent(this, SelectPetActivity.class);
        intent.putExtra(LOGIN, username);
        startActivity(intent);

    }

    /* Jump to the CreateAccountActivity.*/
    public void createNewAccount(View view){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }
}
