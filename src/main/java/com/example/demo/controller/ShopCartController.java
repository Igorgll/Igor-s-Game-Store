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

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController 
@RequestMapping("/purchases")
@Tag(name = "Shop Cart", description = "Shop Cart Administration")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class ShopCartController {
    
    public @Autowired ShopCartRepository repositoryShopCart;

    public @Autowired ProductRepository repositoryProduct;

    public @Autowired UserRepository repositoryUser;

    @GetMapping("/{id}")
        public ResponseEntity<ShopCartModel> getById(@PathVariable Long id) {
            return repositoryShopCart.findById(id).map(resp -> ResponseEntity.ok(resp))
            .orElse(ResponseEntity.notFound().build());
        }
        
    @PostMapping
        public ResponseEntity<ShopCartModel> newPurchase(@RequestBody ShopCartModel purchase) {
            if (repositoryUser.existsById(purchase.getUser().getId()) 
                    && repositoryProduct.existsById(purchase.getProduct().getId())) {
                        return ResponseEntity.ok(repositoryShopCart.save(purchase));
                    } 
                    return ResponseEntity.badRequest().build();
        }
 
    @PutMapping 
        public ResponseEntity<ShopCartModel> updatePurchase(@Valid @RequestBody ShopCartModel purchase) {
            return ResponseEntity.status(HttpStatus.OK).body(repositoryShopCart.save(purchase));
        }

    @DeleteMapping("/{id}")
        public void delete (@PathVariable Long id) {
            repositoryShopCart.deleteById(id);
        }

}
