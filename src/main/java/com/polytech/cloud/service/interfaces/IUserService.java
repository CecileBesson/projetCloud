package com.polytech.cloud.service.interfaces;

import com.polytech.cloud.entities.UserEntity;
import com.polytech.cloud.exceptions.DeleteAllException;
import com.polytech.cloud.exceptions.IncorrectlyFormedUserException;
import com.polytech.cloud.exceptions.ReplaceAllPutException;
import com.polytech.cloud.exceptions.ReplacePutException;

import java.io.IOException;
import java.util.List;

public interface IUserService {

    public List<UserEntity> findAllUsers();

    public UserEntity findByIdUser(int id);

    void deleteAUserById(int id);
    
    public void saveAllRandomUsersToDatabase() throws IOException, IncorrectlyFormedUserException, IOException, IncorrectlyFormedUserException;

    public void deleteAll() throws DeleteAllException;


    public void replaceAll(List<UserEntity> users) throws ReplaceAllPutException, IncorrectlyFormedUserException;
}
