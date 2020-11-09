package com.polytech.cloud.service.interfaces;

import com.polytech.cloud.entities.UserEntity;

import java.util.List;

public interface IUserService {

    public List<UserEntity> findAllUsers();

    public serEntity findByIdUser(String id);
    
    public void saveAllRandomUsersToDatabase() throws IOException, IncorrectlyFormedUserException;
}
