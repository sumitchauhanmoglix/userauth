package com.auth.user.repository.customRepository;

import com.auth.user.model.dao.ProductDao;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductCustomRepository {
    private final MongoTemplate mongoTemplate;

    public ProductCustomRepository(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    public List<ProductDao> searchByProductOrDescription(String key){
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("name").regex(key, "i"),
                Criteria.where("description").regex(key, "i")
        );
        Query query = new Query(criteria);

        return mongoTemplate.find(query, ProductDao.class);
    }
}
