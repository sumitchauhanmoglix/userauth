package com.auth.user.service.serviceImpl;

import com.auth.user.enums.CartStatus;
import com.auth.user.enums.UpdateType;
import com.auth.user.exception.BusinessException;
import com.auth.user.mapper.CartMapper;
import com.auth.user.model.dao.CartDao;
import com.auth.user.model.dao.ProductDao;
import com.auth.user.model.dto.UpdateProductRequest;
import com.auth.user.model.dto.Cart;
import com.auth.user.model.dto.Product;
import com.auth.user.repository.CartRepository;
import com.auth.user.service.CartService;
import com.auth.user.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;

    public CartServiceImpl(CartRepository cartRepository, ProductService productService){
        this.cartRepository = cartRepository;
        this.productService = productService;
    }

    @Override
    public Cart getActiveCart(String userId){
        return CartMapper.toDto(getActiveCartDao(userId));
    }

    private CartDao getActiveCartDao(String userId){
        CartDao cartDao = cartRepository.findByUserId(userId).orElse(null);

        if(Objects.isNull(cartDao)){
            CartDao emptyCart = new CartDao();
            emptyCart.setProducts(new ArrayList<>());
            emptyCart.setStatus(CartStatus.ACTIVE);
            emptyCart.setUserId(userId);
            return cartRepository.save(emptyCart);
        }

        return cartDao;
    }

    @Override
    public Cart updateProductInCart(String userId, UpdateProductRequest updateProductRequest){
        CartDao cartDao = getActiveCartDao(userId);

        Product product = productService.getProductById(updateProductRequest.getProductId());

        if(updateProductRequest.getUpdateType().equals(UpdateType.INCREMENT)){
            boolean exists = false;
            for(ProductDao productDao : cartDao.getProducts()){
                if(productDao.getId().equals(updateProductRequest.getProductId())){
                    productDao.setQuantity(productDao.getQuantity()+ updateProductRequest.getQuantity());
                    exists = true;
                }
            }

            if(!exists){
                cartDao.getProducts().add(ProductDao.builder().id(updateProductRequest.getProductId()).quantity(updateProductRequest.getQuantity()).selected(true).build());
            }
        }else{
            ProductDao existingProduct = null;
            for(ProductDao productDao : cartDao.getProducts()) {
                if (productDao.getId().equals(updateProductRequest.getProductId())) {
                    existingProduct = productDao;
                }
            }
            if(Objects.isNull(existingProduct)){
                throw new BusinessException("Product to be removed does not exist.", HttpStatus.BAD_REQUEST);
            }
            existingProduct.setQuantity(existingProduct.getQuantity() - updateProductRequest.getQuantity());
            if(existingProduct.getQuantity() <= 0){
                cartDao.getProducts().remove(existingProduct);
            }
        }


        validateProductStock(product, cartDao);

        return CartMapper.toDto(cartRepository.save(cartDao));
    }

    private void validateProductStock(Product product, CartDao cartDao){
        for(ProductDao productDao : cartDao.getProducts()){
            if(productDao.getId().equals(product.getId()) && (product.getQuantity() <= 0 || (product.getQuantity() - productDao.getQuantity() < 0))){
                throw new BusinessException("Item out of stock. Please decrease the quantity or try again later.", HttpStatus.BAD_REQUEST);
            }
        }
    }
}
