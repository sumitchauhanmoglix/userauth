package com.auth.user.model.dao;

import com.auth.user.enums.CartStatus;
import com.auth.user.model.dto.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "carts")
public class CartDao {

    @Id
    private String id;

    @Field(name = "products")
    private List<ProductDao> products;

    @Field(name = "status")
    private CartStatus status;

    @Field(name = "userId")
    private String userId;
}
