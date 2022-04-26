package com.example.demo.service;

import java.nio.charset.Charset;
import java.util.Optional;

import com.example.demo.model.UserModel;
import com.example.demo.model.dto.UserLoginModel;
import com.example.demo.repository.UserRepository;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
 
    private @Autowired UserRepository repository;

    public Optional<UserModel> signUpUser(UserModel user) {
        Optional<UserModel> optional = repository.findByUser(user.getUser()); 

        if(optional.isPresent()) {
            return Optional.empty();
        } 
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
        String passwordEncoder = encoder.encode(user.getPassword());
        user.setPassword(passwordEncoder);

        return Optional.ofNullable(repository.save(user));
    }

    public Optional<UserModel> updateUser (UserModel user) {
        if (repository.findById(user.getId()).isPresent()) {

            Optional<UserModel> findUser = repository.findByUser(user.getUser());

            if (findUser.isPresent()) {
                if(findUser.get().getId() != user.getId())
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists!", null);
            }

            user.setPassword(passwordCryptography(user.getPassword()));

            return Optional.of(repository.save(user));

        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!", null);
    }

    public Optional<UserLoginModel> login(Optional<UserLoginModel> user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        Optional <UserModel> usuario = repository.findByUser(user.get().getUser());

        if(usuario.isPresent()) {
            if(encoder.matches(user.get().getPassword(), user.get().getPassword())) {
                String auth = user.get().getUser() + ":" + user.get().getPassword();
                byte[] encoderAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encoderAuth);

                user.get().setToken(authHeader);
                user.get().setId(user.get().getId());
                user.get().setName(user.get().getName());
                user.get().setUser(user.get().getUser());
                user.get().setPassword(user.get().getPassword());

                return user;

            }   
        }
        return null;
        
    }

        private String passwordCryptography(String password) {
            
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            return encoder.encode(password);

        }

}
