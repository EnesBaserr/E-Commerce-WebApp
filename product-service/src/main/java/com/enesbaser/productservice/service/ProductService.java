package com.enesbaser.productservice.service;
import com.enesbaser.productservice.dto.ProductRequest;
import com.enesbaser.productservice.dto.ProductResponse;
import com.enesbaser.productservice.model.Product;
import com.enesbaser.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    //@Autowired
    private  final ProductRepository productRepository;
    public void createProduct(ProductRequest productRequest){
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved",product.getId());
    }
    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findAll();
        //we should map products to product response using stream.
      return   products.stream().map(product -> mapToProductResponse( product)).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
