package com.auth.user.service;

import com.auth.user.model.dto.User;

public interface UserService {
    User createUser(User user);
    User loginUser(User user);
    User resetPassword(User user);
}
