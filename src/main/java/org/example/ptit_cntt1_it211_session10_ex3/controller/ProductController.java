package org.example.ptit_cntt1_it211_session10_ex3.controller;

import org.example.ptit_cntt1_it211_session10_ex3.dto.CreateProductRequest;
import org.example.ptit_cntt1_it211_session10_ex3.entity.Product;
import org.example.ptit_cntt1_it211_session10_ex3.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody CreateProductRequest request) {
        return productService.create(request);
    }
}
