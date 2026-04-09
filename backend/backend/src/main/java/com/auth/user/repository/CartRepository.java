package com.auth.user.repository;

import com.auth.user.model.dao.CartDao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<CartDao, String> {
    Optional<CartDao> findByUserId(String userID);
}
