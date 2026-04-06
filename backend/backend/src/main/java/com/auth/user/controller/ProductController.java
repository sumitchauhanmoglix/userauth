package com.auth.user.controller;

import com.auth.user.model.dto.Product;
import com.auth.user.service.ProductService;
import com.auth.user.util.HeaderConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/product")
@Tag(name = "Product APIs", description = "Performs CRUD related operations for users.")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product, @RequestHeader(name = HeaderConstants.HEADER_USER_ID) String userId){
        return new ResponseEntity<>(productService.createProduct(product, userId), HttpStatus.CREATED);
    }

    // TODO :: this has to be a paginated API
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

}
