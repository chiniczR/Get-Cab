package com.example.rchinicz.getcab.model.entities;

import android.location.Address;
import android.location.Geocoder;
import android.util.Patterns;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Passenger implements Serializable
{
    // Constructors --------------------------------------------------------------------------------
    public Passenger()  // Default
    {
        email = "default";
        name = "Default Defaultovsky";
        phone = "000000000";
    }
    public Passenger(String otrEmail, String otrName, String otrPhone)   // Parameterized
    {
        email = new String(otrEmail);
        name = new String(otrName);
        phone = new String(otrPhone);
    }
    public Passenger(Passenger otr) // Copy
    {
        email = new String(otr.getEmail());
        name = new String(otr.getName());
        phone = new String(otr.getPhone());
    }
    // ---------------------------------------------------------------------------------------------

    // Private members -----------------------------------------------------------------------------
    private String email;
    private String name;
    private String phone;
    // ---------------------------------------------------------------------------------------------

    // Getters/Setters -----------------------------------------------------------------------------
    public String getEmail() {
        return new String(email);
    }
    public void setEmail(String email) {
        this.email = new String(email);
    }
    public String getName() {
        return new String(name);
    }
    public void setName(String name) {
        this.name = new String(name);
    }
    public String getPhone() {
        return new String(phone);
    }
    public void setPhone(String phone) {
        this.phone =  new String(phone);
    }
    // ---------------------------------------------------------------------------------------------

    // Overriding methods --------------------------------------------------------------------------
    @Override
    public boolean equals(Object obj)
    {
        if(obj.getClass() == Passenger.class)
        {
            Passenger otr = (Passenger)obj;
            if(otr.getEmail().equals(email)
                    && otr.getName().equals(name)
                    && otr.getPhone().equals(phone))
            {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString()
    {
        return "Name: " + name + "\nEmail address: " + email + "\nPhone no.: " + phone;
    }
    // ---------------------------------------------------------------------------------------------

    // Misc. ---------------------------------------------------------------------------------------
    public boolean valid()
    {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            return false;
        }
        if(!android.util.Patterns.PHONE.matcher(phone).matches())
        {
            return false;
        }
        return true;
    }
    // ---------------------------------------------------------------------------------------------
}
