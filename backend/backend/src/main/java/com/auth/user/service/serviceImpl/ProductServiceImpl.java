package com.auth.user.service.serviceImpl;

import com.auth.user.mapper.ProductMapper;
import com.auth.user.model.dao.ProductDao;
import com.auth.user.model.dto.Product;
import com.auth.user.repository.ProductRepository;
import com.auth.user.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product, String userId){
        product.setUserId(userId);
        ProductDao productDao = ProductMapper.toDao(product);
        productDao = productRepository.save(productDao);
        return ProductMapper.toDto(productDao);
    }

    @Override
    public List<Product> getAllProducts(){
        List<ProductDao> productDaos = productRepository.findAll();
        return ProductMapper.toListDto(productDaos);
    }
}
