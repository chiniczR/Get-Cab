package com.example.rchinicz.getcab2.controller.activities;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rchinicz.getcab2.R;
import com.example.rchinicz.getcab2.model.model.backend.IAction;
import com.example.rchinicz.getcab2.model.model.backend.Globals;
import com.example.rchinicz.getcab2.model.model.entities.Cabbie;
import com.example.rchinicz.getcab2.model.model.utils.Dialogs;
import com.example.rchinicz.getcab2.model.model.utils.SharedPref;

public class LoginActivity extends AppCompatActivity
{
    protected EditText email, password;
    protected CheckBox rm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.emailInput);
        if(SharedPref.getPreference(LoginActivity.this, "email") != null
                || SharedPref.getPreference(LoginActivity.this, "email") != "")
        {
            email.setText(SharedPref.getPreference(LoginActivity.this, "email"));
        }
        password = (EditText) findViewById(R.id.passwordInput);
        rm = (CheckBox) findViewById(R.id.remember_me);
    }

    public void onSignInClick(View view)
    {
        rm.setEnabled(false);
        email.setEnabled(false);
        password.setEnabled(false);

        if(rm.isChecked())
        {
            SharedPref.savePreference(LoginActivity.this,"email",email.getText().toString());
        }

        final Cabbie cabbie = new Cabbie(email.getText().toString(),password.getText().toString());

        Globals.backEnd.Authenticate(cabbie, new IAction<Boolean>() {
            @Override
            public void onSuccess(Boolean obj) {
                if (obj == false) {
                    Dialogs.Dialog(LoginActivity.this,"ERROR","Incorrect password","Close");
                    rm.setEnabled(true);
                    email.setEnabled(true);
                    password.setEnabled(true);
                    return;
                }
                else {
                    Globals.cabbie = new Cabbie();
                    Globals.cabbie.setEmail(email.getText().toString());
                    //finish();
                    Intent intent = new Intent(LoginActivity.this, Main.class);
                    startActivity(intent);
                    rm.setEnabled(true);
                    email.setEnabled(true);
                    password.setEnabled(true);
                    password.setText("");
                    if(!rm.isChecked())
                    {
                        email.setText("");
                        SharedPref.savePreference(LoginActivity.this,"email",email.getText().toString());
                    }
                }
            }
            @Override
            public void onFailure(Exception ex) {
                Dialogs.Dialog(LoginActivity.this,"ERROR","Here's what the exception says:\n"
                        + ex.getMessage(), "Close");
                rm.setEnabled(true);
                email.setEnabled(true);
                password.setEnabled(true);
                return;
            }
        });
    }

    public void onRegisterClick(View view)
    {
        Intent intent = new Intent(LoginActivity.this, Register.class);
        startActivity(intent);
    }
}
