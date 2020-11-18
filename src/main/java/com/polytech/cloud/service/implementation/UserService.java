package com.polytech.cloud.service.implementation;

import com.polytech.cloud.entities.*;
import com.polytech.cloud.exceptions.IncorrectlyFormedUserException;
import com.polytech.cloud.io.UsersReader;
import com.polytech.cloud.repository.*;
import com.polytech.cloud.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidParameterException;
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
     * Gets all the users
     * @return all the users
     */
    @Override
    public List<UserEntity> findAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public UserEntity findByIdUser(String id) {
        return this.userRepository.findById(id);
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
     * @param user the user to create
     * @throws InvalidParameterException if the attributes of the user are not correct.
     */
    public void createUser(UserEntity user){
        // check attributes validity
        if(user.getFirstName() == null) {
            throw new InvalidParameterException("You must fill in a first name.");
        }
        if(user.getLastName() == null) {
            throw new InvalidParameterException("You must fill in a last name.");
        }
        if(user.getBirthDay() == null) {
            throw new InvalidParameterException("You must fill in a birthday date.");
        }

        this.userRepository.save(user);
    }

    /**
     * Updates a user
     *
     * @param userToUpdateId the id of user to update
     * @param updatedUser the user containing the new values
     */
    public void updateUser(String userToUpdateId, UserEntity updatedUser){
        UserEntity userToUpdate = this.getUserById(userToUpdateId);
        if(updatedUser.getFirstName() != null) {
            userToUpdate.setFirstName(updatedUser.getFirstName());
        }
        if(updatedUser.getLastName() != null) {
            userToUpdate.setLastName(updatedUser.getLastName());
        }
        if(updatedUser.getBirthDay() != null) {
            userToUpdate.setBirthDay(updatedUser.getBirthDay());
        }
        this.userRepository.save(userToUpdate);
    }


    public UserEntity getUserById(String id) {
        return userRepository.findById(id);
    }

}
