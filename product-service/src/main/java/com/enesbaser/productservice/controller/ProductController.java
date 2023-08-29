package com.enesbaser.productservice.controller;

import com.enesbaser.productservice.dto.ProductRequest;
import com.enesbaser.productservice.dto.ProductResponse;
import com.enesbaser.productservice.model.Product;
import com.enesbaser.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest producRequest){
        productService.createProduct(producRequest);


    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }


}

