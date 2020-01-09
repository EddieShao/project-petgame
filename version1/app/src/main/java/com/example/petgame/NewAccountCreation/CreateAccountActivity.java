package com.example.petgame.NewAccountCreation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.petgame.Account.Account;
import com.example.petgame.Account.AccountCreator;
import com.example.petgame.Account.AccountFileManagerFactory;
import com.example.petgame.MessageBox.MessageBoxBuilder;
import com.example.petgame.R;

public class CreateAccountActivity extends AppCompatActivity {

    /* Create new Account Screen.*/

    /* Manager that saves and reads file.*/
    private AccountCreator manager;

    /* Widget on this screen.*/
    private EditText user;
    private EditText pswd;
    private EditText nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        manager = new AccountFileManagerFactory().buildAccountCreator(this, getString(R.string.DATAFILE));

        user = findViewById(R.id.newUsername);
        pswd = findViewById(R.id.newPassword);
        nick = findViewById(R.id.nickname);
    }

    /* Create the new account and save it to the file.*/
    public void createNewAccount(View view){
        String username = user.getText().toString();
        String password = pswd.getText().toString();
        String nickname = nick.getText().toString();

        MessageBoxBuilder dialog = new MessageBoxBuilder(this);

        // Check for empty inputs.
        if(username.equals("")||password.equals("")||nickname.equals("")||username.equals("null")){
            // Pop out message to remind user that the input is empty.
            dialog.showMessageBox(R.string.empty_input_title, R.string.empty_input_content);
            return;
        }

        if (manager.duplicatedUsername(username)){
            // Pop out message that reminds user that the name has been taken.\
            dialog.showMessageBox(R.string.name_existed_title, R.string.name_existed_content);
            return;
        }

        Account newAccount = new Account(username, password, nickname);
        // Add newAccount to file.
        manager.addNewAccountToFile(newAccount);

        finish();
    }

}
