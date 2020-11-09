package com.polytech.cloud.service.implementation;

import com.polytech.cloud.entities.PositionEntity;
import com.polytech.cloud.entities.UserEntity;
import com.polytech.cloud.exceptions.IncorrectlyFormedUserException;
import com.polytech.cloud.io.UsersReader;
import com.polytech.cloud.repository.PositionRepository;
import com.polytech.cloud.repository.UserRepository;
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

    @Override
    public void saveAllRandomUsersToDatabase() throws IOException, IncorrectlyFormedUserException {
        this.userRepository.deleteAll();
        List<UserEntity> randomUsers = this.usersReader.getUsersEntityListFromResourceFile();
        List<PositionEntity> positions = randomUsers.stream().map(UserEntity::getPositionByFkPosition).collect(Collectors.toList());
        this.positionRepository.saveAll(positions);
        this.userRepository.saveAll(randomUsers);
    }
}
