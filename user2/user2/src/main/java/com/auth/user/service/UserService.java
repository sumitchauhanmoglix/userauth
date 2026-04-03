package com.auth.user.service;

import com.auth.user.enums.ConfigType;
import com.auth.user.exception.BusinessException;
import com.auth.user.mapper.UserMapper;
import com.auth.user.model.dao.ConfigurationDao;
import com.auth.user.model.dao.UserDao;
import com.auth.user.model.dto.User;
import com.auth.user.repository.UserRepository;
import com.auth.user.service.helper.EncryptionHelperService;
import com.auth.user.util.Constants;
import com.auth.user.util.PasswordUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ConfigurationService configurationService;
    private final EncryptionHelperService encryptionHelperService;

    public UserService(UserRepository userRepository, ConfigurationService configurationService, EncryptionHelperService encryptionHelperService){
        this.userRepository = userRepository;
        this.configurationService = configurationService;
        this.encryptionHelperService = encryptionHelperService;
    }

    public User createUser(User user){
        log.info("[UserService] : createUser : creating user with username {}", user.getUsername());
        if(userRepository.existsByUsername(user.getUsername())){
            throw new BusinessException("User with this username exists, please login, or try a different username.", HttpStatus.BAD_REQUEST);
        }
        if(!PasswordUtility.validateUserName(user.getUsername())){
            throw new BusinessException("Username cannot contains spaces", HttpStatus.BAD_REQUEST);
        }
        UserDao userDao = UserMapper.mapUserToDao(user);
        ConfigurationDao configurationDao = configurationService.getConfiguration(ConfigType.USER_CONFIG);
        userDao.setRetryCount((int)configurationDao.getData().get(Constants.RETRY_COUNT));
        userDao.setPassword(encryptionHelperService.encrypt(user.getPassword()));
        return UserMapper.mapUserDaoToDto(userRepository.save(userDao));
    }



    public User loginUser(User user){
        log.info("[UserService] : login user : logging in user with username {}", user.getUsername());
        UserDao existingUser = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new BusinessException("Username does not exist or is incorrect, please try again.", HttpStatus.BAD_REQUEST));
        String existingPassword = encryptionHelperService.decrypt(existingUser.getPassword());

        if(existingPassword.equals(user.getPassword()) && existingUser.getRetryCount() > 0){
            ConfigurationDao configurationDao = configurationService.getConfiguration(ConfigType.USER_CONFIG);
            existingUser.setRetryCount((int)configurationDao.getData().get(Constants.RETRY_COUNT));
            return UserMapper.mapUserDaoToDto(userRepository.save(existingUser));
        }else{
            synchronized (this){
                int remainingRetries = existingUser.getRetryCount();
                if(remainingRetries <= 0){
                    throw new BusinessException("Account locked, please contact support team for assistance.", HttpStatus.FORBIDDEN);
                }else{
                    remainingRetries -= 1;
                    existingUser.setRetryCount(remainingRetries);
                    userRepository.save(existingUser);
                    throw new BusinessException("Incorrect password, " + remainingRetries + " attempts remaining.", HttpStatus.BAD_REQUEST );
                }
            }
        }
    }
}
