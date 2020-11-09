package com.polytech.cloud.service.interfaces;

import com.polytech.cloud.exceptions.IncorrectlyFormedUserException;

import java.io.IOException;

public interface IUserService {

    public void saveAllRandomUsersToDatabase() throws IOException, IncorrectlyFormedUserException;
}
