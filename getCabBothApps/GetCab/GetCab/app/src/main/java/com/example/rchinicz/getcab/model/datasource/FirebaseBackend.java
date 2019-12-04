package com.example.rchinicz.getcab.model.datasource;

import com.example.rchinicz.getcab.model.backend.IAction;
import com.example.rchinicz.getcab.model.backend.IBackend;
import com.example.rchinicz.getcab.model.entities.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseBackend implements IBackend
{
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference trips = database.getReference("trips");

    // Trip operations -----------------------------------------------------------------------------
    @Override
    public void addTrip(Trip toAdd)
    {
        trips.child(toAdd.makeKey()).setValue(toAdd);
        return;
    }
    @Override
    public void existsTrip(final Trip toCheck, final IAction<Boolean> action)
    {
        trips.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                action.onSuccess(snapshot.hasChild(toCheck.makeKey()));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                action.onFailure(databaseError.toException());
            }
        });
    }
    // --------------------------------------------------------------------------------------------------
}
