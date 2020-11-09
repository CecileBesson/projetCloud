package com.polytech.cloud.service.implementation;

import com.polytech.cloud.entities.UserEntity;
import com.polytech.cloud.repository.UserRepository;
import com.polytech.cloud.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
