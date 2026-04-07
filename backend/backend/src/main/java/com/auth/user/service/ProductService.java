package com.auth.user.service;

import com.auth.user.model.dto.PageRequest;
import com.auth.user.model.dto.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Product product, String userId);
    List<Product> getAllProducts(PageRequest pageRequest);
    Product getProductById(String id);
    Product updateProduct(Product product, String productId, String userId);
    void deleteProduct(String productId, String userId);
    List<Product> searchProductByKey(String key);
}
