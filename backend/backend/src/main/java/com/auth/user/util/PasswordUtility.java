package com.auth.user.util;

public class PasswordUtility {

    public static boolean validateUserName(String username){
        return !username.contains(" ");
    }
}
