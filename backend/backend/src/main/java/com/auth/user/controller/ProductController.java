package com.auth.user.controller;

import com.auth.user.model.dto.Product;
import com.auth.user.service.ProductService;
import com.auth.user.util.HeaderConstants;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Endpoint used to create product.")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product, @RequestHeader(name = HeaderConstants.HEADER_USER_ID) String userId){
        return new ResponseEntity<>(productService.createProduct(product, userId), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Endpoint used to fetch all products.")
    public ResponseEntity<List<Product>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Endpoint to fetch product by Id.")
    public ResponseEntity<Product> getProductById(@PathVariable(name = "id") String productId){
        return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Endpoint used to update the product.")
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product, @PathVariable(name = "id") String productId, @RequestHeader(name = HeaderConstants.HEADER_USER_ID) String userId){
        return new ResponseEntity<>(productService.updateProduct(product, productId, userId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Endpoint used to delete te product.")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id") String productId, @RequestHeader(name = HeaderConstants.HEADER_USER_ID) String userId){
        productService.deleteProduct(productId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search/{key}")
    @Operation(summary = "Endpoint used to search a product for a specific value.")
    public ResponseEntity<List<Product>> searchProductByKey(@PathVariable(name = "key") String key){
        return new ResponseEntity<>(productService.searchProductByKey(key), HttpStatus.OK);
    }

}
