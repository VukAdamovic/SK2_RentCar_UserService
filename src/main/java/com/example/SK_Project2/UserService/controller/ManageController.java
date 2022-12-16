package com.example.SK_Project2.UserService.controller;


import com.example.SK_Project2.UserService.dto.user.ManagerCreateDto;
import com.example.SK_Project2.UserService.dto.user.ManagerDto;
import com.example.SK_Project2.UserService.security.CheckSecurity;
import com.example.SK_Project2.UserService.service.ManagerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/manager")
public class ManageController {

    private ManagerService managerService;

    public ManageController(ManagerService managerService) {
        this.managerService = managerService;
    }


    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Page<ManagerDto>> getAllManagers(@RequestHeader("authorization") String authorization, Pageable pageable){
        return new ResponseEntity<>(managerService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER"})
    public ResponseEntity<ManagerDto> getManagerById(@PathVariable("id") Long id){
        return new ResponseEntity<>(managerService.findById(id), HttpStatus.OK);
    }
    //---------------------

    @PostMapping("/registration")
    @CheckSecurity(roles = {"ROLE_MANAGER"})
    public ResponseEntity<ManagerDto> registerManager(@RequestBody ManagerCreateDto managerCreateDto) {
        return new ResponseEntity<>(managerService.add(managerCreateDto), HttpStatus.CREATED);
    }
    //---------------------

    @DeleteMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER"})
    public ResponseEntity<Boolean> deleteManager(@PathVariable("id") Long id) {
        return new ResponseEntity<>(managerService.delete(id), HttpStatus.OK);
    }

    @PutMapping
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_MANAGER"})
    public ResponseEntity<ManagerDto> updateManager(@RequestBody ManagerDto managerDto) {
        return new ResponseEntity<>(managerService.update(managerDto), HttpStatus.OK);
    }

}
