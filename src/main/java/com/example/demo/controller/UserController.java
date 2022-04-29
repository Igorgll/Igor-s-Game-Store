package com.example.demo.controller;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.example.demo.model.UserModel;
import com.example.demo.model.dto.UserLoginModel;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "User resources", description = "User administration")

public class UserController {
  
    public @Autowired UserRepository repository;

    public @Autowired UserService userService;

	@Operation(summary = "Method get all users")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Return users list"),
			@ApiResponse(responseCode = "400", description = "Return without users"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
    @GetMapping
        public ResponseEntity<List <UserModel>> getAll(){
            List<UserModel> list = repository.findAll();
            if (list.isEmpty()) {
                return ResponseEntity.status(204).build();
            }else {
                return ResponseEntity.status(200).body(list);
            }
        }
        
        @Operation(summary = "Method get user by id")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Return existent user"),
                @ApiResponse(responseCode = "400", description = "Inexistent user"),
                @ApiResponse(responseCode = "500", description = "Internal Server Error")
        })
    @GetMapping("/{id}")
        public ResponseEntity<UserModel> getById(@PathVariable (value = "id") Long id) {
            return repository.findById(id).map(resp -> ResponseEntity.status(200).body(resp))
            .orElseThrow(() -> {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Id doesn't exist.");
            }); 
        }

        @Operation(summary = "Method get user by name")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Return users list"),
                @ApiResponse(responseCode = "500", description = "Internal Server Error")
        })        
    @GetMapping("/name/{name}") 
        public ResponseEntity<List <UserModel>> getByName(@PathVariable (value = "name") String name) {
            List<UserModel> list = repository.findAllByNameContainingIgnoreCase(name);

            if(list.isEmpty()) {
                return ResponseEntity.status(204).build();
            }else {
                return ResponseEntity.status(200).body(list);
            }
        }

        @Operation(summary = "Saves user")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "User successful saved"),
                @ApiResponse(responseCode = "400", description = "Error while saving"),
                @ApiResponse(responseCode = "500", description = "Internal Server Error")
        })
    @PostMapping
        public ResponseEntity<UserModel> post(@RequestBody UserModel user) {
            return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(user));        
        }

        @Operation(summary = "Signs up user")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Successful user sign up"),
                @ApiResponse(responseCode = "400", description = "Request error"),
                @ApiResponse(responseCode = "422", description = "User already signed up"),
                @ApiResponse(responseCode = "500", description = "Internal Server Error")
        })
    @PostMapping("/signup") 
        public ResponseEntity<UserModel> postUser(@Valid @RequestBody UserModel user) {
            return userService.signUpUser(user).map(resp -> ResponseEntity.status(201).body(resp))
                .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
        }
        
        @Operation(summary = "User login")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "User successful logged in"),
                @ApiResponse(responseCode = "400", description = "Error while logging"),
                @ApiResponse(responseCode = "500", description = "Internal Server Error")
        })
    @PostMapping("/login")
        public ResponseEntity<UserLoginModel> authentication(@RequestBody Optional<UserLoginModel> user) {
            return userService.login(user).map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }   

        @Operation(summary = "Updates user")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Returns updated user"),
                @ApiResponse(responseCode = "400", description = "Request error"),
                @ApiResponse(responseCode = "500", description = "Internal Server Error")
        })        
    @PutMapping 
        public ResponseEntity<UserModel> put(@RequestBody UserModel user) {
            return userService.updateUser(user).map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
            .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()); 

        }

        @Operation(summary = "Deletes existent user")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Returns deleted user"),
                @ApiResponse(responseCode = "400", description = "User id invalid"),
                @ApiResponse(responseCode = "500", description = "Internal Server Error")
        })        
    @DeleteMapping("/{îd}")
        public void delete(@PathVariable Long id) {
            repository.deleteById(id);
        }

}
