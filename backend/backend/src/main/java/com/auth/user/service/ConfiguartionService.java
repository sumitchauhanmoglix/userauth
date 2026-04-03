package com.auth.user.service;

import com.auth.user.enums.ConfigType;
import com.auth.user.model.dao.ConfigurationDao;

public interface ConfiguartionService {
    ConfigurationDao getConfiguration(ConfigType configType);
}
