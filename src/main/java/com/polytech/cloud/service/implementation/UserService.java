package com.polytech.cloud.service.implementation;


import com.polytech.cloud.entities.UserEntity;
import com.polytech.cloud.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.security.InvalidParameterException;


@Service("UserService")
public class UserService {

    private UserRepository userRepository;

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
