package com.polytech.cloud.controller;

import com.polytech.cloud.entities.UserEntity;
import com.polytech.cloud.service.implementation.UserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return no content http response
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(@RequestBody UserEntity user) {
        this.userService.createUser(user);
        return ResponseEntity.noContent().build();
    }

    /**
     * Updates the current user.
     *
     * @param user the user to update
     * @return no content http response
     */
    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateUser(@RequestBody UserEntity user) {
        this.userService.updateUser(user.getId(), user);
        return ResponseEntity.noContent().build();
    }

}
