package com.auth.user.service;

import com.auth.user.model.dto.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product, String userId);
    List<Product> getAllProducts();
}
