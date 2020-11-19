package com.polytech.cloud.service;

import com.polytech.cloud.UserBasicDataSamples;
import com.polytech.cloud.entities.PositionEntity;
import com.polytech.cloud.entities.UserEntity;
import com.polytech.cloud.exceptions.*;
import com.polytech.cloud.repository.PositionRepository;
import com.polytech.cloud.repository.UserRepository;
import com.polytech.cloud.service.implementation.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest extends UserBasicDataSamples {

    @Mock
    UserRepository userRepository;

    @Mock
    PositionRepository positionRepository;

    @InjectMocks
    UserService userService;


    @Test
    void getTest() {
        when(userRepository.findAll()).thenReturn(myUsers);

        assertEquals(myUsers, userService.get());

        System.out.println("✔ findAll");
    }

    @Test
    void getByIdTest() throws UserToGetDoesNotExistException, StringIdExceptionForGetException {

        when(userRepository.findById("1")).thenReturn(user0);

        assertEquals(user0, userService.getById("1"));

        assertThrows(UserToGetDoesNotExistException.class, () ->
        {
            userService.getById("4");
        });

        System.out.println("✔ findById");
    }

    @Test
    void putByIdTest() throws ReplacePutException, IncorrectlyFormedUserException, UserToGetDoesNotExistException, StringIdExceptionForGetException {

        user0.setId("0");
        when(userRepository.findById("0")).thenReturn(user0);

        assertEquals(user0, userRepository.findById("0"));
        assertNull(user0.getLastName());


        Assert.assertThrows(IncorrectlyFormedUserException.class, () ->
        {
            userService.putById("0", user0);
        });

        user0.setBirthDay(Date.valueOf(LocalDate.now()));
        user0.setLastName("Benali");
        PositionEntity pos = new PositionEntity();
        pos.setLat(new BigDecimal(30));
        pos.setLon(new BigDecimal(50));
        user0.setPositionByFkPosition(pos);


        userService.putById("0", user0);
        assertEquals(user0, userService.getById("0"));

        System.out.println("✔ replace");
    }

    @Test
    void putTest() throws ReplaceAllPutException, IncorrectlyFormedUserException {

        when(userRepository.findAll()).thenReturn(myUsers);
        assertEquals(myUsers, userService.get());

        doNothing().when(userRepository).deleteAll();
        doNothing().when(positionRepository).deleteAll();

        Assert.assertThrows(IncorrectlyFormedUserException.class, () ->
        {
            userService.put(myUsers);
        });

        PositionEntity pos = new PositionEntity();
        pos.setLat(new BigDecimal(30));
        pos.setLon(new BigDecimal(50));

        for(UserEntity user : myUsers)
        {
            user.setPositionByFkPosition(pos);
            user.setBirthDay(Date.valueOf(LocalDate.now()));
        }

        Assert.assertThrows(IncorrectlyFormedUserException.class, () ->
        {
            userService.put(myUsers);
        });

        user0.setLastName("NAAJI");
        user1.setLastName("BENALI");
        user2.setLastName("BENALI");

        userService.put(myUsers);

        assertEquals(myUsers, userService.get());

        System.out.println("✔ put");
    }

    @Test
    void deleteAllTest() throws Exception {

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                myUsers.clear();
                return null;
            }
        }).when(this.userRepository).deleteAll();


        Assert.assertEquals(3, myUsers.size());
        this.userRepository.deleteAll();
        assertEquals(0, myUsers.size());

        System.out.println("✔ delete");
    }

    @Test
    void deleteTest() throws Exception {

       // when()
        throw new Exception();
        //System.out.println("✔ delete");
    }

    // post
    @Test
    void postTest() throws Exception {

        //todo
        throw new Exception();
        //System.out.println("✔ create");
    }
}