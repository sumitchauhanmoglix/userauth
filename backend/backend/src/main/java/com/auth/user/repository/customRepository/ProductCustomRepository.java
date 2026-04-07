package com.auth.user.repository.customRepository;

import com.auth.user.model.dao.ProductDao;
import com.auth.user.model.dto.PageRequest;
import org.springframework.data.domain.Sort;
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

    public List<ProductDao> getAllProducts(PageRequest pageRequest){
        Query query = new Query();
        setLimitAndOffset(query, pageRequest);
        return mongoTemplate.find(query, ProductDao.class);
    }

    private void createOrderByClause(Query query, PageRequest pageRequest){
        if(pageRequest.getOrder().equalsIgnoreCase(Sort.Direction.ASC.name())){
            query.with(Sort.by(Sort.Direction.ASC, pageRequest.getSort()));
        }else{
            query.with(Sort.by(Sort.Direction.DESC, pageRequest.getSort()));
        }
    }

    private void setLimitAndOffset(Query query, PageRequest pageRequest){
        query.limit(pageRequest.getLimit());
        query.skip(pageRequest.getOffset());
    }
}
