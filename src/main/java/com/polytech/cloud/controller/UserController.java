package com.polytech.cloud.controller;

import com.polytech.cloud.entities.UserEntity;
import com.polytech.cloud.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.polytech.cloud.exceptions.IncorrectlyFormedUserException;
import com.polytech.cloud.responses.*;

import java.io.IOException;

import static com.polytech.cloud.utils.ControllerExceptionBuilder.buildErrorResponseAndPrintStackTrace;

@RestController
@RequestMapping("/user")
public class UserController {

    //todo : GET /user -> retourne tous les utilisateurs
    // PUT /user -> permet de remplacer la collection entière par une nouvelle liste d'utilisateur
    // DELETE /user -> supprime toute la collection des utilisateurs
    //attention au codes retour http.

    //todo : GET /user/{id} -> retourne l'utilisateur correspondant
    // POST /user -> ajoute un nouvel utilisateur passé en paramètre
    // PUT /user/{id} -> met à jour l'utilisateur
    // DELETE /user/{id} -> supprime l'utilisateur correspondant


    private final UserService userService;

    @Autowired
    public UserController(UserService service){
        this.userService = service;
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

    /**
     * Delete a user by his id.
     * @param id user id.
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable int id) {
        this.userService.deleteAUserById(id);
        Success success = new Success(HttpStatus.OK, "The user n°" + id + " has been correctly deleted.");
        return new ResponseEntity<ApiResponse>(success, HttpStatus.OK);
    }


    /**
     * This operation is destructive. It removes all users and adds random ones into the database.
     * @return
     * @throws IOException
     * @throws IncorrectlyFormedUserException
     */
    @RequestMapping(value ="/dev/insert-random", method = RequestMethod.PUT)
    public ResponseEntity<ApiResponse> saveRandomUsersToDatabase() throws IOException, IncorrectlyFormedUserException {
        this.userService.saveAllRandomUsersToDatabase();

        Success success = new Success(HttpStatus.OK, "Random users were inserted into the database");
        return new ResponseEntity<ApiResponse>(success, HttpStatus.OK);
    }



    @ExceptionHandler(IOException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ApiResponse> inOutException(IOException ex){
        return buildErrorResponseAndPrintStackTrace(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while reading files", ex);
    }

    @ExceptionHandler(IncorrectlyFormedUserException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ApiResponse> inOutException(IncorrectlyFormedUserException ex){
        return buildErrorResponseAndPrintStackTrace(HttpStatus.INTERNAL_SERVER_ERROR, "One the users provided in the data.json classpath resource file was incorrectly formed.", ex);
    }

}
