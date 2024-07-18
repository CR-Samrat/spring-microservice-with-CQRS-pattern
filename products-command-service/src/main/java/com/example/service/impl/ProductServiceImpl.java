package com.example.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.dto.ProductEvent;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public Product addProduct(Product newProduct) {
        Product product = this.productRepository.save(newProduct);

        ProductEvent event = new ProductEvent("CreateProduct", product);
        kafkaTemplate.send("product-event-topic",event);

        return product;
    }

    @Override
    public Product editProduct(Product exProduct, Product newProduct) {

        exProduct.setName(newProduct.getName());
        exProduct.setDescription(newProduct.getDescription());
        exProduct.setPrice(newProduct.getPrice());

        Product product = this.productRepository.save(exProduct);

        ProductEvent event = new ProductEvent("UpdateProduct",product);
        kafkaTemplate.send("product-event-topic", event);

        return product;
    }

    @Override
    public String deleteProduct(long id) {
        ProductEvent event = new ProductEvent("DeleteProduct", this.productRepository.findById(id).get());
        kafkaTemplate.send("product-event-topic",event);

        this.productRepository.deleteById(id);

        return "Product deleted successfully";
    }
    
}
