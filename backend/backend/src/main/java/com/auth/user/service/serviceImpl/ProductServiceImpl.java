package com.auth.user.service.serviceImpl;

import com.auth.user.exception.BusinessException;
import com.auth.user.mapper.ProductMapper;
import com.auth.user.model.dao.ProductDao;
import com.auth.user.model.dto.PageRequest;
import com.auth.user.model.dto.Product;
import com.auth.user.repository.ProductRepository;
import com.auth.user.repository.customRepository.ProductCustomRepository;
import com.auth.user.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCustomRepository productCustomRepository;

    public ProductServiceImpl(ProductRepository productRepository, ProductCustomRepository productCustomRepository){
        this.productRepository = productRepository;
        this.productCustomRepository = productCustomRepository;
    }

    @Override
    public Product createProduct(Product product, String userId){
        ProductDao productDao = ProductMapper.toDao(product);
        productDao.setUserId(userId);
        if(product.getAmount() < 0){
            throw new BusinessException("Enter valid amount of product.", HttpStatus.BAD_REQUEST);
        }
        productDao = productRepository.save(productDao);
        return ProductMapper.toDto(productDao);
    }

    @Override
    public List<Product> getAllProducts(PageRequest pageRequest){
        List<ProductDao> productDaos = productCustomRepository.getAllProducts(pageRequest);
        return ProductMapper.toListDto(productDaos);
    }

    @Override
    public Product getProductById(String id){
        ProductDao productDao = productRepository.findById(id).orElseThrow(() -> new BusinessException("Product with the given ID not found", HttpStatus.NOT_FOUND));
        return ProductMapper.toDto(productDao);
    }

    @Override
    public Product updateProduct(Product product, String productId, String userId) {
        ProductDao existingProduct = productRepository.findById(productId).orElseThrow(() -> new BusinessException("Product with given ID does not exist.", HttpStatus.NOT_FOUND));
        if(!existingProduct.getUserId().equals(userId)){
            throw new BusinessException("The product can only be edited by the owner.", HttpStatus.BAD_REQUEST);
        }

        ProductMapper.updateProduct(existingProduct, product);

        return ProductMapper.toDto(productRepository.save(existingProduct));
    }

    @Override
    public void deleteProduct(String productId, String userId) {
        ProductDao existingProduct = productRepository.findById(productId).orElseThrow(() -> new BusinessException("Product with given ID does not exist", HttpStatus.NOT_FOUND));

        if(!existingProduct.getUserId().equals(userId)){
            throw new BusinessException("The product can only be deleted by the owner", HttpStatus.BAD_REQUEST);
        }

        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> searchProductByKey(String key) {
        List<ProductDao> products = productCustomRepository.searchByProductOrDescription(key);
        return ProductMapper.toListDto(products);
    }


}
