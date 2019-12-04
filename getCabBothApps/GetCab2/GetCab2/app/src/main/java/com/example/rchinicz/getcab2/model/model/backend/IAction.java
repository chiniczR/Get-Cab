package com.example.rchinicz.getcab2.model.model.backend;

public interface IAction<T>
{
    void onSuccess(T obj);
    void onFailure(Exception exception);
}
