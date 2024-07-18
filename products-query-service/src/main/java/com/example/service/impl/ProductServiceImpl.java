package com.example.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.dto.ProductEvent;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
    
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public Product getProduct(long id) {
        return this.productRepository.findById(id).get();
    }

    @KafkaListener(topics = "product-event-topic", groupId = "test-consumer-group")
    public void processProductEvent(ProductEvent productEvent) throws Exception{
        if(productEvent.getEvent().equals("CreateProduct")){
            Product product = new Product();
            product.setId(productEvent.getProduct().getId());
            product.setDescription(productEvent.getProduct().getDescription());
            product.setName(productEvent.getProduct().getName());
            product.setPrice(productEvent.getProduct().getPrice());

            this.productRepository.save(product);
        }
        if(productEvent.getEvent().equals("UpdateProduct")){
            Optional<Product> product = this.productRepository.findById(productEvent.getProduct().getId());
            if(product.isPresent()){
                this.productRepository.save(productEvent.getProduct());
            }else{
                throw new Exception("Product not found");
            }
        }
        if(productEvent.getEvent().equals("DeleteProduct")){
            this.productRepository.delete(productEvent.getProduct());
        }
    }
}
