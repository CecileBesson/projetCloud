package com.polytech.cloud.service.implementation;

import com.polytech.cloud.entities.*;
import com.polytech.cloud.exceptions.IncorrectlyFormedUserException;
import com.polytech.cloud.io.UsersReader;
import com.polytech.cloud.repository.*;
import com.polytech.cloud.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
     * Gets all the users.
     *
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
    public UserEntity findByIdUser(String id) {
        return this.userRepository.findById(id);
    }

    /**
     * Delete a user by his id.
     * @param id the id of the user to delete.
     */
    @Override
    public void deleteAUserById(int id) {
        this.userRepository.deleteAllById(id);
    }

    @Override
    public void saveAllRandomUsersToDatabase() throws IOException, IncorrectlyFormedUserException {
        this.userRepository.deleteAll();
        List<UserEntity> randomUsers = this.usersReader.getUsersEntityListFromResourceFile();
        List<PositionEntity> positions = randomUsers.stream().map(UserEntity::getPositionByFkPosition).collect(Collectors.toList());
        this.positionRepository.saveAll(positions);
        this.userRepository.saveAll(randomUsers);
    }

}
