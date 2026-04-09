package com.auth.user.mapper;

import com.auth.user.model.dao.CartDao;
import com.auth.user.model.dto.Cart;

public class CartMapper {

    public static CartDao toDao(Cart cart){
        return CartDao.builder()
                .products(ProductMapper.toListDao(cart.getProducts()))
                .build();
    }

    public static Cart toDto(CartDao cartDao){
        return Cart.builder()
                .id(cartDao.getId())
                .products(ProductMapper.toListDto(cartDao.getProducts()))
                .build();
    }
}
