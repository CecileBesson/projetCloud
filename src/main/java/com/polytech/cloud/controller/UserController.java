package com.polytech.cloud.controller;

import com.polytech.cloud.entities.UserEntity;
import com.polytech.cloud.exceptions.*;
import com.polytech.cloud.responses.Error;
import com.polytech.cloud.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.polytech.cloud.responses.*;

import java.io.IOException;

import static com.polytech.cloud.utils.ControllerExceptionBuilder.buildErrorResponseAndPrintStackTrace;

@RestController
@RequestMapping("/user")
public class UserController {

    //todo :
    // DELETE /user -> supprime toute la collection des utilisateurs. Please reset the seed
    // DELETE /user/{id} -> supprime l'utilisateur correspondant


    private final UserService userService;

    @Autowired
    public UserController(UserService service) {
        this.userService = service;
    }

    // GET /
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        // todo : performance will need to be increased as soon as we can.
        return new ResponseEntity<List<UserEntity>>(this.userService.findAllUsers(), HttpStatus.OK);
    }

    /**
     * Retrieve a user by his id
     * GET/{id}
     *
     * @param id users id
     * @return a specific user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserEntity> getAUser(@PathVariable String id) throws UserToGetDoesNotExistException, StringIdExceptionForGetException {

            UserEntity result = this.userService.findByIdUser(id);
            return new ResponseEntity<UserEntity>(result, HttpStatus.OK);



        //return new ResponseEntity<UserEntity>(result, HttpStatus.OK);
    }

    // DELETE /
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteAllUsers() throws DeleteAllException {
        this.userService.deleteAll();
        //ApiResponse resp = new Success(HttpStatus.OK, "All users were deleted.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Delete a user by his id.
     * @param id  user id.
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable String id) throws UserToDeleteDoesNotExistException, StringIdExceptionForDelete {
            this.userService.deleteAUserById(id);
            Success success = new Success(HttpStatus.OK, "The user n°" + id + " has been correctly deleted.");
            return new ResponseEntity<ApiResponse>(success, HttpStatus.OK);
    }


    /**
     * PUT/
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
     * POST/
     * Creates a new user.
     *
     * @param user the user to create
     * @return no content http response
     */
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody UserEntity user) throws CreatePostException, IncorrectlyFormedUserException {
        this.userService.createUser(user);
        return new ResponseEntity<UserEntity>(user, HttpStatus.CREATED);
    }

    /**
     * PUT/{id}
     * Updates the current user
     *
     * @param user the user to update
     * @return no content http response
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody UserEntity user, @PathVariable int id) throws ReplacePutException, IncorrectlyFormedUserException {
        this.userService.updateUser(id, user);
        return ResponseEntity.ok().build();
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

    /* --------------------------------- Exception handlers --------------------------------------------------------------*/
    @ExceptionHandler(IOException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ApiResponse> inOutException(IOException ex) {
        return buildErrorResponseAndPrintStackTrace(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while deserializing. Check your params.", ex);
    }

    @ExceptionHandler(IncorrectlyFormedUserException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<ApiResponse> inOutException(IncorrectlyFormedUserException ex) {
        return buildErrorResponseAndPrintStackTrace(HttpStatus.BAD_REQUEST, "One the users provided in the data.json classpath resource file was incorrectly formed.", ex);
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

    @ExceptionHandler(CreatePostException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ApiResponse> createPostException(DeleteAllException ex) {
        return buildErrorResponseAndPrintStackTrace(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
    }

    @ExceptionHandler(UserToDeleteDoesNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<ApiResponse> userToDeleteDoesNotExist(UserToDeleteDoesNotExistException ex) {
        return buildErrorResponseAndPrintStackTrace(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
    }

    @ExceptionHandler(StringIdExceptionForDelete.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ApiResponse> stringIdExceptionForDelete(StringIdExceptionForDelete ex) {
        return buildErrorResponseAndPrintStackTrace(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
    }

    @ExceptionHandler(UserToGetDoesNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<ApiResponse> userToGetDoesNotExistException(UserToGetDoesNotExistException ex) {
        return buildErrorResponseAndPrintStackTrace(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
    }

    @ExceptionHandler(StringIdExceptionForGetException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<ApiResponse> stringIdExceptionForGetException(StringIdExceptionForGetException ex) {
        return buildErrorResponseAndPrintStackTrace(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
    }



}
