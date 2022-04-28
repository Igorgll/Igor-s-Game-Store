package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
   
    private @Autowired TestRestTemplate testRestTemplate;

    private @Autowired UserService userService;

    private UserModel userToCreate;

    private UserModel userToUpdate;

    @BeforeAll
    void start() {

        userToCreate = new UserModel(0L, "Igor Boaz", "igor@email.com", "12345678");
        userToUpdate = new UserModel(1L, "Igor Boaz UPDATED", "igor@email.com", "12345678");
    }

    @Test
    @Order(1)
    @DisplayName("Should sign up user") 
    public void shouldCreateUser() {
        
        //GIVEN
        HttpEntity<UserModel> request = new HttpEntity<UserModel> (userToCreate);
        
        //WHEN
        ResponseEntity<UserModel> response = testRestTemplate
        .exchange("/users/signup", HttpMethod.POST, request, UserModel.class);

        //THEN
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

    }

    @Test 
    @Order(2)
    @DisplayName("It should not duplicate user")
    public void shouldNotDuplicateUser() {

        //GIVEN 
        userService.signUpUser(new UserModel(0l, "Maria Santos", "maria@email.com", "12345678"));
        HttpEntity<UserModel> request = new HttpEntity<UserModel>(
            new UserModel(0L, "Maria Santos", "maria@email.com", "12345678"));
        
        //WHEN 
        ResponseEntity<UserModel> response = testRestTemplate
        .exchange("/users/signup", HttpMethod.POST, request, UserModel.class);

        //THEN
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test 
    @Order(3)
    @DisplayName("Should update an user")
    public void shouldUpdateUser() {

        //GIVEN 
        HttpEntity<UserModel> request = new HttpEntity<UserModel>(userToUpdate);
        
        //WHEN 
        ResponseEntity<UserModel> response = testRestTemplate
                .withBasicAuth("root", "root")
                .exchange("/users", HttpMethod.PUT, request, UserModel.class);

        //THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

}
