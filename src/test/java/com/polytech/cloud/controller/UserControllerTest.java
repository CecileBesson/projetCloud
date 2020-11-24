package com.polytech.cloud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polytech.cloud.UserBasicDataSamples;
import com.polytech.cloud.exceptions.StringIdExceptionForGetException;
import com.polytech.cloud.exceptions.UserToGetDoesNotExistException;
import com.polytech.cloud.service.implementation.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.*;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = {UserController.class})
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class UserControllerTest extends UserBasicDataSamples {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    private final ObjectMapper mapper = new ObjectMapper();



    @Test
    public void getTest() throws Exception {

        when(userService.get()).thenReturn(myUsers);

        ResultActions response = mvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].firstName").value(user0.getFirstName()))
                    .andExpect(jsonPath("$[1].firstName").value(user1.getFirstName()))
                    .andExpect(jsonPath("$[2].firstName").value(user2.getFirstName()));

        System.out.println("✔ GET /user");
    }

    @Test
    void getByIdTest() throws Exception {

        when(userService.getById("1")).thenReturn(user0);
        when(userService.getById("2")).thenReturn(user1);
        when(userService.getById("3")).thenReturn(user2);
        when(userService.getById("15532")).thenThrow(UserToGetDoesNotExistException.class);
        when(userService.getById("willCauseBadRequestError")).thenThrow(StringIdExceptionForGetException.class);


        mvc.perform(get("/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName").value(user0.getFirstName()));


        mvc.perform(get("/user/15532")
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(HttpStatus.NOT_FOUND.value()));

        mvc.perform(get("/user/willCauseBadRequestError")
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(HttpStatus.NOT_FOUND.value()));

        System.out.println("✔ GET /user/{id}");
    }

    @Test
    void putTest() throws Exception {
        doNothing().when(userService).put(myUsers);

        mvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        mvc.perform(put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(myUsers)))
                    .andExpect(status().is(HttpStatus.CREATED.value()));

        System.out.println("✔ PUT /user");

    }

    @Test
    void putByIdTest() throws Exception {
        doNothing().when(userService).putById("1", user0);

        mvc.perform(put("/user/dazssefqs466009--zefze&")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        mvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user0)))
                .andExpect(status().isOk());

        when(this.userService.getById("1")).thenReturn(this.user0);


        mvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user0)))
                    .andExpect(status().isOk());

        System.out.println("✔ PUT /user/{id}");
    }

    @Test
    void deleteTest() throws Exception {

        mvc.perform(delete("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        System.out.println("✔ DELETE /user");
    }

    @Test
    void deleteByIdTest() throws Exception {

        mvc.perform(delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        System.out.println("✔ DELETE /user/{id}");
    }

    @Test
    void postTest(){
        //todo

        System.out.println("✔ POST /user");
    }

    @Test
    void getUsersByNameTest() throws Exception {

        when(userService.findByLastName("Na", 0, 100)).thenReturn(myOtherUsers);

        mvc.perform(get("/user/search?term=Na")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].lastName").value(user3.getLastName()))
                .andExpect(jsonPath("$[1].lastName").value(user4.getLastName()))
                .andExpect(jsonPath("$[2].lastName").value(user5.getLastName()));

        System.out.println("✔ GET /user/search?term={}");
    }

    @Test
    void getFirst10NearestUsersTest() throws Exception {

        when(userService.getFirst10NearestUsers(78.2, 56.8)).thenReturn(myOtherUsers);

        mvc.perform(get("/user/nearest?lat=78.2&lon=56.8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].position.lat").value(user3.getPositionByFkPosition().getLat()))
                .andExpect(jsonPath("$[0].position.lon").value(user3.getPositionByFkPosition().getLon()))
                .andExpect(jsonPath("$[1].position.lat").value(user4.getPositionByFkPosition().getLat()))
                .andExpect(jsonPath("$[1].position.lon").value(user4.getPositionByFkPosition().getLon()))
                .andExpect(jsonPath("$[2].position.lat").value(user5.getPositionByFkPosition().getLat()))
                .andExpect(jsonPath("$[2].position.lon").value(user5.getPositionByFkPosition().getLon()));

        System.out.println("✔ GET /user/nearest?lat={}&lon={}");

    }



}
