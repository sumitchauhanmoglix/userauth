package com.auth.user.service;

import com.auth.user.model.dto.UpdateProductRequest;
import com.auth.user.model.dto.Cart;

public interface CartService {
    Cart getActiveCart(String userId);
    Cart updateProductInCart(String userId, UpdateProductRequest updateProductRequest);
}
