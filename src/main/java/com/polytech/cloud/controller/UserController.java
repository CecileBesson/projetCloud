package com.polytech.cloud.controller;

import com.polytech.cloud.entities.UserEntity;
import com.polytech.cloud.exceptions.DeleteAllException;
import com.polytech.cloud.exceptions.ReplaceAllPutException;
import com.polytech.cloud.exceptions.ReplacePutException;
import com.polytech.cloud.responses.Error;
import com.polytech.cloud.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.polytech.cloud.exceptions.IncorrectlyFormedUserException;
import com.polytech.cloud.responses.*;
import com.polytech.cloud.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.polytech.cloud.utils.ControllerExceptionBuilder.buildErrorResponseAndPrintStackTrace;

@RestController
@RequestMapping("/user")
public class UserController {

    //todo :
    // DELETE /user -> supprime toute la collection des utilisateurs. Please reset the seed
    // POST /user -> ajoute un nouvel utilisateur passé en paramètre
    // PUT /user/{id} -> met à jour l'utilisateur
    // DELETE /user/{id} -> supprime l'utilisateur correspondant


    private final UserService userService;

    @Autowired
    public UserController(UserService service) {
        this.userService = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        // todo : performance will need to be increased as soon as we can.
        return new ResponseEntity<List<UserEntity>>(this.userService.findAllUsers(), HttpStatus.OK);
    }

    /**
     * Retrieve a user by his id
     *
     * @param id users id
     * @return a specific user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserEntity> getAUser(@PathVariable int id) {

        UserEntity result = this.userService.findByIdUser(id);

        if (result != null) {
            return new ResponseEntity<UserEntity>(result, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteAllUsers() throws DeleteAllException {
        this.userService.deleteAll();
        ApiResponse resp = new Success(HttpStatus.OK, "All users were deleted.");
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Replaces the entire database collection of users with new given users.
     *
     * @param newUsers the new users, contained in the request body that are deserialized into a List of UserEntity.
     * @return ApiResponse with code CREATED if everything is ok.
     * @throws ReplaceAllPutException
     * @throws IncorrectlyFormedUserException
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<List<UserEntity>> replaceAll(@RequestBody List<UserEntity> newUsers) throws ReplaceAllPutException, IncorrectlyFormedUserException {
        if (newUsers != null) {
            this.userService.replaceAll(newUsers);
            Success success = new Success(HttpStatus.CREATED, "PUT successfuly completed. Users were replaced");
            return new ResponseEntity<List<UserEntity>>(this.userService.findAllUsers(), HttpStatus.CREATED);
        } else {
            Error error = new Error(HttpStatus.BAD_REQUEST, "Given params were incorrect");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates an user
     *
     * @param modifiedUser the user that will be modified within the database.
     * @return ApiResponse with code CREATED if everything is ok.
     * @throws ReplaceAllPutException
     * @throws IncorrectlyFormedUserException
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ApiResponse> replace(@RequestBody UserEntity modifiedUser, @PathVariable int id) throws IncorrectlyFormedUserException, ReplacePutException {
        if (modifiedUser != null  && this.userService.findByIdUser(id) != null) {
            modifiedUser.setId(id);
            this.userService.replace(modifiedUser);
            Success success = new Success(HttpStatus.OK, "PUT successfuly completed. User n°" + id + " was modified.");
            return new ResponseEntity<ApiResponse>(success, HttpStatus.OK);
        } else {
            Error error = new Error(HttpStatus.BAD_REQUEST, "Given params were incorrect / User does not exist.");
            return new ResponseEntity<ApiResponse>(error, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This operation is destructive. It removes all users and adds random ones into the database.
     *
     * @return
     * @throws IOException
     * @throws IncorrectlyFormedUserException
     */
    @RequestMapping(value = "/dev/insert-random", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ApiResponse> saveRandomUsersToDatabase() throws IOException, IncorrectlyFormedUserException {
        this.userService.saveAllRandomUsersToDatabase();

        Success success = new Success(HttpStatus.OK, "Random users were inserted into the database");
        return new ResponseEntity<ApiResponse>(success, HttpStatus.OK);
    }




    /* Exception handlers */


    @ExceptionHandler(IOException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ApiResponse> inOutException(IOException ex) {
        return buildErrorResponseAndPrintStackTrace(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while deserializing. Check your params.", ex);
    }

    @ExceptionHandler(IncorrectlyFormedUserException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ApiResponse> inOutException(IncorrectlyFormedUserException ex) {
        return buildErrorResponseAndPrintStackTrace(HttpStatus.INTERNAL_SERVER_ERROR, "One the users provided in the data.json classpath resource file was incorrectly formed.", ex);
    }

    @ExceptionHandler(ReplaceAllPutException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ApiResponse> putReplaceAllException(ReplaceAllPutException ex) {
        return buildErrorResponseAndPrintStackTrace(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
    }

    @ExceptionHandler(ReplacePutException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ApiResponse> putReplaceAllException(ReplacePutException ex) {
        return buildErrorResponseAndPrintStackTrace(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
    }

    @ExceptionHandler(DeleteAllException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ApiResponse> putReplaceAllException(DeleteAllException ex) {
        return buildErrorResponseAndPrintStackTrace(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
    }

}
