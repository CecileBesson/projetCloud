package com.polytech.cloud.io;

import com.google.gson.*;
import com.polytech.cloud.entities.PositionEntity;
import com.polytech.cloud.entities.UserEntity;
import com.polytech.cloud.exceptions.IncorrectlyFormedUserException;
import org.springframework.beans.factory.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsersReader {

    private Resource usersJson;

    @Autowired
    public UsersReader(@Value(value = "classpath:/data/data.json") Resource users) {
        this.usersJson = users;
    }

    public List<UserEntity> getUsersEntityListFromResourceFile() throws IOException, IncorrectlyFormedUserException {
        List<UserEntity> usersList = new ArrayList<UserEntity>();

        JsonParser parser = new JsonParser();
        InputStream inputStream = this.usersJson.getInputStream();
        Reader reader = new InputStreamReader(inputStream);

        JsonArray allUsers = (JsonArray) parser.parse(reader);

        for (JsonElement jElement : allUsers) {
            usersList.add(this.initializeUserFromJElement(jElement));
        }
        return usersList;
    }

    private UserEntity initializeUserFromJElement(JsonElement jElement) throws IncorrectlyFormedUserException {
        UserEntity currentUser = new UserEntity();
        currentUser.setPositionByFkPosition(new PositionEntity());
        try {
            JsonObject currentObject = jElement.getAsJsonObject();
            JsonObject position = currentObject.get("position").getAsJsonObject();
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String birthDayStr = currentObject.get("birthDay").getAsString();

            String firstName = currentObject.get("firstName").getAsString();
            String lastName = currentObject.get("lastName").getAsString();
            java.util.Date birthDay = format.parse(birthDayStr);
            BigDecimal longitude = position.get("lon").getAsBigDecimal();
            BigDecimal latitude = position.get("lat").getAsBigDecimal();

            currentUser.setFirstName(firstName);
            currentUser.setLastName(lastName);
            currentUser.setBirthDay(new java.sql.Date(birthDay.getTime()));
            currentUser.getPositionByFkPosition().setLon(longitude);
            currentUser.getPositionByFkPosition().setLat(latitude);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IncorrectlyFormedUserException("One of the given users did not have the expected format.");
        }
        return currentUser;
    }
}
