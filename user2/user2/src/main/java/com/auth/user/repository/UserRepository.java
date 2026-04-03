package com.auth.user.repository;

import com.auth.user.model.dao.UserDao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<UserDao, UUID> {

    boolean existsByUsername(String username);

    Optional<UserDao> findByUsername(String username);
}
