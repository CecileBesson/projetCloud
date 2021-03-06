package com.polytech.cloud.controller;

import com.polytech.cloud.entities.UserEntity;
import com.polytech.cloud.exceptions.*;
import com.polytech.cloud.responses.Error;
import com.polytech.cloud.service.implementation.UserService;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.polytech.cloud.responses.*;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

import static com.polytech.cloud.utils.ControllerExceptionBuilder.buildErrorResponseAndPrintStackTrace;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService service) {
        this.userService = service;
    }

    /**
     * GET /user
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return new ResponseEntity<List<UserEntity>>(this.userService.get(), HttpStatus.OK);
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
            this.userService.put(newUsers);
            Success success = new Success(HttpStatus.CREATED, "PUT successfuly completed. Users were replaced");
            return new ResponseEntity<List<UserEntity>>(this.userService.get(), HttpStatus.CREATED);
        } else {
            Error error = new Error(HttpStatus.BAD_REQUEST, "Given params were incorrect");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * DELETE/
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity deleteAllUsers() throws DeleteAllException {
        this.userService.delete();
        //ApiResponse resp = new Success(HttpStatus.OK, "All users were deleted.");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * GET/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<UserEntity> getAUser(@PathVariable String id) throws UserToGetDoesNotExistException, StringIdExceptionForGetException {
        UserEntity result = this.userService.getById(id);
        return new ResponseEntity<UserEntity>(result, HttpStatus.OK);
    }

    /**
     * GET users with age > gt
     * @param gt
     * @return pageSize first users corresponding to pageNo
     */
    @RequestMapping(value= "/age", method = RequestMethod.GET, params = "gt")
    public ResponseEntity<List<UserEntity>> getUsersByAgeGt(
            @RequestParam Integer gt,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer pageSize) throws UserToGetDoesNotExistException {
        if(gt<0){
            Error error = new Error(HttpStatus.BAD_REQUEST, "Given param must be positive");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<UserEntity>>(this.userService.getUsersByAgeGt(gt, page, pageSize), HttpStatus.OK);
    }

    /**
     * GET users with age eq
     * @param eq
     * @return pageSize first users corresponding to pageNo
     */
    @RequestMapping(value= "/age", method = RequestMethod.GET, params = "eq")
    public ResponseEntity<List<UserEntity>> getUsersByAgeEq(
            @RequestParam Integer eq,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer pageSize) throws UserToGetDoesNotExistException {
        if(eq<0){
            Error error = new Error(HttpStatus.BAD_REQUEST, "Given param must be positive");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<List<UserEntity>>(this.userService.getUsersByAgeEq(eq, page, pageSize), HttpStatus.OK);
    }

    /**
     * GET users
     * @param page the page number
     * @param pageSize the page size
     * @return pageSize first users corresponding to pageNo
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<UserEntity>> getUsersByPage(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer pageSize)
    {

        return new ResponseEntity<List<UserEntity>>(this.userService.getUsers(page, pageSize), HttpStatus.OK);
    }

    /**
     * POST/ Creates a new user.
     * @param user the user to create
     * @return no content http response
     */
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody UserEntity user) throws CreatePostException, IncorrectlyFormedUserException {
        this.userService.post(user);
        return new ResponseEntity<UserEntity>(user, HttpStatus.CREATED);
    }

    /**
     * PUT/{id} Updates the current user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateUser(@RequestBody UserEntity user, @PathVariable String id) throws ReplacePutException, IncorrectlyFormedUserException {
        this.userService.putById(id, user);
        return ResponseEntity.ok().build();
    }


    /**
     * DELETE/{id}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable String id) throws UserToDeleteDoesNotExistException, StringIdExceptionForDelete {
            this.userService.deleteById(id);
            Success success = new Success(HttpStatus.OK, "The user n°" + id + " has been correctly deleted.");
            return new ResponseEntity<ApiResponse>(success, HttpStatus.OK);
    }

    /**
     * GET /search?term={}
     * @param term the user's lastname.
     * @param page the page number.
     * @param pageSize the size page.
     * @return the first 100 users corresponding to a specific lastname
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET, params = "term")
    public ResponseEntity<List<UserEntity>> getUsersByName(
            @RequestParam String term,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "100") Integer pageSize) {
        return new ResponseEntity<List<UserEntity>>(this.userService.findByLastName(term, page, pageSize), HttpStatus.OK);
    }

    /**
     * GET /nearest?lat={}&lon={}
     * @param lat the user's latitude.
     * @param lon the user's longitude.
     * @return the nearest users.
     */
    @RequestMapping(value = "/nearest", method = RequestMethod.GET)
    public ResponseEntity<List<UserEntity>> getFirst10NearestUsers(
            @RequestParam Double lat,
            @RequestParam Double lon) {
        return new ResponseEntity<List<UserEntity>>(this.userService.getFirst10NearestUsers(lat, lon), HttpStatus.OK);
    }


    /**
     * This operation is destructive. It removes all users and adds random ones into the database.
     */
    @RequestMapping(value = "/dev/insert-random", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<ApiResponse> saveRandomUsersToDatabase() throws IOException, IncorrectlyFormedUserException {
        this.userService.insertRandomUsersIntoDatabase();

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
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ApiResponse> userToDeleteDoesNotExist(UserToDeleteDoesNotExistException ex) {
        return buildErrorResponseAndPrintStackTrace(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
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
