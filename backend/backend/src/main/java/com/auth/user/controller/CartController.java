package com.auth.user.controller;

import com.auth.user.model.dto.UpdateProductRequest;
import com.auth.user.model.dto.Cart;
import com.auth.user.service.CartService;
import com.auth.user.util.HeaderConstants;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Cart> getActiveCart(@RequestHeader(HeaderConstants.HEADER_USER_ID) String userId){
        return new ResponseEntity<>(cartService.getActiveCart(userId), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Cart> updateProduct(@RequestHeader(HeaderConstants.HEADER_USER_ID) String userId, @Valid @RequestBody UpdateProductRequest updateProductRequest){
        return new ResponseEntity<>(cartService.updateProductInCart(userId, updateProductRequest), HttpStatus.OK);
    }






}
