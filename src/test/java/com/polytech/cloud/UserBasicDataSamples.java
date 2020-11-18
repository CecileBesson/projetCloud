package com.polytech.cloud;

import com.polytech.cloud.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class UserBasicDataSamples {

    protected List<UserEntity> myUsers;
    protected UserEntity user0 = new UserEntity();
    protected UserEntity user1 = new UserEntity();
    protected UserEntity user2 = new UserEntity();

    @BeforeEach
    private void setUp(){

        user0.setFirstName("Dorian");
        user1.setFirstName("Myriam");
        user2.setFirstName("Titi");

        myUsers = Stream.of(user0, user1, user2).collect(Collectors.toList());
    }
}
