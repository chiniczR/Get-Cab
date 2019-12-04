package com.example.rchinicz.getcab2.model.model.entities;

import android.util.Patterns;

public class Passenger
{
    // Constructors --------------------------------------------------------------
    public Passenger() {    }   // Default
    public Passenger(Passenger passenger)   // Copy
    {
        this.name = passenger.name;
        this.email = passenger.email;
        this.phone = passenger.phone;
    }
    public Passenger(String name, String email, String phone)
    // Parameterized
    {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    // -------------------------------------------------------------------------------

    // Members ------------------------------------------------------------------
    protected String name;
    protected String email;
    protected String phone;
    // ------------------------------------------------------------------------------

    // Getters/Setters --------------------------------------------------------
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    // ------------------------------------------------------------------------------

    // Overriding methods ---------------------------------------------------
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
    // -----------------------------------------------------------------------------

    // Misc -----------------------------------------------------------------------
    public boolean valid()
    {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()
                || this.email.equals(""))
        {
            return false;
        }
        if(!android.util.Patterns.PHONE.matcher(phone).matches()
                || this.phone.equals(""))
        {
            return false;
        }
        if (this.name.equals("")) { return false; }
        return true;
    }
    // -----------------------------------------------------------------------------
}
