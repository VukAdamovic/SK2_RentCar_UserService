package com.example.SK_Project2.UserService.service;

import com.example.SK_Project2.UserService.dto.DiscountDto;
import com.example.SK_Project2.UserService.dto.user.ClientCreateDto;
import com.example.SK_Project2.UserService.dto.user.ClientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    Page<ClientDto> findAll(Pageable pageable);

    ClientDto findById(Long id);

    ClientDto add(ClientCreateDto clientCreateDto);

    Boolean delete(Long id);
    ClientDto update(ClientDto clientDto);

    void incrementRentCar(Long id, Integer days);
    DiscountDto findDiscount(Long id);









}
