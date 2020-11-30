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
    protected UserEntity user3 = new UserEntity();

    protected List<UserEntity> myOtherUsers;
    protected UserEntity user4 = new UserEntity();
    protected UserEntity user5 = new UserEntity();
    protected UserEntity user6 = new UserEntity();

    protected List<UserEntity> myUserPage1Gt20;
    protected UserEntity user7 = new UserEntity();
    protected UserEntity user8 = new UserEntity();

    protected List<UserEntity> myUserPage1Eq20;
    protected UserEntity user9 = new UserEntity();
    protected UserEntity user10 = new UserEntity();

    protected List<UserEntity> myUserPage;
    protected UserEntity user11 = new UserEntity();
    protected UserEntity user12 = new UserEntity();


    @BeforeEach
    private void setUp(){

        user0.setFirstName("Dorian");
        user1.setFirstName("Myriam");
        user2.setFirstName("Titi");
        user3.setFirstName("Toto");


        user4.setLastName("Na");
        PositionEntity positionEntityUser3 = new PositionEntity();
        positionEntityUser3.setLat(BigDecimal.valueOf(42.5));
        positionEntityUser3.setLon(BigDecimal.valueOf(18.6));
        user4.setPositionByFkPosition(positionEntityUser3);

        user5.setLastName("Na");
        PositionEntity positionEntityUser4 = new PositionEntity();
        positionEntityUser4.setLat(BigDecimal.valueOf(10.3));
        positionEntityUser4.setLon(BigDecimal.valueOf(5.6));
        user5.setPositionByFkPosition(positionEntityUser4);

        user6.setLastName("Na");
        PositionEntity positionEntityUser5 = new PositionEntity();
        positionEntityUser5.setLat(BigDecimal.valueOf(1.2));
        positionEntityUser5.setLon(BigDecimal.valueOf(85.2));
        user6.setPositionByFkPosition(positionEntityUser5);

        user7.setId("001b845e-c00d-4b7a-971c-d44754e9a8d6");
        user8.setId("001e66fe-b408-49b5-bd78-95e306243717");
        user9.setId("27590191-8189-4152-a5ac-1ead0a53c300");
        user10.setId("32885155-95c0-4653-8600-baf3ded99674");
        user11.setId("001b845e-c00d-4b7a-971c-d44754e9a8d6");
        user12.setId("001e66fe-b408-49b5-bd78-95e306243717");



        myUsers = Stream.of(user0, user1, user2, user3).collect(Collectors.toList());

        myOtherUsers = Stream.of(user4, user5, user6).collect(Collectors.toList());

        myUserPage1Gt20 = Stream.of(user7, user8).collect(Collectors.toList());

        myUserPage1Eq20 = Stream.of(user9, user10).collect(Collectors.toList());

        myUserPage = Stream.of(user11, user12).collect(Collectors.toList());

    }
}
