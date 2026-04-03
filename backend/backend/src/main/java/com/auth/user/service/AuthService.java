package com.auth.user.service;

import com.auth.user.model.dto.User;
import com.auth.user.model.dto.UserToken;
import org.springframework.http.HttpHeaders;

public interface AuthService {
    HttpHeaders authenticateUser(User user);
    void logout(UserToken userToken);
    UserToken refreshToken(UserToken userToken);
}
