package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    public List<UserModel> findAllByNameContainingIgnoreCase(String name);

    public Optional<UserModel> findByUser(String user);
}
