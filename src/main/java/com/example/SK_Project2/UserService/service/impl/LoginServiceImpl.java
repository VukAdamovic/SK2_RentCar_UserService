package com.example.SK_Project2.UserService.service.impl;

import com.example.SK_Project2.UserService.domain.User;
import com.example.SK_Project2.UserService.dto.token.TokenRequestDto;
import com.example.SK_Project2.UserService.dto.token.TokenResponseDto;
import com.example.SK_Project2.UserService.exception.NotFoundException;
import com.example.SK_Project2.UserService.repository.UserRepository;
import com.example.SK_Project2.UserService.security.service.TokenService;
import com.example.SK_Project2.UserService.service.LoginService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private TokenService tokenService;
    private UserRepository userRepository;

    public LoginServiceImpl(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    public TokenResponseDto login(TokenRequestDto tokenRequestDto) {
        //Try to find active user for specified credentials


        User user = userRepository
                .findUserByEmailAndPassword(tokenRequestDto.getEmail(), tokenRequestDto.getPassword())
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with email: %s and password: %s not found.", tokenRequestDto.getEmail(),
                                tokenRequestDto.getPassword())));



        if(user.isForbidden()){
            return  new TokenResponseDto("Nemas dozvolu da pristupis app");
        }
        //Create token payload
        Claims claims = Jwts.claims();
        claims.put("id", user.getId());
        claims.put("role", user.getRole().getName());
        claims.put("forbidden",user.isForbidden());

        //Generate token
        return new TokenResponseDto(tokenService.generate(claims));
    }
}
