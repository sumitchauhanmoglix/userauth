package com.auth.user.service.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class EncryptionHelperService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public EncryptionHelperService(BCryptPasswordEncoder bCryptPasswordEncoder){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public String encrypt(String value){
        return bCryptPasswordEncoder.encode(value);
    }

    public boolean matches(String rawPassword, String hashedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, hashedPassword);
    }

}
