package com.example.service;

import java.util.List;

import com.example.model.Product;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProduct(long id);
}
