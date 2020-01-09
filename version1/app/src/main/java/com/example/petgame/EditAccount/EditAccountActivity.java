package com.example.petgame.EditAccount;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.petgame.Account.AccountFileManagerFactory;
import com.example.petgame.Account.AccountEditor;
import com.example.petgame.MessageBox.MessageBoxBuilder;
import com.example.petgame.R;

public class EditAccountActivity extends AppCompatActivity {

    /* Key used to pass information through intent. */
    public static final String NEW_NICKNAME = "new_nickname";
    public static final String NEW_PASSWORD = "new_password";

    /* Manage file information. Only have access to AccountEditor methods.*/
    private AccountEditor manager;

    /* Widgets on this screen.*/
    private EditText nickname;
    private EditText originalPassword;
    private EditText newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        /* Fetch the widgets.*/
        nickname = findViewById(R.id.changeNickname);
        originalPassword = findViewById(R.id.originalPassword);
        newPassword = findViewById(R.id.newPassword);

        manager = new AccountFileManagerFactory().buildAccountEditor(this, getString(R.string.DATAFILE));

        /* Default text is the original nickname.*/
        nickname.setText(manager.getNickname());

    }

    /* Send the changes to the account back to SelectPetActivity screen.*/
    public void saveChanges(View view){
        String prevPassword = originalPassword.getText().toString();
        String updatedPassword = newPassword.getText().toString();
        String newNickname = nickname.getText().toString();
        MessageBoxBuilder dialog = new MessageBoxBuilder(this);

        if (newNickname.equals("")){
            // Pop out message to remind user that the input is empty.
            dialog.showMessageBox(R.string.empty_input_title, R.string.empty_input_content);
        } else if (prevPassword.equals("") && updatedPassword.equals("")){
            Intent intent = new Intent();
            intent.putExtra(NEW_NICKNAME, newNickname);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            if (manager.checkPassword(prevPassword)){
                if (updatedPassword.equals("")){
                    dialog.showMessageBox(R.string.empty_input_title, R.string.empty_input_content);
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra(NEW_PASSWORD, updatedPassword);
                intent.putExtra(NEW_NICKNAME, newNickname);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                // Pop out message to remind user that the password entered is wrong.
                dialog.showMessageBox(R.string.wrong_password_title, R.string.wrong_password_content);
            }
        }
    }

    /* Returns to SelectPetActivity without result and do not create anything.*/
    public void cancelChange(View view){
        setResult(RESULT_CANCELED);
        finish();
    }

    /* When the back button at the bottom is pressed*/
    @Override
    public void onBackPressed(){
        setResult(RESULT_CANCELED);
        finish();
    }
}
