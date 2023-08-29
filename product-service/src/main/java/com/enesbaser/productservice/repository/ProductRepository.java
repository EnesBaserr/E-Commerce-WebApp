package com.enesbaser.productservice.repository;

import com.enesbaser.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface ProductRepository extends MongoRepository<Product,String> {

}
