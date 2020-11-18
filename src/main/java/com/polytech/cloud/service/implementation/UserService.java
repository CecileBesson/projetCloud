package com.polytech.cloud.service.implementation;

import com.polytech.cloud.entities.*;
import com.polytech.cloud.exceptions.DeleteAllException;
import com.polytech.cloud.exceptions.IncorrectlyFormedUserException;
import com.polytech.cloud.exceptions.ReplaceAllPutException;
import com.polytech.cloud.exceptions.ReplacePutException;
import com.polytech.cloud.io.UsersReader;
import com.polytech.cloud.repository.*;
import com.polytech.cloud.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
     * Gets all the users
     *
     * @return all the users
     */
    @Override
    public List<UserEntity> findAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public UserEntity findByIdUser(int id) {
        return this.userRepository.findById(id);
    }

    public void replace(UserEntity user) throws IncorrectlyFormedUserException, ReplacePutException {
        this.checkIfUserIsCorrectlyFormed(user);
        try {
            this.userRepository.save(user);
        } catch (Exception e) {
            throw new ReplacePutException("Could not save th given user with PUT. : " + e.getMessage());
        }
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
            throw new IncorrectlyFormedUserException("Some of the users fields are null or valid");
        }

        BigDecimal lat = ue.getPositionByFkPosition().getLat();
        BigDecimal lon = ue.getPositionByFkPosition().getLon();

        boolean allNotNull = ue.getFirstName() != null && ue.getLastName() != null && ue.getBirthDay() != null
                && ue.getPositionByFkPosition() != null
                && ue.getPositionByFkPosition().getLon() != null
                && ue.getPositionByFkPosition().getLat() != null;
        if (!allNotNull) {
            throw new IncorrectlyFormedUserException("Some of the users fields are null or valid");
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

    @Override
    public void saveAllRandomUsersToDatabase() throws IOException, IncorrectlyFormedUserException {
        this.userRepository.deleteAll();
        List<UserEntity> randomUsers = this.usersReader.getUsersEntityListFromResourceFile();
        List<PositionEntity> positions = randomUsers.stream().map(UserEntity::getPositionByFkPosition).collect(Collectors.toList());
        this.positionRepository.saveAll(positions);
        this.userRepository.saveAll(randomUsers);
    }

}
