package com.auth.user.service;

import com.auth.user.enums.ConfigType;
import com.auth.user.exception.BusinessException;
import com.auth.user.model.dao.ConfigurationDao;
import com.auth.user.repository.ConfigurationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    public ConfigurationService(ConfigurationRepository configurationRepository){
        this.configurationRepository = configurationRepository;
    }

    public ConfigurationDao getConfiguration(ConfigType configType){
        log.info("[ConfigurationService] : getConfiguration : fetching configuration of type {}", configType);
        return configurationRepository.findByConfigType(configType).orElseThrow(() -> new BusinessException("Configuration not found.", HttpStatus.NOT_FOUND));
    }
}

