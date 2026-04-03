package com.auth.user.model.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class UserDao {
    @Id
    private String id;

    @Field(name = "username")
    private String username;

    @Field(name = "password")
    private String password;

    @Field(name = "retry_count")
    private int retryCount;
}
