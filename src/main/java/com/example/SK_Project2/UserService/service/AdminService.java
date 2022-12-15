package com.example.SK_Project2.UserService.service;


import com.example.SK_Project2.UserService.dto.user.*;

public interface AdminService {
    AdminDto findById(Long id);

    AdminDto update(AdminDto adminDto);

    Boolean forbid(Long id);
    Boolean setRank(Long id);

}
