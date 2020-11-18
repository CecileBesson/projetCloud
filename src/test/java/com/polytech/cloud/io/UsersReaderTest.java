package com.polytech.cloud.io;

import com.polytech.cloud.entities.UserEntity;
import com.polytech.cloud.exceptions.IncorrectlyFormedUserException;
import org.junit.Assert;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
public class UsersReaderTest {

    @Autowired
    UsersReader reader;

    /**
     * Checks that users were loaded correctly.
     * @throws IOException
     * @throws IncorrectlyFormedUserException
     * See https://github.com/Slashgear/cloud-random-user
     */
    @Test
    public void getUsersEntityListFromResourceFile() throws IOException, IncorrectlyFormedUserException {
        List<UserEntity> users = reader.getUsersEntityListFromResourceFile();
        // 300 users were generated with the
        Assert.assertEquals(5000, users.size());
    }
}