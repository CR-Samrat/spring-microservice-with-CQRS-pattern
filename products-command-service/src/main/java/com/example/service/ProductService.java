package com.example.service;

import com.example.model.Product;

public interface ProductService {
    Product addProduct(Product newProduct);
    Product editProduct(Product exProduct, Product newProduct);
    String deleteProduct(long id);
}
