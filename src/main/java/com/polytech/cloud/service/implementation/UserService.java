package com.polytech.cloud.service.implementation;

import com.polytech.cloud.entities.*;
import com.polytech.cloud.exceptions.*;
import com.polytech.cloud.io.UsersReader;
import com.polytech.cloud.repository.*;
import com.polytech.cloud.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.math.BigDecimal;
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
     * Gets all the users.
     * @return all the users
     */
    @Override
    public List<UserEntity> findAllUsers() {
        return this.userRepository.findAll();
    }

    /**
     * Get a user by his id.
     * @param id the id of the user to retrieve.
     * @return a specific user.
     */
    @Override
    public UserEntity findByIdUser(int id) {
        return this.userRepository.findById(id);
    }

    @Transactional
    public void replaceAll(List<UserEntity> users) throws ReplaceAllPutException, IncorrectlyFormedUserException {
        for (UserEntity user : users) {
            this.checkIfUserIsCorrectlyFormed(user);
        }
        try {
            this.userRepository.deleteAll();
            this.userRepository.resetAutoIncrementSeed();
            this.positionRepository.deleteAll();
            this.positionRepository.resetAutoIncrementSeed();
            this.userRepository.saveAll(users);
        } catch (Exception e) {
            throw new ReplaceAllPutException("Could not save all the given users with PUT. : " + e.getMessage());
        }

    }

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

    @Override
    public void deleteAll() throws DeleteAllException {
        try {
            this.userRepository.deleteAll();
        } catch (Exception e) {
            throw new DeleteAllException("Users could not be deleted from the database");
        }
    }

    /**
     * Delete a user by his id.
     * @param idString the id of the user to delete.
     */
    @Override
    public void deleteAUserById(String idString) throws UserToDeleteDoesNotExistException, UserIdIsAStringException {

        List<UserEntity> userEntities = this.userRepository.findAll();

        try {
            int id = Integer.parseInt(idString);

            boolean doesUserToRemoveExist = false;

            for (UserEntity userEntity : userEntities) {
                if(userEntity.getId() == id) {
                    doesUserToRemoveExist = true;
                }
                break;
            }

            if(doesUserToRemoveExist) {
                this.userRepository.deleteAllById(id);
            }
            else {
                throw new UserToDeleteDoesNotExistException("User does not exist into the database");
            }
        } catch (NumberFormatException e) {
            throw new UserIdIsAStringException("Error : the user id is a string");
        }



    }

    @Override
    public void saveAllRandomUsersToDatabase() throws IOException, IncorrectlyFormedUserException {
        this.userRepository.deleteAll();
        List<UserEntity> randomUsers = this.usersReader.getUsersEntityListFromResourceFile();
        List<PositionEntity> positions = randomUsers.stream().map(UserEntity::getPositionByFkPosition).collect(Collectors.toList());
        this.positionRepository.saveAll(positions);
        this.userRepository.saveAll(randomUsers);
    }

    /**
     * Creates a new user.
     *
     * @param newUser the user to create
     * @throws InvalidParameterException if the attributes of the user are not correct.
     */
    public void createUser(UserEntity newUser) throws CreatePostException, IncorrectlyFormedUserException {
        // check attributes validity
        checkIfUserIsCorrectlyFormed(newUser);
        try{
            this.userRepository.save(newUser);

        }catch(Exception e){
            throw new CreatePostException("Could not save given user with PUT. :" + e.getMessage());
        }

    }

    /**
     * Updates a user
     *
     * @param userToUpdateId the id of user to update
     * @param updatedUser    the user containing the new values
     */
    public void updateUser(int userToUpdateId, UserEntity updatedUser) throws IncorrectlyFormedUserException, ReplacePutException {
        UserEntity userToUpdate = this.getUserById(userToUpdateId);
        // check attributes validity
        checkIfUserIsCorrectlyFormed(updatedUser);
        try {
            this.userRepository.save(userToUpdate);
        } catch(Exception e){
            throw new ReplacePutException("Could not update given user with POST. : " + e.getMessage());
        }
    }


    public UserEntity getUserById(int id) {
        return userRepository.findById(id);
    }

}
