package com.example.SK_Project2.UserService.controller;


import com.example.SK_Project2.UserService.dto.user.ClientCreateDto;
import com.example.SK_Project2.UserService.dto.user.ClientDto;
import com.example.SK_Project2.UserService.messageHelper.MessageHelper;
import com.example.SK_Project2.UserService.security.CheckSecurity;
import com.example.SK_Project2.UserService.service.ClientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/client")
public class ClientController {

    private ClientService clientService;

    private JmsTemplate jmsTemplate;

    private MessageHelper messageHelper;

    private String registrationDestination;


//    public ClientController(ClientService clientService, JmsTemplate jmsTemplate, MessageHelper messageHelper, String registrationDestination) {
//        this.clientService = clientService;
//        this.jmsTemplate = jmsTemplate;
//        this.messageHelper = messageHelper;
//        this.registrationDestination = registrationDestination;
//    }

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @CheckSecurity(roles = {"ROLE_ADMIN"})
    public ResponseEntity<Page<ClientDto>> getAllClients(@RequestHeader("authorization") String authorization, Pageable pageable){
        return new ResponseEntity<>(clientService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_CLIENT"})
    public ResponseEntity<ClientDto> getClientById(@RequestHeader("authorization") String authorization,@PathVariable("id") Long id){
        return new ResponseEntity<>(clientService.findById(id), HttpStatus.OK);
    }
    //---------------------

    @PostMapping("/registration")
    @CheckSecurity(roles = {"ROLE_CLIENT"})
    public ResponseEntity<ClientDto> registerClient(@RequestHeader("authorization") String authorization,@RequestBody ClientCreateDto clientCreateDto) {
        //jmsTemplate.convertAndSend(registrationDestination,messageHelper.createTextMessage(clientCreateDto));
        return new ResponseEntity<>(clientService.add(clientCreateDto), HttpStatus.CREATED);
    }
    //---------------------

    @DeleteMapping("/{id}")
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_CLIENT"})
    public ResponseEntity<Boolean> deleteClient(@RequestHeader("authorization") String authorization,@PathVariable("id") Long id) {
        return new ResponseEntity<>(clientService.delete(id), HttpStatus.OK);
    }

    @PutMapping
    @CheckSecurity(roles = {"ROLE_ADMIN","ROLE_CLIENT"})
    public ResponseEntity<ClientDto> updateClient(@RequestHeader("authorization") String authorization,@RequestBody ClientDto clientDto) {
        return new ResponseEntity<>(clientService.update(clientDto), HttpStatus.OK);
    }

//    @PutMapping("/addReservation/{id}/{days}")
//    @CheckSecurity(roles = {"ROLE_CLIENT"})
//    public ResponseEntity<Boolean> addReservation(@PathVariable("id,days") Long id, Integer days) {
//        return new ResponseEntity<>(clientService.addReservation(id,days), HttpStatus.OK);
//    }

}
