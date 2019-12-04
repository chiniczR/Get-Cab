package com.example.rchinicz.getcab2.model.model.entities;

public class Cabbie extends Passenger
{
    // Constructors --------------------------------------------------------------------------------
    public Cabbie() {    } // Default
    public Cabbie(String email, String password)    // Parameterized
    {
        super();
        this.email = email;
        this.password = password;
    }
    public Cabbie(String name, String email, String tel, String lastName, String identity,
                  String cardNumber, String password)   // Alt. parameterized
    {
        super(name, email, tel);
        this.lastName = lastName;
        this.cardNumber = cardNumber;
        this.identity = identity;
        this.password = password;
    }
    public Cabbie(Passenger passenger, String lastName, String identity, String cardNumber,
                  String password)  // Other alt. parameterized
    {
        super(passenger);
        this.lastName = lastName;
        this.cardNumber = cardNumber;
        this.identity = identity;
        this.password = password;
    }
    // ---------------------------------------------------------------------------------------------

    // Members -------------------------------------------------------------------------------------
    protected String lastName;
    protected String identity;
    protected String cardNumber;
    protected String password;
    // ---------------------------------------------------------------------------------------------

    // Getters/Setters -----------------------------------------------------------------------------
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getIdentity() {
        return identity;
    }
    public void setIdentity(String identity) {
        this.identity = identity;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    // ---------------------------------------------------------------------------------------------

    // Overriding methods --------------------------------------------------------------------------
    @Override
    public boolean equals(Object obj)
    {
        if(obj.getClass() == Cabbie.class)
        {
            Passenger passenger = (Cabbie)obj;
            Cabbie otr = (Cabbie)obj;
            if(((Passenger)this).equals(passenger)
                    && otr.getIdentity().equals(identity)
                    && otr.getLastName().equals(lastName)
                    && otr.getCardNumber().equals(cardNumber)
                    && otr.getPassword().equals(password))
            {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString()
    {
        return "Full name: " + name + " " + lastName + "\nEmail address: " + email + "\nPhone no.: " + phone;
    }
    @Override
    public boolean valid()
    {
        if (super.valid() == false) return false;
        if (this.lastName.equals("")) return false;
        if(!luhnTest(cardNumber) || this.cardNumber.equals(""))
        {
            return false;
        }
        if (this.identity.equals("") || this.identity.length() != 9) return false;
        return true;
    }
    // ---------------------------------------------------------------------------------------------

    // Misc ----------------------------------------------------------------------------------------
    public String getKey()
    {
        String key = this.email;
        key = key.replace("@","_");
        key = key.replace(".","__");
        return key;
    }
    protected static boolean luhnTest(String number)    // Luhn test for the credit card number
    {
        int s1 = 0, s2 = 0;
        String reverse = new StringBuffer(number).reverse().toString();
        for(int i = 0 ;i < reverse.length();i++){
            int digit = Character.digit(reverse.charAt(i), 10);
            if(i % 2 == 0){//this is for odd digits, they are 1-indexed in the algorithm
                s1 += digit;
            }else{//add 2 * digit for 0-4, add 2 * digit - 9 for 5-9
                s2 += 2 * digit;
                if(digit >= 5){
                    s2 -= 9;
                }
            }
        }
        return (s1 + s2) % 10 == 0;
    }
    // ---------------------------------------------------------------------------------------------
}
