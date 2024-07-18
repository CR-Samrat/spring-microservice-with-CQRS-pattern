package com.example.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService queryService;

    @GetMapping
    public ResponseEntity<?> retrieveAllProducts(){
        return new ResponseEntity<>(this.queryService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieveProduct(@PathVariable("id") long id){
        Optional<Product> product = this.productRepository.findById(id);

        if(product.isPresent()){
            return new ResponseEntity<>(this.queryService.getProduct(id), HttpStatus.OK);
        }

        return new ResponseEntity<>(product, HttpStatus.BAD_REQUEST);
    }
}
