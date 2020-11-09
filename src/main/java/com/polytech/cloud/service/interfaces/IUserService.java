package com.polytech.cloud.service.interfaces;

import com.polytech.cloud.entities.UserEntity;

import java.util.List;

public interface IUserService {

    List<UserEntity> findAllUsers();

    UserEntity findByIdUser(String id);
}
