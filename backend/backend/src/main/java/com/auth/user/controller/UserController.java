package com.auth.user.controller;

import com.auth.user.model.dto.User;
import com.auth.user.model.dto.UserToken;
import com.auth.user.model.dto.validationGroups.UpdatePasswordGroup;
import com.auth.user.service.AuthService;
import com.auth.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.groups.Default;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User APIs", description = "Operations related to users")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService){
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Used for signing in user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        log.info("[UserController] : createUser : create user request for username : {}", user.getUsername());
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser,authService.authenticateUser(newUser),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Used for logging in user")
    public ResponseEntity<User> loginUser(@Valid @RequestBody User user){
        log.info("[UserController] : loginUser : logging in user for username : {}", user.getUsername());
        User existingUser = userService.loginUser(user);
        return new ResponseEntity<>(existingUser,authService.authenticateUser(existingUser),HttpStatus.OK);
    }

    @PostMapping("/logout")
    @Operation(summary = "Used for logging out user")
    public ResponseEntity<Void> logoutUser(@Valid @RequestBody UserToken userToken){
        log.info("[UserController] : logoutUser : logging out user for username : {}", userToken.getUsername());
        authService.logout(userToken);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Used to reset the password")
    public ResponseEntity<User> resetPassword(@Validated({Default.class, UpdatePasswordGroup.class}) @RequestBody User user){
        log.info("[UserController] : resetPassword : reset password for username : {}", user.getUsername());
        return new ResponseEntity<>(userService.resetPassword(user), HttpStatus.OK);
    }
}