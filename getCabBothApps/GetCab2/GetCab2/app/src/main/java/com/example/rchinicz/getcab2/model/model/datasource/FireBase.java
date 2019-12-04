package com.example.rchinicz.getcab2.model.model.datasource;
import android.location.Location;

import com.example.rchinicz.getcab2.model.model.entities.Cabbie;
import com.example.rchinicz.getcab2.model.model.entities.Trip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import com.example.rchinicz.getcab2.model.model.backend.Globals;
import com.example.rchinicz.getcab2.model.model.backend.IAction;
import com.example.rchinicz.getcab2.model.model.backend.IBackEnd;

public class FireBase implements IBackEnd
{
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference travels = db.getReference("trips");
    DatabaseReference drivers = db.getReference("cabbies");

    // Cabbie-related operations --------------------------------------------------------
    @Override
    public void addCabbie(Cabbie cabbie)
    {
        drivers.child(cabbie.getKey()).setValue(cabbie);
        return;
    }
    @Override
    public void cabbieExists(final Cabbie cabbie, final IAction<Boolean> action)
    {
        drivers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                action.onSuccess(snapshot.hasChild(cabbie.getKey()));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                action.onFailure(databaseError.toException());
            }
        });
    }
    @Override
    public void Authenticate(final Cabbie cabbie, final IAction<Boolean> action)
    {
        drivers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String key = cabbie.getKey();
                if (snapshot.hasChild(key)) {
                    Cabbie d = snapshot.child(key).getValue(Cabbie.class);
                    if (d.getPassword().equals(cabbie.getPassword())){
                        Globals.cabbie = d;
                        action.onSuccess(true);
                    } else
                        action.onSuccess(false);
                } else {
                    action.onSuccess(false);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                action.onFailure(databaseError.toException());
            }
        });
    }
    // ------------------------------------------------------------------------------------------

    // Trip-related operations ------------------------------------------------------------
    @Override
    public void addTrip(Trip trip)
    {
        travels.child(trip.getKey()).setValue(trip);
        return;
    }
    @Override
    public void tripExists(final Trip trip, final IAction<Boolean> action)
    {
        travels.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
               action.onSuccess(snapshot.hasChild(trip.getKey()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                action.onFailure(databaseError.toException());
            }
        });
    }
    @Override
    public void openTrips(final IAction<ArrayList<Trip>> action)
    {
        travels.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<Trip> results = new ArrayList<>();
                for (DataSnapshot item: snapshot.getChildren()) {
                    Trip trip = item.getValue(Trip.class);
                    if (trip.getState().equals(Trip.State.FREE))
                    {
                        float[] dist = {0};
                        Location.distanceBetween(
                                trip.getOriginLat(), trip.getOriginLon(),
                                trip.getDestinationLat(), trip.getDestinationLon(), dist
                        );
                        if (dist[0] != 0)
                        {
                            trip.setTripDistance(dist[0] / 1000.0);
                            // Divided by 1000 so that we can have the distance in Km
                        }
                        results.add(trip);
                    }
                }
                action.onSuccess(results);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                action.onFailure(databaseError.toException());
            }
        });
    }
    @Override
    public void myTrips(final Cabbie cabbie, final IAction<ArrayList<Trip>> action)
    {
        travels.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<Trip> results = new ArrayList<>();
                for (DataSnapshot item: snapshot.getChildren()) {
                    Trip trip = item.getValue(Trip.class);
                    if (trip.getCabbieId() != null )
                        if (trip.getCabbieId().equals(cabbie.getEmail()) && trip.getState().equals(Trip.State.ONGOING) )
                        {
                            float[] dist = {0};
                            Location.distanceBetween(
                                    trip.getOriginLat(), trip.getOriginLon(),
                                    trip.getDestinationLat(), trip.getDestinationLon(), dist
                            );
                            if (dist[0] != 0) {
                                trip.setTripDistance(dist[0] / 1000.0);
                                // Divided by 1000 so that we can have the distance in Km
                            }
                            results.add(trip);
                        }
                }

                action.onSuccess(results);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                action.onFailure(databaseError.toException());
            }
        });
    }
    @Override
    public void startTrip(final String key, final String email, final IAction<Boolean> action)
    {
        travels.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(key)) {
                    Trip trip = snapshot.child(key).getValue(Trip.class);
                    trip.setCabbieId(email);
                    trip.setState(Trip.State.ONGOING);
                    travels.child(key).setValue(trip);
                    action.onSuccess(true);
                } else {
                    action.onSuccess(false);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                action.onFailure(databaseError.toException());
            }
        });
    }
    @Override
    public void endTrip(final String key, final IAction<Boolean> action)
    {
        travels.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(key)) {
                    Trip trip = snapshot.child(key).getValue(Trip.class);
                    trip.setState(Trip.State.OVER);
                    travels.child(key).setValue(trip);
                    action.onSuccess(true);
                } else {
                    action.onSuccess(false);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                action.onFailure(databaseError.toException());
            }
        });
    }
    // ------------------------------------------------------------------------------------------
}
