package com.example.SK_Project2.UserService.service;

import com.example.SK_Project2.UserService.dto.DiscountDto;
import com.example.SK_Project2.UserService.dto.user.ClientCreateDto;
import com.example.SK_Project2.UserService.dto.user.ClientDto;


import java.util.List;

public interface ClientService {
    List<ClientDto> findAll();

    ClientDto findById(Long id);

    ClientDto add(ClientCreateDto clientCreateDto);

    Boolean delete(Long id);
    ClientDto update(ClientDto clientDto);

    void incrementRentCar(Long id, Integer days);

    void decrementRentCar(Long id, Integer days);
    DiscountDto findDiscount(Long id);









}
