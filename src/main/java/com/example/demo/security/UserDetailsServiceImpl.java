package com.example.demo.security;

import java.util.Optional;

import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private @Autowired UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername (String userName) throws UsernameNotFoundException {
        Optional<UserModel> user = userRepository.findByUser(userName);
        user.orElseThrow(() -> new UsernameNotFoundException(userName + "not found."));

        return user.map(UserDetailsImpl:: new).get();
    }

}
