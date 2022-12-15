package com.example.SK_Project2.UserService.security.service;

import io.jsonwebtoken.Claims;

public interface TokenService {
    String generate(Claims claims);

    Claims parseToken(String jwt);
}
