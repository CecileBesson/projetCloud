package com.polytech.cloud.exceptions;

public class UserToDeleteDoesNotExistException extends Exception {

    public UserToDeleteDoesNotExistException(String message)
    {
        super(message);
    }
}
