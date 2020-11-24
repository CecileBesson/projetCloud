package com.polytech.cloud;

import com.polytech.cloud.entities.PositionEntity;
import com.polytech.cloud.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class UserBasicDataSamples {

    protected List<UserEntity> myUsers;
    protected UserEntity user0 = new UserEntity();
    protected UserEntity user1 = new UserEntity();
    protected UserEntity user2 = new UserEntity();

    protected List<UserEntity> myOtherUsers;
    protected UserEntity user3 = new UserEntity();
    protected UserEntity user4 = new UserEntity();
    protected UserEntity user5 = new UserEntity();


    @BeforeEach
    private void setUp(){

        user0.setFirstName("Dorian");
        user1.setFirstName("Myriam");
        user2.setFirstName("Titi");

        user3.setLastName("Na");
        PositionEntity positionEntityUser3 = new PositionEntity();
        positionEntityUser3.setLat(BigDecimal.valueOf(42.5));
        positionEntityUser3.setLon(BigDecimal.valueOf(18.6));
        user3.setPositionByFkPosition(positionEntityUser3);

        user4.setLastName("Na");
        PositionEntity positionEntityUser4 = new PositionEntity();
        positionEntityUser4.setLat(BigDecimal.valueOf(10.3));
        positionEntityUser4.setLon(BigDecimal.valueOf(5.6));
        user4.setPositionByFkPosition(positionEntityUser4);

        user5.setLastName("Na");
        PositionEntity positionEntityUser5 = new PositionEntity();
        positionEntityUser5.setLat(BigDecimal.valueOf(1.2));
        positionEntityUser5.setLon(BigDecimal.valueOf(85.2));
        user5.setPositionByFkPosition(positionEntityUser5);

        myUsers = Stream.of(user0, user1, user2).collect(Collectors.toList());

        myOtherUsers = Stream.of(user3, user4, user5).collect(Collectors.toList());
    }
}
