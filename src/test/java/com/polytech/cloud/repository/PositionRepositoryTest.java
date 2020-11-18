package com.polytech.cloud.repository;

import com.polytech.cloud.UserDataSamples;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource(locations="classpath:application-test.properties")
@DataJpaTest
class PositionRepositoryTest extends UserDataSamples {

    //todo : test any custom repository method here
}