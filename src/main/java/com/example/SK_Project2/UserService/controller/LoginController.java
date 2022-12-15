package com.example.SK_Project2.UserService.controller;

import com.example.SK_Project2.UserService.dto.token.TokenRequestDto;
import com.example.SK_Project2.UserService.dto.token.TokenResponseDto;
import com.example.SK_Project2.UserService.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class LoginController {

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(loginService.login(tokenRequestDto), HttpStatus.OK);
    }
}
