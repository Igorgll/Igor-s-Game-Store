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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/games")
@Tag(name = "Games", description = "Games Administration")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class ProductController {

    private @Autowired ProductRepository repository;

    @Operation(summary = "Method get all products")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Return products"),
			@ApiResponse(responseCode = "400", description = "Return with no products"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
	})    
    @GetMapping
        public ResponseEntity<List <ProductModel>> getAll(){
            List<ProductModel> list = repository.findAll();
            
            if (list.isEmpty()) {
                return ResponseEntity.status(204).build();
            }else {
                return ResponseEntity.ok(repository.findAll());
            }
        }

        @Operation(summary = "Method get product by id")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Return existent product"),
                @ApiResponse(responseCode = "400", description = "Inexistent product"),
                @ApiResponse(responseCode = "500", description = "Internal Server Error")
        })        
    @GetMapping("/{id}") 
        public ResponseEntity<ProductModel> getById (@PathVariable Long id){
            return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
            .orElse(ResponseEntity.notFound().build());
        }

        @Operation(summary = "Method get product by category")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Return existent category"),
                @ApiResponse(responseCode = "400", description = "Inexistent category"),
                @ApiResponse(responseCode = "500", description = "Internal Server Error")
        })          
        @GetMapping("/category")    
            public ResponseEntity<List <ProductModel>> getByCategory (@RequestParam (defaultValue = "ACTION") Category category) {
                return ResponseEntity.ok(repository.findAllByCategory(category));
            }

            @Operation(summary = "Method get product by platform")
            @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "Return existent platform"),
                    @ApiResponse(responseCode = "400", description = "Inexistent platform"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })              
        @GetMapping("/platform")
            public ResponseEntity<List <ProductModel>> getByPlatform (@RequestParam (defaultValue = "PC") Platform platform) {
                return ResponseEntity.ok(repository.findAllByPlatform(platform));
            }

            @Operation(summary = "Method post product")
            @ApiResponses(value = {
                    @ApiResponse(responseCode = "200", description = "Product successful posted"),
                    @ApiResponse(responseCode = "400", description = "Post method error"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            })            
    @PostMapping 
    public ResponseEntity<ProductModel> post(@Valid @RequestBody ProductModel product) {
            return ResponseEntity.status(HttpStatus.CREATED)
            .body(repository.save(product));
        }

        @Operation(summary = "Update existent product")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Return updated product"),
                @ApiResponse(responseCode = "400", description = "Request error"),
                @ApiResponse(responseCode = "500", description = "Internal Server Error")
        })        
    @PutMapping 
        public ResponseEntity<ProductModel> put (@Valid @RequestBody ProductModel product) {
            return ResponseEntity.status(HttpStatus.OK)
            .body(repository.save(product));
        }

        @Operation(summary = "Method delete product by id")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Product successful deleted"),
                @ApiResponse(responseCode = "400", description = "Invalid product id"),
                @ApiResponse(responseCode = "500", description = "Internal Server Error")
        })        
    @DeleteMapping("/{id}")
        public void delete(@PathVariable Long id) {
            repository.deleteById(id);
        }
        

}
