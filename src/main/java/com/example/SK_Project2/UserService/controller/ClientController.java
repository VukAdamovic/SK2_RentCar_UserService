package com.example.SK_Project2.UserService.controller;


import com.example.SK_Project2.UserService.dto.rental.DiscountDto;
import com.example.SK_Project2.UserService.dto.user.ClientCreateDto;
import com.example.SK_Project2.UserService.dto.user.ClientDto;
import com.example.SK_Project2.UserService.security.CheckSecurity;
import com.example.SK_Project2.UserService.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/client")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<List<ClientDto>> getAllClients(@RequestHeader("authorization") String authorization){
        return new ResponseEntity<>(clientService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_CLIENT"})
    public ResponseEntity<ClientDto> getClientById(@RequestHeader("authorization") String authorization,@PathVariable("id") Long id){
        return new ResponseEntity<>(clientService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/discount")
    public ResponseEntity<DiscountDto> getDiscount(@PathVariable("id") Long id){
        return new ResponseEntity<>(clientService.findDiscount(id),HttpStatus.OK);
    }
    //---------------------

    @PostMapping("/registration")
    @CheckSecurity(roles = {"ROLE_CLIENT","ROLE_ADMIN"})
    public ResponseEntity<ClientDto> registerClient(@RequestHeader("authorization") String authorization,@RequestBody ClientCreateDto clientCreateDto) {
        return new ResponseEntity<>(clientService.add(clientCreateDto), HttpStatus.CREATED);
    }

    //---------------------

    @DeleteMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_CLIENT"})
    public ResponseEntity<Boolean> deleteClient(@RequestHeader("authorization") String authorization,@PathVariable("id") Long id) {
        return new ResponseEntity<>(clientService.delete(id), HttpStatus.OK);
    }


    //---------------------

    @PutMapping
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_CLIENT"})
    public ResponseEntity<ClientDto> updateClient(@RequestHeader("authorization") String authorization,@RequestBody ClientDto clientDto) {
        return new ResponseEntity<>(clientService.update(clientDto), HttpStatus.OK);
    }

    @PutMapping("/registration/{link}")
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<Boolean> verificationEmail(@RequestHeader("authorization") String authorization,@PathVariable("link") String link) {
        return new ResponseEntity<>(clientService.verificationEmail(link), HttpStatus.OK);
    }
}
