package com.example.rchinicz.getcab.model.entities;
import java.io.Serializable;
import java.util.Date;

public class Trip implements Serializable
{
    // Constructors --------------------------------------------------------------------------------
    public Trip ()  // Default
    {
        state = TripState.FREE;
        origin = new String("<Origin>");
        destination = new String("<Destination>");
        passenger = new Passenger();
    }
    // Parameterized
    public Trip (String otrOrigin, String otrDestination, Passenger otrPassenger)
    {
        state = TripState.FREE;
        origin = new String(otrOrigin);
        destination = new String(otrDestination);
        passenger = new Passenger(otrPassenger);
        startDateHour = new Date();
    }
    public Trip(Trip otrTrip)   // Copy - assumes the other Trip object is completely valid
    {
        state = otrTrip.getState();
        origin = new String(otrTrip.getOrigin());
        originLat = otrTrip.getOriginLat();
        originLon = otrTrip.getOriginLon();
        destination = new String(otrTrip.getDestination());
        destinationLat = otrTrip.getDestinationLat();
        destinationLon = otrTrip.getDestinationLon();
        startDateHour = new Date(otrTrip.getStartDateHour().getTime());
        passenger = new Passenger(otrTrip.getPassenger());
        endDateHour = new Date();
    }
    // ---------------------------------------------------------------------------------------------

    // Private members -----------------------------------------------------------------------------
    private TripState state;
    private String origin;
    private double originLat;
    private double originLon;
    private String destination;
    private double destinationLat;
    private double destinationLon;
    private Date startDateHour;
    private Date endDateHour = new Date();
    private Passenger passenger;
    // ---------------------------------------------------------------------------------------------

    // Getters/Setters -----------------------------------------------------------------------------
    public TripState getState() {
        return state;
    }
    public void setState(TripState state) {
        this.state = state;
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
    public Date getStartDateHour() {
        return new Date(startDateHour.getTime());
    }
    public Date getEndDateHour() {
        return new Date(endDateHour.getTime());
    }
    public void setEndDateHour(Date endDateHour)  {
        this.endDateHour = new Date(endDateHour.getTime());
    }
    public Passenger getPassenger() {
        return new Passenger(passenger);
    }
    public void setPassenger(Passenger passenger) {
        this.passenger = new Passenger(passenger);
    }
    // ---------------------------------------------------------------------------------------------

    // Overriding methods --------------------------------------------------------------------------
    @Override
    public boolean equals(Object obj)
    {
        if(obj.getClass() == Trip.class)
        {
            Trip otr = (Trip)obj;
            if(     state.equals(otr.getState())
                    && origin.equals(otr.getOrigin())
                    && destination.equals(otr.getDestination())
                    && startDateHour.equals(otr.getStartDateHour())
                    && passenger.equals(otr.getPassenger())   )
            {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString()
    {
        String ifOver = "";
        if(state == TripState.OVER) { ifOver = "Trip ended at: " + endDateHour + "\n"; }
        return "Trip state: " + state + "\n"
                + "Origin: " + origin + "\n"
                + "Destination: " + destination + "\n"
                + "Trip started at: " + startDateHour + "\n"
                + ifOver    // We'll have an end date if the trip is over
                + "Passenger contact information:\n"
                + passenger;
    }
    // ---------------------------------------------------------------------------------------------

    // Misc. --------------------------------------------------------------------------------------
    public String makeKey()
    {
        return passenger.getName() + "-" + passenger.getPhone()
                + "-" + origin + "-" + destination;
    }
    // ---------------------------------------------------------------------------------------------
}
