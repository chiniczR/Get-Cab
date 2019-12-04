package com.example.rchinicz.getcab2.controller.activities;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.rchinicz.getcab2.R;
import com.example.rchinicz.getcab2.model.model.backend.Globals;
import com.example.rchinicz.getcab2.model.model.backend.IAction;
import com.example.rchinicz.getcab2.model.model.entities.Cabbie;
import com.example.rchinicz.getcab2.model.model.entities.Passenger;
import com.example.rchinicz.getcab2.model.model.utils.Dialogs;
import com.example.rchinicz.getcab2.model.model.utils.SharedPref;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity
{
    protected EditText firstName,lastName,email,password,tel,IDNumber,creditCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        // To test connectivity with Firebase database, un-comment:
        /*
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Ta-da!");
        */
        Init();
    }

    protected void Init()
    {
        firstName = (EditText) findViewById(R.id.fnameInput);
        lastName = (EditText) findViewById(R.id.lnameInput);
        email = (EditText) findViewById(R.id.emailInput);
        password = (EditText) findViewById(R.id.passwordInput);
        tel = (EditText) findViewById(R.id.phoneInput);
        IDNumber = (EditText) findViewById(R.id.idInput);
        creditCard = (EditText) findViewById(R.id.cardInput);
    }

    public void btnRegisterClick(View view)
    {
        firstName.setEnabled(false);
        lastName.setEnabled(false);
        email.setEnabled(false);
        password.setEnabled(false);
        tel.setEnabled(false);
        IDNumber.setEnabled(false);
        creditCard.setEnabled(false);

        String firstNameTxt = firstName.getText().toString();
        String lastNameTxt = lastName.getText().toString();
        String emailTxt = email.getText().toString();
        String passwdTxt = password.getText().toString();
        String phoneTxt = tel.getText().toString();
        String idTxt = IDNumber.getText().toString();
        String cardTxt = creditCard.getText().toString();

        Passenger passenger = new Passenger(firstNameTxt, emailTxt, phoneTxt);

        if (!passenger.valid())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
            builder.setMessage(R.string.passenger_info_dialog_msg)
                    .setTitle(R.string.passenger_info_title);
            AlertDialog dialog = builder.create();
            dialog.show();
            clearAndEnableEditTexts(true);
            return;
        }

        final Cabbie cabbie = new Cabbie(new Passenger(passenger), lastNameTxt, idTxt, cardTxt, passwdTxt);

        if (!cabbie.valid() && passenger.valid())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
            builder.setMessage(R.string.cabbie_info_dialog_msg)
                    .setTitle(R.string.cabbie_info_title);
            AlertDialog dialog = builder.create();
            dialog.show();
            clearAndEnableEditTexts(true);
            return;
        }

        if(!validPassword(passwdTxt))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
            builder.setMessage(R.string.bad_password_dialog_msg)
                    .setTitle(R.string.bad_password_title);
            AlertDialog dialog = builder.create();
            dialog.show();
            clearAndEnableEditTexts(true);
            return;
        }

        Globals.backEnd.cabbieExists(cabbie, new IAction<Boolean>() {
            @Override
            public void onSuccess(Boolean obj) {
                if (obj) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                    builder.setMessage(R.string.account_exists_dialog_msg)
                            .setTitle(R.string.account_exists_title);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    return;
                }
                else
                {
                    AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(Register.this);
                    // Add the buttons
                    confirmBuilder.setMessage("This is the information you\'ve entered:\n"
                            + cabbie.toString() + "\nDo you confirm it is accurate?");
                    confirmBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            SharedPref.savePreference(Register.this, "email", email.getText().toString());
                            Globals.backEnd.addCabbie(cabbie);
                            Globals.cabbie = cabbie;
                            AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                            builder.setMessage(R.string.account_added_dialog_msg)
                                    .setTitle(R.string.account_added_title);
                            AlertDialog dialog2 = builder.create();
                            dialog2.show();
                            // Just in case the user presses back too many times and ends up on Register again
                            clearAndEnableEditTexts(false);
                            Intent intent = new Intent(Register.this, Main.class);
                            startActivity(intent);
                            return;
                        }
                    });
                    confirmBuilder.setNegativeButton("No, let me change the information", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            clearAndEnableEditTexts(true);
                            return;
                        }
                    });
                    // Set other dialog properties
                    // Create the AlertDialog
                    AlertDialog dialogConfirm = confirmBuilder.create();
                    dialogConfirm.show();
                    //finish();
                }
            }
            @Override
            public void onFailure(Exception ex) {
                Dialogs.Dialog(Register.this,getString(R.string.FIREBASE),ex.getMessage(),getString(R.string.BUTTON_CLOSE));
            }
        });
    }

    protected static boolean validPassword(String password)
    {
        // Pattern = at least eight characters including one lowercase, one uppercase, one number
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
        return pattern.matcher(password).matches();
    }

    protected void clearAndEnableEditTexts(boolean justEnable)
    {
        firstName.setEnabled(true);
        lastName.setEnabled(true);
        email.setEnabled(true);
        password.setEnabled(true);
        tel.setEnabled(true);
        IDNumber.setEnabled(true);
        creditCard.setEnabled(true);
        if(!justEnable)
        {
            firstName.setText("");
            lastName.setText("");
            email.setText("");
            password.setText("");
            tel.setText("");
            IDNumber.setText("");
            creditCard.setText("");
        }
    }
}
