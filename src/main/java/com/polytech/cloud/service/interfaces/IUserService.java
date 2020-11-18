package com.polytech.cloud.service.interfaces;

import com.polytech.cloud.entities.UserEntity;
import com.polytech.cloud.exceptions.IncorrectlyFormedUserException;

import java.io.IOException;
import java.util.List;

public interface IUserService {

    public List<UserEntity> findAllUsers();

    public UserEntity findByIdUser(String id);

    void deleteAUserById(int id);
    
    public void saveAllRandomUsersToDatabase() throws IOException, IncorrectlyFormedUserException, IOException, IncorrectlyFormedUserException;
}
