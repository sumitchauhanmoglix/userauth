package com.auth.user.repository;

import com.auth.user.model.dao.RefreshTokenUserMappingDao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenUserMappingRepository extends MongoRepository<RefreshTokenUserMappingDao, String> {
    Optional<RefreshTokenUserMappingDao> findByUsername(String username);
    Optional<RefreshTokenUserMappingDao> findByToken(String token);
    void deleteByToken(String token);
}
