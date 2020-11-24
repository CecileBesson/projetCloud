package com.polytech.cloud.service.interfaces;

import com.polytech.cloud.entities.UserEntity;
import com.polytech.cloud.exceptions.*;

import java.io.IOException;
import java.util.List;

public interface IUserService {

    public List<UserEntity> get();

    public void put(List<UserEntity> users) throws ReplaceAllPutException, IncorrectlyFormedUserException;

    public void delete() throws DeleteAllException;

    public UserEntity getById(String idString) throws UserToGetDoesNotExistException, StringIdExceptionForGetException;

    public void post(UserEntity newUser) throws CreatePostException, IncorrectlyFormedUserException;

    public void putById(String userToUpdateId, UserEntity updatedUser) throws IncorrectlyFormedUserException, ReplacePutException;

    public void deleteById(String idString) throws UserToDeleteDoesNotExistException, StringIdExceptionForDelete;

    public List<UserEntity> findByLastName(String lastName, Integer page, Integer pageSize);

    public List<UserEntity> getFirst10NearestUsers(Double lat, Double lon);

    public void insertRandomUsersIntoDatabase() throws IOException, IncorrectlyFormedUserException;

}
