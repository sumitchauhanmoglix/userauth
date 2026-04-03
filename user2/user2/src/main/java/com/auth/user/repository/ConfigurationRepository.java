package com.auth.user.repository;

import com.auth.user.enums.ConfigType;
import com.auth.user.model.dao.ConfigurationDao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ConfigurationRepository extends MongoRepository<ConfigurationDao, String> {
    Optional<ConfigurationDao> findByConfigType(ConfigType configType);
}
