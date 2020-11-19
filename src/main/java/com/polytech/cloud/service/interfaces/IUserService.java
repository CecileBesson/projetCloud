package com.polytech.cloud.service.interfaces;

import com.polytech.cloud.entities.UserEntity;
import com.polytech.cloud.exceptions.*;

import java.io.IOException;
import java.util.List;

public interface IUserService {

    public List<UserEntity> findAllUsers();

    UserEntity findByIdUser(String idString) throws UserToGetDoesNotExistException, StringIdExceptionForGetException;

    void deleteAUserById(String id) throws UserToDeleteDoesNotExistException, StringIdExceptionForDelete;
    
    public void saveAllRandomUsersToDatabase() throws IOException, IncorrectlyFormedUserException, IOException, IncorrectlyFormedUserException;

    public void deleteAll() throws DeleteAllException;


    public void replaceAll(List<UserEntity> users) throws ReplaceAllPutException, IncorrectlyFormedUserException;
}
