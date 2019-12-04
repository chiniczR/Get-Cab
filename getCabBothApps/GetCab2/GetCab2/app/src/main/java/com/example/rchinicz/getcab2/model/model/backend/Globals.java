package com.example.rchinicz.getcab2.model.model.backend;
import com.example.rchinicz.getcab2.model.model.datasource.FireBase;
import com.example.rchinicz.getcab2.model.model.entities.Cabbie;

public class Globals
{
    public static IBackEnd backEnd = new FireBase();
    public static Cabbie cabbie;
}
