package com.polytech.cloud;

import com.polytech.cloud.entities.PositionEntity;
import com.polytech.cloud.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class UserDataSamples {

    protected List<UserEntity> myUsers;
    protected UserEntity user1 = new UserEntity();
    protected UserEntity user2 = new UserEntity();
    protected UserEntity user3 = new UserEntity();

    @BeforeEach
    private void setUp(){

        PositionEntity pos1 = new PositionEntity();
        pos1.setLon(new BigDecimal(31.323276));
        pos1.setLat(new BigDecimal(59.014005));

        PositionEntity pos2 = new PositionEntity();
        pos2.setLon(new BigDecimal(5.994693));
        pos2.setLat(new BigDecimal(175.826895));

        PositionEntity pos3 = new PositionEntity();
        pos3.setLon(new BigDecimal(-31.177123));
        pos3.setLat(new BigDecimal(-100.68559));

        user1.setFirstName("'تارا");
        user1.setLastName("سلطانی ");
        user1.setBirthDay(new Date(1963, 8, 8));
        user1.setPositionByFkPosition(pos1);

        user2.setFirstName("Ali");
        user2.setLastName("'Özkök'");
        user2.setBirthDay(new Date(1970, 7, 6));
        user2.setPositionByFkPosition(pos2);

        user3.setFirstName("Lola");
        user3.setLastName("Kim");
        user3.setBirthDay(new Date(1984, 5, 10));
        user3.setPositionByFkPosition(pos3);


        myUsers = Stream.of(user1, user2, user3).collect(Collectors.toList());
    }
}
