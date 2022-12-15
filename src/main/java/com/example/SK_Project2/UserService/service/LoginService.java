package com.example.SK_Project2.UserService.service;

import com.example.SK_Project2.UserService.dto.token.TokenRequestDto;
import com.example.SK_Project2.UserService.dto.token.TokenResponseDto;

public interface LoginService {

    TokenResponseDto login(TokenRequestDto tokenRequestDto);
}
