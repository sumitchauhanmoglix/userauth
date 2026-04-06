package com.auth.user.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "configurations")
public class ProductDao {

    @Id
    private String id;

    @Field("quantity")
    private int quantity;

    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("url")
    private String url;

    @Field("user_id")
    private String userId;
}
