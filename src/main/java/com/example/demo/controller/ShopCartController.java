package com.example.demo.controller;

import javax.validation.Valid;

import com.example.demo.model.ShopCartModel;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShopCartRepository;
import com.example.demo.repository.UserRepository;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController 
@RequestMapping("/purchases")
@Tag(name = "Shop Cart", description = "Shop Cart Administration")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class ShopCartController {
    
    public @Autowired ShopCartRepository repositoryShopCart;

    public @Autowired ProductRepository repositoryProduct;

    public @Autowired UserRepository repositoryUser;

    @Operation(summary = "Purchase method get by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Purchase inexistent"),
			@ApiResponse(responseCode = "400", description = "Invalid purchase"),
			@ApiResponse(responseCode = "500", description = "Internal Server Error")
	})
    @GetMapping("/{id}")
        public ResponseEntity<ShopCartModel> getById(@PathVariable Long id) {
            return repositoryShopCart.findById(id).map(resp -> ResponseEntity.ok(resp))
            .orElse(ResponseEntity.notFound().build());
        }
        
        @Operation(summary = "It creates new purchase")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Purchase successful"),
                @ApiResponse(responseCode = "400", description = "Request error"),
                @ApiResponse(responseCode = "422", description = "Purchase already existent"),
                @ApiResponse(responseCode = "500", description = "Internal Server Error")
        })      
    @PostMapping
        public ResponseEntity<ShopCartModel> newPurchase(@RequestBody ShopCartModel purchase) {
            if (repositoryUser.existsById(purchase.getUser().getId()) 
                    && repositoryProduct.existsById(purchase.getProduct().getId())) {
                        return ResponseEntity.ok(repositoryShopCart.save(purchase));
                    } 
                    return ResponseEntity.badRequest().build();
        }
 
        @Operation(summary = "Method put purchase")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "201", description = "Return updated purchase"),
                @ApiResponse(responseCode = "400", description = "Request error"),
                @ApiResponse(responseCode = "500", description = "Internal Server Error")
        })
    @PutMapping 
        public ResponseEntity<ShopCartModel> updatePurchase(@Valid @RequestBody ShopCartModel purchase) {
            return ResponseEntity.status(HttpStatus.OK).body(repositoryShopCart.save(purchase));
        }

        @Operation(summary = "Method delete purchase by id")
        @ApiResponses(value = {
                @ApiResponse(responseCode = "200", description = "Purchase successful deleted"),
                @ApiResponse(responseCode = "400", description = "Purchase id invalid"),
        })        
    @DeleteMapping("/{id}")
        public void delete (@PathVariable Long id) {
            repositoryShopCart.deleteById(id);
        }

}
