package com.example.demo.repository;

import java.util.Optional;

import com.example.demo.model.ShopCartModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopCartRepository extends JpaRepository<ShopCartModel, Long>{
    
    public Optional<ShopCartModel> findById(Long id);

}
