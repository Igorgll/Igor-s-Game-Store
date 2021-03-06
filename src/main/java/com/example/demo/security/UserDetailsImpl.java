package com.example.demo.security;

import java.util.Collection;
import java.util.List;

import com.example.demo.model.UserModel;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetailsImpl implements UserDetails {
    
    private static final long serialVersionUID = 1L;

    private String userName; 

    private String password;
    
    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(UserModel user) {
        this.userName = user.getUser();
        this.password = user.getPassword();
    }

    public UserDetailsImpl() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    
}
