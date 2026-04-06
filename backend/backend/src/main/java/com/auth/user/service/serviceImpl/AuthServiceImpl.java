package com.auth.user.service.serviceImpl;

import com.auth.user.exception.BusinessException;
import com.auth.user.model.dao.RefreshTokenUserMappingDao;
import com.auth.user.model.dto.User;
import com.auth.user.model.dto.UserToken;
import com.auth.user.service.AuthService;
import com.auth.user.service.helper.JwtHelperService;
import com.auth.user.service.helper.RefreshTokenUserMappingHelperService;
import com.auth.user.util.HeaderConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Value("${access.token.jwt.expiration.time}")
    private long accessTokenExpirationTime;

    @Value("${refresh.token.jwt.expiration.time}")
    private long refreshTokenExpirationTime;

    private final JwtHelperService jwtHelperService;
    private final RefreshTokenUserMappingHelperService refreshTokenUserMappingHelperService;

    public AuthServiceImpl(JwtHelperService jwtHelperService, RefreshTokenUserMappingHelperService refreshTokenUserMappingHelperService){
        this.jwtHelperService = jwtHelperService;
        this.refreshTokenUserMappingHelperService = refreshTokenUserMappingHelperService;
    }

    @Override
    public HttpHeaders authenticateUser(User user){
        log.info("[AuthService] : authenticateUser : authenticating user with username {}", user.getUsername());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HeaderConstants.HEADER_ACCESS_TOKEN, jwtHelperService.generateToken(user.getUsername(), accessTokenExpirationTime));
        String refreshToken = jwtHelperService.generateToken(user.getUsername(), refreshTokenExpirationTime);
        httpHeaders.add(HeaderConstants.HEADER_REFRESH_TOKEN, refreshToken);
        httpHeaders.add(HeaderConstants.HEADER_USER_ID, user.getId());

        RefreshTokenUserMappingDao refreshTokenUserMappingDao = new RefreshTokenUserMappingDao();
        refreshTokenUserMappingDao.setUsername(user.getUsername());
        refreshTokenUserMappingDao.setToken(refreshToken);

        refreshTokenUserMappingHelperService.saveRefreshToken(refreshTokenUserMappingDao);
        return httpHeaders;
    }

    @Override
    public void logout(UserToken userToken){
        refreshTokenUserMappingHelperService.deleteRefreshToken(userToken.getToken());
    }

    @Override
    public UserToken refreshToken(UserToken userToken){
        log.info("[AuthService] : refreshToken : refreshing access token of user with username {}", userToken.getUsername());
        RefreshTokenUserMappingDao existingRefreshToken = refreshTokenUserMappingHelperService.getRefreshTokenMappingByToken(userToken.getToken());

        if(Objects.isNull(existingRefreshToken)){
            throw new BusinessException("Invalid token, please login again.", HttpStatus.BAD_REQUEST);
        }

        if(jwtHelperService.validateToken(existingRefreshToken.getToken())){
            userToken.setToken(jwtHelperService.generateToken(userToken.getUsername(), accessTokenExpirationTime));
        }else{
            refreshTokenUserMappingHelperService.deleteRefreshToken(userToken.getToken());
            throw new BusinessException("Invalid token, please login again.", HttpStatus.BAD_REQUEST);
        }

        return userToken;
    }

}
