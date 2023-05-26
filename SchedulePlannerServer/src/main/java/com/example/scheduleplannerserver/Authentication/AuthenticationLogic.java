package com.example.scheduleplannerserver.Authentication;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class AuthenticationLogic {
    public static String generateSalt(){
        return BCrypt.gensalt();
    }

    public static String generatePasswordHash(String password, String salt){
        return BCrypt.hashpw(password, salt);
    }
}
