package com.auth.user.repository;

import com.auth.user.model.dao.ProductDao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<ProductDao, String>{
}
