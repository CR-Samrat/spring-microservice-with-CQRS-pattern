package com.example.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    private ProductService commandService;
    
    @PostMapping
    public ResponseEntity<?> addNewProduct(@RequestBody Product product){
        return new ResponseEntity<>(this.commandService.addProduct(product), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modifyProduct(@PathVariable("id") long id,@RequestBody Product product){
        Optional<Product> exProduct = this.productRepository.findById(id);

        if(exProduct.isPresent()){
            return new ResponseEntity<>(this.commandService.editProduct(exProduct.get(), product), HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid product", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeProduct(@PathVariable("id") long id){
        Optional<Product> product = this.productRepository.findById(id);

        if(product.isPresent()){
            return new ResponseEntity<>(this.commandService.deleteProduct(id), HttpStatus.OK);
        }

        return new ResponseEntity<>("Id doesn't exists",HttpStatus.BAD_REQUEST);
    }
}
