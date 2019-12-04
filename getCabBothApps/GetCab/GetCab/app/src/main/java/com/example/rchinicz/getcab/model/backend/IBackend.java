package com.example.rchinicz.getcab.model.backend;

import com.example.rchinicz.getcab.model.entities.*;

public interface IBackend
{
    // Trip operations -----------------------------------------------------------------------------
    void addTrip(Trip toAdd);
    void existsTrip(Trip toCheck, IAction<Boolean> action);
    // --------------------------------------------------------------------------------------------------
}
