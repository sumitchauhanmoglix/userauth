package com.auth.user.mapper;

import com.auth.user.model.dao.UserDao;
import com.auth.user.model.dto.User;

// we can use struct mapper here as well or bean utils mapper
public class UserMapper {

    public static User mapUserDaoToDto(UserDao userDao){
        return User.builder().id(userDao.getId())
                .username(userDao.getUsername())
                .build();
    }

    public static UserDao mapUserToDao(User user){
        return UserDao.builder().username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
