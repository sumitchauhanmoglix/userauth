package com.auth.user.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class BusinessException extends RuntimeException{

    private HttpStatus status;

    public BusinessException(String message, HttpStatus status){
        super(message);
        this.status = status;
    }

    public BusinessException(HttpStatus status){
        this.status = status;
    }
}
