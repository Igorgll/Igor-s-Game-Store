package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import com.example.demo.model.ProductModel;
import com.example.demo.repository.ProductRepository;
import com.example.demo.util.Category;
import com.example.demo.util.Platform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/games")
@Tag(name = "Games", description = "Games Administration")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class ProductController {

    private @Autowired ProductRepository repository;

    @GetMapping
        public ResponseEntity<List <ProductModel>> getAll(){
            List<ProductModel> list = repository.findAll();
            
            if (list.isEmpty()) {
                return ResponseEntity.status(204).build();
            }else {
                return ResponseEntity.ok(repository.findAll());
            }
        }

    @GetMapping("/{id}") 
        public ResponseEntity<ProductModel> getById (@PathVariable Long id){
            return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
            .orElse(ResponseEntity.notFound().build());
        }

        @GetMapping("/category")    
            public ResponseEntity<List <ProductModel>> getByCategory (@RequestParam (defaultValue = "ACTION") Category category) {
                return ResponseEntity.ok(repository.findAllByCategory(category));
            }

        @GetMapping("/platform")
            public ResponseEntity<List <ProductModel>> getByPlatform (@RequestParam (defaultValue = "PC") Platform platform) {
                return ResponseEntity.ok(repository.findAllByPlatform(platform));
            }

    @PostMapping 
        public ResponseEntity<ProductModel> post(@Valid @RequestBody ProductModel product) {
            return ResponseEntity.status(HttpStatus.CREATED)
            .body(repository.save(product));
        }

    @PutMapping 
        public ResponseEntity<ProductModel> put (@Valid @RequestBody ProductModel product) {
            return ResponseEntity.status(HttpStatus.OK)
            .body(repository.save(product));
        }

    @DeleteMapping("/{id}")
        public void delete(@PathVariable Long id) {
            repository.deleteById(id);
        }
        

}
