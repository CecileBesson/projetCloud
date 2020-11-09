package com.polytech.cloud.controller;

import com.polytech.cloud.entities.UserEntity;
import com.polytech.cloud.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return new ResponseEntity<List<UserEntity>>(this.userService.findAllUsers(), HttpStatus.OK);
    }


    /**
     * Retrieve a user by his id
     * @param id users id
     * @return a specific user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserEntity> getAUser(
            @PathVariable String id) {
        return new ResponseEntity<UserEntity>(this.userService.findByIdUser(id), HttpStatus.OK);
    }
}
