package com.polytech.cloud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polytech.cloud.UserBasicDataSamples;
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

        when(userService.findAllUsers()).thenReturn(myUsers);

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

        when(userService.findByIdUser(1)).thenReturn(user0);
        when(userService.findByIdUser(2)).thenReturn(user1);
        when(userService.findByIdUser(3)).thenReturn(user2);

        mvc.perform(get("/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName").value(user0.getFirstName()));


        mvc.perform(get("/user/15532")
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(HttpStatus.NOT_FOUND.value()));

        mvc.perform(get("/user/willCauseBadRequestError")
                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        System.out.println("✔ GET /user/{id}");
    }

    @Test
    void putTest() throws Exception {
        doNothing().when(userService).replaceAll(myUsers);

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
        doNothing().when(userService).replace(user0);

        mvc.perform(put("/user/dazssefqs466009--zefze&")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));

        mvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user0)))
                .andExpect(status().isBadRequest());

        when(this.userService.findByIdUser(1)).thenReturn(this.user0);


        mvc.perform(put("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(user0)))
                    .andExpect(status().isOk());

        System.out.println("✔ PUT /user/{id}");
    }

    @Test
    void deleteTest(){
        //todo

        System.out.println("✔ DELETE /user");
    }

    @Test
    void deleteByIdTest(){
        //todo

        System.out.println("✔ DELETE /user/{id}");
    }

    @Test
    void postTest(){
        //todo

        System.out.println("✔ POST /user");
    }





}
