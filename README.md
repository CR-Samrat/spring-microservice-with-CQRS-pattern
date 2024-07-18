# Steps to run spring micro-service

1. Navigate to you kafka directory and open two command prompt

2. Run zoo-keeper server using this command (windows)
        .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties

3. Run kafka server using this command (windows)
        .\bin\windows\kafka-server-start.bat .\config\server.properties

4. Copy the groupId present in the .\config\consumer.properties

5. In product-query-service navigate to ProductServiceImpl.java and replace the groupId with this

        @KafkaListener(topics = "product-event-topic", groupId = "your-kafka-consumer-groupId")
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

6. Run producer app (product-command-service)

7. Run consumer app (product-query-service)
