package com.example.rchinicz.getcab.model.backend;

import com.example.rchinicz.getcab.model.datasource.FirebaseBackend;

public class BackendFactory
{
    public static IBackend getBackend()
    {
        return new FirebaseBackend();
    }
}
