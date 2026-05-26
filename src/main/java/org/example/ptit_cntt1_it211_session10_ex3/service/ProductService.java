package org.example.ptit_cntt1_it211_session10_ex3.service;

import org.example.ptit_cntt1_it211_session10_ex3.dto.CreateProductRequest;
import org.example.ptit_cntt1_it211_session10_ex3.entity.Product;
import org.example.ptit_cntt1_it211_session10_ex3.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product create(CreateProductRequest request) {
        if (request.quantity() == null || request.quantity() < 0) {
            throw new IllegalArgumentException("Số lượng khởi tạo không hợp lệ.");
        }
        Product product = new Product();
        product.setName(request.name());
        product.setQuantity(request.quantity());
        product.setSku(request.sku());
        return productRepository.save(product);
    }
}
