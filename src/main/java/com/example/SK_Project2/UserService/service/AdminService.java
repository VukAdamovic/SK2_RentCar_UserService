package com.example.SK_Project2.UserService.service;


import com.example.SK_Project2.UserService.dto.RankCreateDto;
import com.example.SK_Project2.UserService.dto.RankDto;
import com.example.SK_Project2.UserService.dto.user.*;

public interface AdminService {
    AdminDto findById(Long id);

    AdminDto update(AdminDto adminDto);

    Boolean forbid(Long id);
    RankDto addUserStatusRank(RankCreateDto rankCreateDto); //rankDto name od do ,  nema id

}
