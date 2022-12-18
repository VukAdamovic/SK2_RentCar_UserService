package com.example.SK_Project2.UserService.controller;

import com.example.SK_Project2.UserService.dto.RankCreateDto;
import com.example.SK_Project2.UserService.dto.RankDto;
import com.example.SK_Project2.UserService.dto.user.AdminDto;
import com.example.SK_Project2.UserService.security.CheckSecurity;
import com.example.SK_Project2.UserService.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/admin")
public class AdminController {

    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/{id}")
    //@CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<AdminDto> getAdminById(@RequestHeader("authorization") String authorization,@PathVariable("id") Long id){
        return new ResponseEntity<>(adminService.findById(id), HttpStatus.OK);
    }

    // ------------------------

    @PutMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<AdminDto> updateAdmin(@RequestHeader("authorization") String authorization,@RequestBody AdminDto adminDto) {
        return new ResponseEntity<>(adminService.update(adminDto), HttpStatus.OK);
    }

    @PutMapping("/forbid/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Boolean> forbid(@RequestHeader("authorization") String authorization,@PathVariable("id") Long id) {
        return new ResponseEntity<>(adminService.forbid(id), HttpStatus.OK);
    }
    //--------------------------
    @PostMapping("/setRank")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<RankDto> addRank(@RequestHeader("authorization") String authorization,@RequestBody RankCreateDto rankCreateDto){
        return new ResponseEntity<>(adminService.addUserStatusRank(rankCreateDto),HttpStatus.OK);
    }
}
