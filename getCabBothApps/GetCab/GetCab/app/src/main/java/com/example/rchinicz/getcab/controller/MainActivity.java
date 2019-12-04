package com.example.rchinicz.getcab.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.example.rchinicz.getcab.R;
import com.example.rchinicz.getcab.model.backend.IAction;
import com.example.rchinicz.getcab.model.backend.BackendFactory;
import com.example.rchinicz.getcab.model.backend.IBackend;
import com.example.rchinicz.getcab.model.entities.Passenger;
import com.example.rchinicz.getcab.model.entities.Trip;

import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity
{
    Trip trip;
    IBackend ibe;
    Passenger passenger = new Passenger();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ibe = BackendFactory.getBackend();
        trip = new Trip();
    }

    public void onGetCabClick(View view)
    {
        final EditText originText, destinationText, nameText, phoneText, emailText;
        String origin, destination, passengerName, passengerPhone, passengerEmail;

        originText = findViewById(R.id.originEdit);
        originText.setEnabled(false);
        origin = new String(originText.getText().toString());

        destinationText = findViewById(R.id.destinationEdit);
        destinationText.setEnabled(false);
        destination = new String(destinationText.getText().toString());

        nameText = findViewById(R.id.nameEdit);
        nameText.setEnabled(false);
        passengerName = new String(nameText.getText().toString());

        phoneText = findViewById(R.id.phoneEdit);
        phoneText.setEnabled(false);
        passengerPhone = new String(phoneText.getText().toString());

        emailText = findViewById(R.id.emailEdit);
        emailText.setEnabled(false);
        passengerEmail = new String(emailText.getText().toString());

        passenger = new Passenger(passengerEmail, passengerName, passengerPhone);

        if (!passenger.valid())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.passenger_info_dialog_msg)
                    .setTitle(R.string.passenger_info_title);
            AlertDialog dialog = builder.create();
            dialog.show();
            originText.setEnabled(true);
            destinationText.setEnabled(true);
            nameText.setEnabled(true);
            phoneText.setEnabled(true);
            emailText.setEnabled(true);
            return;
        }

        trip = new Trip(origin, destination, passenger);
        if (!valid(trip))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage(R.string.invalid_address_dialog_msg)
                    .setTitle(R.string.invalid_address_title);
            AlertDialog dialog = builder.create();
            dialog.show();
            originText.setEnabled(true);
            destinationText.setEnabled(true);
            nameText.setEnabled(true);
            phoneText.setEnabled(true);
            emailText.setEnabled(true);
            return;
        }

        ibe.existsTrip(trip, new IAction<Boolean>() {
            @Override
            public void onSuccess(Boolean obj) {
                if (obj == true)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage(R.string.trip_exists_dialog_msg)
                            .setTitle(R.string.trip_exists_title);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                else
                {
                    AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(MainActivity.this);
                    // Add the buttons
                    confirmBuilder.setMessage("This is the trip you\'ve entered:\n"
                            + trip.toString() + "\nDo you confirm the accuracy of this information?");
                    confirmBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked OK button
                            ibe.addTrip(new Trip(trip));
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setMessage(R.string.trip_added_dialog_msg)
                            .setTitle(R.string.trip_added_title);
                            AlertDialog dialog2 = builder.create();
                            dialog2.show();
                            originText.setText("");
                            originText.setEnabled(true);
                            destinationText.setText("");
                            destinationText.setEnabled(true);
                            nameText.setEnabled(true);
                            phoneText.setEnabled(true);
                            emailText.setEnabled(true);
                            return;
                        }
                    });
                    confirmBuilder.setNegativeButton("No, let me change the information", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                            originText.setEnabled(true);
                            destinationText.setEnabled(true);
                            nameText.setEnabled(true);
                            phoneText.setEnabled(true);
                            emailText.setEnabled(true);
                            return;
                        }
                    });
                    // Set other dialog properties
                    // Create the AlertDialog
                    AlertDialog dialogConfirm = confirmBuilder.create();
                    dialogConfirm.show();
                }
            }
            @Override
            public void onFailure(Exception e)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(R.string.something_wrong_dialog_msg)
                        .setTitle(R.string.something_wrong_title);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public boolean valid(Trip t)
    {
        if(!validAddress(t.getOrigin(), t, true)) { return false; }
        if(!validAddress(t.getDestination(), t, false)) { return false; }
        if(t.getOrigin().equals(t.getDestination())) { return false; }
        if(!passenger.valid()) { return false; }
        return true;
    }
    protected boolean validAddress(String address, Trip trip, boolean isOrigin)
    {
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses =
                    geoCoder.getFromLocationName(address, 3);

            if (addresses.size() > 0)
            {
                Address addr = addresses.get(0);
                if (isOrigin)
                {
                    trip.setOriginLat(addr.getLatitude());
                    trip.setOriginLon(addr.getLongitude());
                }
                else
                {
                    trip.setDestinationLat(addr.getLatitude());
                    trip.setDestinationLon(addr.getLongitude());
                }
                return true;
            }
        } catch (Exception e) {  }
        return false;
    }
}
