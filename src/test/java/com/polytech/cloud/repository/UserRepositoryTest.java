package com.polytech.cloud.repository;

import com.polytech.cloud.UserDataSamples;
import com.polytech.cloud.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestPropertySource(locations="classpath:application-test.properties")
@DataJpaTest
@AutoConfigureTestDatabase
class UserRepositoryTest extends UserDataSamples {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PositionRepository positionRepository;


    @Test
    void findById() throws Exception {
        userRepository.save(user1);
        userRepository.save(user2);


        assertEquals(user1.getFirstName(), userRepository.findById(user1.getId()).getFirstName());
        assertEquals(user1.getLastName(), userRepository.findById(user1.getId()).getLastName());
        assertEquals(user1.getPositionByFkPosition(), userRepository.findById(user1.getId()).getPositionByFkPosition());

        assertEquals(user2.getFirstName(), userRepository.findById(user2.getId()).getFirstName());
        assertEquals(user2.getLastName(), userRepository.findById(user2.getId()).getLastName());
        assertEquals(user2.getPositionByFkPosition(), userRepository.findById(user2.getId()).getPositionByFkPosition());

    }

    @Test
    void findByLastName() throws Exception {
        userRepository.save(user1);

        Pageable paging =  PageRequest.of(0, 100);
        assertEquals(user1, userRepository.findByLastName(user1.getLastName(), paging).get(0));

    }


    //todo : test any custom repository method here
}