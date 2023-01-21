package com.example.SK_Project2.UserService.controller;


import com.example.SK_Project2.UserService.dto.user.ManagerCreateDto;
import com.example.SK_Project2.UserService.dto.user.ManagerDto;
import com.example.SK_Project2.UserService.security.CheckSecurity;
import com.example.SK_Project2.UserService.service.ManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/manager")
public class ManageController {

    private ManagerService managerService;

    public ManageController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<List<ManagerDto>> getAllManagers(@RequestHeader("authorization") String authorization){
        return new ResponseEntity<>(managerService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER"})
    public ResponseEntity<ManagerDto> getManagerById(@RequestHeader("authorization") String authorization,@PathVariable("id") Long id){
        return new ResponseEntity<>(managerService.findById(id), HttpStatus.OK);
    }
    //---------------------

    @PostMapping("/registration")
    @CheckSecurity(roles = {"ROLE_MANAGER","ROLE_ADMIN"})
    public ResponseEntity<ManagerDto> registerManager(@RequestHeader("authorization") String authorization,@RequestBody ManagerCreateDto managerCreateDto) {
        return new ResponseEntity<>(managerService.add(managerCreateDto), HttpStatus.CREATED);
    }


    //---------------------

    @DeleteMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER"})
    public ResponseEntity<Boolean> deleteManager(@RequestHeader("authorization") String authorization,@PathVariable("id") Long id) {
        return new ResponseEntity<>(managerService.delete(id), HttpStatus.OK);
    }

    //---------------------

    @PutMapping
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER"})
    public ResponseEntity<ManagerDto> updateManager(@RequestHeader("authorization") String authorization,@RequestBody ManagerDto managerDto) {
        return new ResponseEntity<>(managerService.update(managerDto), HttpStatus.OK);
    }

    @GetMapping("/registration/{link}")
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<Boolean> verificationEmail(@RequestHeader("authorization") String authorization,@PathVariable("link") String link) {
        return new ResponseEntity<>(managerService.verificationEmail(link), HttpStatus.OK);
    }
}
