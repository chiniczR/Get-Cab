package com.example.rchinicz.getcab2.model.model.backend;
import java.util.ArrayList;
import com.example.rchinicz.getcab2.model.model.entities.Cabbie;
import com.example.rchinicz.getcab2.model.model.entities.Trip;

public interface IBackEnd
{
    // Cabbie-related methods ----------------------------------------------------
    void addCabbie(Cabbie cabbie);
    void cabbieExists(Cabbie cabbie, IAction<Boolean> action);
    void Authenticate(Cabbie cabbie, IAction<Boolean> action);
    // -----------------------------------------------------------------------------------
    // Trip-related methods -------------------------------------------------------
    void addTrip(Trip trip);
    void tripExists(Trip trip, IAction<Boolean> action);
    void openTrips(IAction<ArrayList<Trip>> action);
    void myTrips(Cabbie cabbie, IAction<ArrayList<Trip>> action);
    void startTrip(String key, String driverId, IAction<Boolean> action);
    void endTrip(String key, IAction<Boolean> action);
    // -----------------------------------------------------------------------------------
}
