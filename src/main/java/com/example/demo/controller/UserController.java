package com.example.demo.controller;


import java.util.List;
import java.util.Optional;

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

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "User resources", description = "User administration")

public class UserController {
  
    public @Autowired UserRepository repository;

    public @Autowired UserService userService;


    @GetMapping
        public ResponseEntity<List <UserModel>> getAll(){
            List<UserModel> list = repository.findAll();
            if (list.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "User not found");
            }else {
                return ResponseEntity.ok(repository.findAll());
            }
        }
        
    @GetMapping("/{id}")
        public ResponseEntity<UserModel> getById(@PathVariable Long id) {
            return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
            .orElse(ResponseEntity.notFound().build()); 
        }

    @GetMapping("/name/{name}") 
        public ResponseEntity<List <UserModel>> getByName(@PathVariable ("name") String name) {
            return ResponseEntity.ok(repository.findAllByNameContainingIgnoreCase(name));
        }


    @PostMapping
        public ResponseEntity<UserModel> post(@RequestBody UserModel user) {
            return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(user));        
        }


    @PostMapping("/signup") 
        public ResponseEntity<UserModel> postUser(@RequestBody UserModel user) {
            return userService.signUpUser(user).map(resp -> ResponseEntity.status(201).body(resp))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }

    @PostMapping("/login")
        public ResponseEntity<UserLoginModel> authentication(@RequestBody Optional<UserLoginModel> user) {
            return userService.login(user).map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
        }   

    @PutMapping 
        public ResponseEntity<UserModel> put(@RequestBody UserModel user) {
            return userService.updateUser(user).map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp))
            .orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()); 

        }

    @DeleteMapping("/{îd}")
        public void delete(@PathVariable Long id) {
            repository.deleteById(id);
        }

}
