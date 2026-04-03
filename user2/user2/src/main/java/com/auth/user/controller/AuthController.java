package com.auth.user.controller;

import com.auth.user.model.dto.UserToken;
import com.auth.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication APIs", description = "Performs operations related to authentication")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/refresh-token")
    @Operation(summary = "Used to get new access token.")
    public ResponseEntity<UserToken> refreshToken(@RequestBody @Valid UserToken userToken){
        return new ResponseEntity<>(authService.refreshToken(userToken), HttpStatus.OK);
    }

}
