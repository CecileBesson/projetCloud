package com.polytech.cloud.service.implementation;

import com.polytech.cloud.entities.*;
import com.polytech.cloud.exceptions.*;
import com.polytech.cloud.io.UsersReader;
import com.polytech.cloud.repository.*;
import com.polytech.cloud.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    private UserRepository userRepository;

    private PositionRepository positionRepository;

    private UsersReader usersReader;

    @Autowired
    public UserService(UserRepository userRepository, UsersReader usersReader, PositionRepository positionRepository) {
        this.userRepository = userRepository;
        this.positionRepository = positionRepository;
        this.usersReader = usersReader;
    }

    /**
     * GET /user -> retourne tous les utilisateurs.
     *
     * @return tous les utilisateurs en BD.
     */
    @Override
    public List<UserEntity> get() {
        return this.userRepository.findAll();
    }

    /**
     * PUT /user
     *
     * @param users the new list that will replace the old one.
     */
    public void put(List<UserEntity> users) throws ReplaceAllPutException, IncorrectlyFormedUserException {
        for (UserEntity user : users) {
            this.checkIfUserIsCorrectlyFormed(user);
        }
        try {
            this.userRepository.deleteAll();
            this.positionRepository.deleteAll();
            this.userRepository.saveAll(users);
        } catch (Exception e) {
            throw new ReplaceAllPutException("Could not save all the given users with PUT. : " + e.getMessage());
        }
    }

    /**
     * DELETE /user
     */
    @Override
    public void delete() throws DeleteAllException {
        try {
            this.userRepository.deleteAll();
        } catch (Exception e) {
            throw new DeleteAllException("Users could not be deleted from the database");
        }
    }

    /**
     * GET /user/{id}
     *
     * @param id id of the user that will be retrived from the DB.
     */
    @Override
    public UserEntity getById(String id) throws UserToGetDoesNotExistException, StringIdExceptionForGetException {

        if (this.doesUserExist(id)) {
            return this.userRepository.findById(id);
        } else {
            throw new UserToGetDoesNotExistException("User does not exist into the database");
        }
    }

    /**
     * POST /user
     *
     * @param newUser the user that will be added into the DB.
     */
    @Override
    public void post(UserEntity newUser) throws CreatePostException, IncorrectlyFormedUserException {
        // check attributes validity
        checkIfUserIsCorrectlyFormed(newUser);
        try {
            this.userRepository.save(newUser);

        } catch (Exception e) {
            throw new CreatePostException("Could not save given user with PUT. :" + e.getMessage());
        }

    }

    /**
     * PUT /user/{id}
     *
     * @param userToUpdateId the id of the user that will be updated.
     * @param updatedUser    the user with updates.
     */
    public void putById(String userToUpdateId, UserEntity updatedUser) throws IncorrectlyFormedUserException, ReplacePutException {
        UserEntity userToUpdate;

        try {
            userToUpdate = this.getById("" + userToUpdateId);
        } catch (UserToGetDoesNotExistException | StringIdExceptionForGetException e) {
            throw new ReplacePutException("Could not update given user with POST. :" + e.getMessage());
        }

        // check attributes validity
        checkIfUserIsCorrectlyFormed(userToUpdate);
        try {
            this.userRepository.save(userToUpdate);
        } catch (Exception e) {
            throw new ReplacePutException("Could not update given user with POST. : " + e.getMessage());
        }
    }

    /**
     * DELETE /user/{id}
     *
     * @param id the id of the user to delete.
     */
    @Override
    public void deleteById(String id) throws UserToDeleteDoesNotExistException, StringIdExceptionForDelete {

        if (this.doesUserExist(id)) {
            this.userRepository.deleteById(id);
        } else {
            throw new UserToDeleteDoesNotExistException("User does not exist into the database");

        }
    }

    /**
     * Get 100 first users corresponding to the page number
     *
     * @param page
     * @param pageSize
     * @return the users corresponding to the page number
     */
    public List<UserEntity> getUsers(Integer page, Integer pageSize) {
        Pageable paging =  PageRequest.of(page, pageSize);
        Page<UserEntity> pagedResult = userRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<UserEntity>();
        }
    }

    /**
     *  Get 100 first users with age > eq
     * @param eq age
     * @return the users with age = eq
     * @throws UserToGetDoesNotExistException
     */
    public List<UserEntity> getUsersByAge(Integer eq) throws UserToGetDoesNotExistException {
        if(this.userRepository.findByAge(eq).isEmpty()){
            throw new UserToGetDoesNotExistException("There is no user where age =" + eq);
        }else{
            return this.userRepository.findByAge(eq);
        }
    }

    /**
     * Inserts 5k random users into the database from the "data/data.json" resource file.
     */
    @Override
    public void insertRandomUsersIntoDatabase() throws IOException, IncorrectlyFormedUserException {
        this.userRepository.deleteAll();
        List<UserEntity> randomUsers = this.usersReader.getUsersEntityListFromResourceFile();
        List<PositionEntity> positions = randomUsers.stream().map(UserEntity::getPositionByFkPosition).collect(Collectors.toList());
        this.positionRepository.saveAll(positions);
        this.userRepository.saveAll(randomUsers);
    }

    /**
     * Checks if rules for a given user are respected (not null, longitude, latitude, etc.)
     *
     * @param ue the user on which the checks will be done.
     */
    private void checkIfUserIsCorrectlyFormed(UserEntity ue) throws IncorrectlyFormedUserException {
        if (ue == null || ue.getPositionByFkPosition() == null) {
            throw new IncorrectlyFormedUserException("Some of the users fields are null or invalid");
        }

        BigDecimal lat = ue.getPositionByFkPosition().getLat();
        BigDecimal lon = ue.getPositionByFkPosition().getLon();

        boolean allNotNull = ue.getFirstName() != null && ue.getLastName() != null && ue.getBirthDay() != null
                && ue.getPositionByFkPosition() != null
                && ue.getPositionByFkPosition().getLon() != null
                && ue.getPositionByFkPosition().getLat() != null;
        if (!allNotNull) {
            throw new IncorrectlyFormedUserException("Some of the users fields are null or invalid");
        }


        boolean coordinatesDoesNotExceedLimit = ((lat.intValue() >= -90 && lat.intValue() <= 90)
                && (lon.intValue() >= -180 && lon.intValue() <= 180));
        if (!coordinatesDoesNotExceedLimit) {
            throw new IncorrectlyFormedUserException("Some coordinates exceed the limits.");
        }
    }

    /**
     * Returns true if the user does exist into the DB. False otherwise.
     */
    private boolean doesUserExist(String id) {
        return this.userRepository.findById(id) != null;
    }

}
