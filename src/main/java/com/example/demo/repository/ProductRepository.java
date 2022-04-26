package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.ProductModel;
import com.example.demo.util.Category;
import com.example.demo.util.Platform;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    
    public List<ProductModel> findAllByNameContainingIgnoreCase(String name);

    public List<ProductModel> findAllByCategory(Category category);

    public List<ProductModel> findAllByPlatform(Platform platform);

}
