package com.example.SK_Project2.UserService.controller;

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
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<AdminDto> getAdminById(@PathVariable("id") Long id){
        return new ResponseEntity<>(adminService.findById(id), HttpStatus.OK);
    }

    // ------------------------

    @PutMapping("/edit")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<AdminDto> updateAdmin(@RequestBody AdminDto adminDto) {
        return new ResponseEntity<>(adminService.update(adminDto), HttpStatus.OK);
    }

    @PutMapping("/forbid/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Boolean> forbid(@PathVariable("id") Long id) {
        return new ResponseEntity<>(adminService.forbid(id), HttpStatus.OK);
    }

    @PutMapping("/setRank/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Boolean> setRank(@PathVariable("id") Long id) {
        return new ResponseEntity<>(adminService.setRank(id), HttpStatus.OK);
    }
}
