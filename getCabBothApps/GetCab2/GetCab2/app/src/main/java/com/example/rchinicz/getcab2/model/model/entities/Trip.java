package com.example.rchinicz.getcab2.model.model.entities;
import android.location.Address;
import android.location.Geocoder;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Trip
{
    // Constructors ------------------------------------------------------------------------
    public Trip() {  }  // Default
    public Trip(Passenger passenger, String origin, String destination)
    // Parameterized
    {
        this.passenger = passenger;
        this.origin = origin;
        this.destination = destination;
        this.state = State.FREE;
    }
    // -----------------------------------------------------------------------------------------

    // State enum --------------------------------------------------------------------------
    public enum State {
        FREE,
        ONGOING,
        OVER
    }
    // ------------------------------------------------------------------------------------------

    // Members ------------------------------------------------------------------------------
    protected Passenger passenger;
    private String origin;
    private double originLat;
    private double originLon;
    private String destination;
    private double destinationLat;
    private double destinationLon;
    private double tripDistance;
    protected State state;
    protected String cabbieId;
    protected Date startDateHour;
    protected Date endDateHour;
    // -------------------------------------------------------------------------------------------

    // Getters/Setters ---------------------------------------------------------------------
    public String getCabbieId() {
        return cabbieId;
    }
    public void setCabbieId(String cabbieId) {
        this.cabbieId = cabbieId;
    }
    public Passenger getPassenger() {
        return passenger;
    }
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
    public String getOrigin() {
        return new String(origin);
    }
    public void setOrigin(String origin) {
        this.origin = new String(origin);
    }
    public double getOriginLat() {
        return originLat;
    }
    public void setOriginLat(double originLat) {
        this.originLat = originLat;
    }
    public double getOriginLon() {
        return originLon;
    }
    public void setOriginLon(double originLon) {
        this.originLon = originLon;
    }
    public String getDestination() { return new String(destination); }
    public void setDestination(String destination) {
        this.destination = new String(destination);
    }
    public double getDestinationLat() {
        return destinationLat;
    }
    public void setDestinationLat(double destinationLat) {
        this.destinationLat = destinationLat;
    }
    public double getDestinationLon() {
        return destinationLon;
    }
    public void setDestinationLon(double destinationLon) {
        this.destinationLon = destinationLon;
    }
    public double getTripDistance() { return tripDistance; }
    public void setTripDistance(double tripDistance) { this.tripDistance = tripDistance; }
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }
    public Date getStartDateHour() {
        return startDateHour;
    }
    public void setStartDateHour(Date startDateHour) {
        this.startDateHour = startDateHour;
    }
    public Date getEndDateHour() {
        return endDateHour;
    }
    public void setEndDateHour(Date endDateHour) {
        this.endDateHour = endDateHour;
    }
    // -------------------------------------------------------------------------------------------

    // Misc -------------------------------------------------------------------------------------
    public String getKey()
    {
        return passenger.getName() + "-" + passenger.getPhone()
                + "-" + origin + "-" + destination;
    }
    // ---------------------------------------------------------------------------------------------
}
