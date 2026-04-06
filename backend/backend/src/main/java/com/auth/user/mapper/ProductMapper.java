package com.auth.user.mapper;


import com.auth.user.model.dao.ProductDao;
import com.auth.user.model.dto.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductMapper {

    public static ProductDao toDao(Product product){
        return ProductDao.builder()
                .quantity(product.getQuantity())
                .name(product.getName())
                .description(product.getDescription())
                .url(product.getUrl()).build();
    }

    public static Product toDto(ProductDao productDao){
        return Product.builder()
                .id(productDao.getId())
                .quantity(productDao.getQuantity())
                .name(productDao.getName())
                .description(productDao.getDescription())
                .url(productDao.getUrl())
                .userId(productDao.getUserId()).build();
    }

    public static List<Product> toListDto(List<ProductDao> productDaos){
        List<Product> products = new ArrayList<>();
        for(ProductDao productDao : productDaos){
            products.add(toDto(productDao));
        }
        return products;
    }

    public static void updateProduct(ProductDao product, Product request){
        product.setQuantity(request.getQuantity());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setUrl(request.getUrl());
    }

}
