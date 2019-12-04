package com.example.rchinicz.getcab.model.backend;

public interface IAction<T>
{
    void onSuccess(T obj);
    void onFailure(Exception exception);
}
