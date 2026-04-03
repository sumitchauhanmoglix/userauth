package com.auth.user.service.serviceImpl;

import com.auth.user.enums.ConfigType;
import com.auth.user.exception.BusinessException;
import com.auth.user.model.dao.ConfigurationDao;
import com.auth.user.repository.ConfigurationRepository;
import com.auth.user.service.ConfiguartionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConfigurationServiceImpl implements ConfiguartionService {

    private final ConfigurationRepository configurationRepository;

    public ConfigurationServiceImpl(ConfigurationRepository configurationRepository){
        this.configurationRepository = configurationRepository;
    }

    @Override
    public ConfigurationDao getConfiguration(ConfigType configType){
        log.info("[ConfigurationService] : getConfiguration : fetching configuration of type {}", configType);
        return configurationRepository.findByConfigType(configType).orElseThrow(() -> new BusinessException("Configuration not found.", HttpStatus.NOT_FOUND));
    }
}

