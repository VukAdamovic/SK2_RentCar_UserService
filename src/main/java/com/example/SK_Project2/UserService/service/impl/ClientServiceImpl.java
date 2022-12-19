package com.example.SK_Project2.UserService.service.impl;

import com.example.SK_Project2.UserService.domain.Role;
import com.example.SK_Project2.UserService.domain.User;
import com.example.SK_Project2.UserService.domain.UserStatus;
import com.example.SK_Project2.UserService.dto.DiscountDto;
import com.example.SK_Project2.UserService.dto.user.ClientCreateDto;
import com.example.SK_Project2.UserService.dto.user.ClientDto;
import com.example.SK_Project2.UserService.exception.NotFoundException;
import com.example.SK_Project2.UserService.mapper.ClientMapper;
import com.example.SK_Project2.UserService.messageHelper.MessageHelper;
import com.example.SK_Project2.UserService.repository.RoleRepository;
import com.example.SK_Project2.UserService.repository.UserRepository;
import com.example.SK_Project2.UserService.repository.UserStatusRepository;
import com.example.SK_Project2.UserService.service.ClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserStatusRepository userStatusRepository;
    private ClientMapper clientMapper;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;
    private String registrationDestination;

    public ClientServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                             UserStatusRepository userStatusRepository, ClientMapper clientMapper,
                             JmsTemplate jmsTemplate, MessageHelper messageHelper, @Value("${destination.incrementRentCar}") String registrationDestination) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userStatusRepository = userStatusRepository;
        this.clientMapper = clientMapper;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.registrationDestination = registrationDestination;
    }

    @Override
    public List<ClientDto> findAll() {
        List<ClientDto> clients = new ArrayList<>();
        userRepository.findAll()
                .forEach(user -> {
                            if (user.getRole().getName().equals("ROLE_CLIENT"))
                                clients.add(clientMapper.userToClientDto(user));
                        }
                );
        return clients;
    }

    @Override
    public ClientDto findById(Long id) {
        return userRepository.findById(id)
                .map(clientMapper::userToClientDto)
                .orElseThrow(() -> new NotFoundException(String.format("Client with id: %d does not exists.", id)));
    }

    @Override
    public ClientDto add(ClientCreateDto clientCreateDto) {
        Role role = roleRepository.findRoleByName("ROLE_CLIENT")
                .orElseThrow(() -> new NotFoundException("Role with name: ROLE_CLIENT not found."));

        User client = clientMapper.clientCreateDtoToUser(clientCreateDto);
        client.setRole(role);
        userRepository.save(client);

        jmsTemplate.convertAndSend(registrationDestination,messageHelper.createTextMessage(clientCreateDto));
        return clientMapper.userToClientDto(client);
    }

    @Override
    public Boolean delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Client with id: %d does not exists.", id)));

        userRepository.delete(user);
        return true;
    }

    @Override
    public ClientDto update(ClientDto clientDto) {
        User user = userRepository.findById(clientDto.getId())
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d does not exists.", clientDto.getId())));

        user.setId(clientDto.getId());
        user.setUsername(clientDto.getUsername());
        user.setEmail(clientDto.getEmail());
        user.setPhone(clientDto.getPhone());
        user.setDayOfBirth(clientDto.getDayOfBirth());
        user.setFirstName(clientDto.getFirstName());
        user.setLastName(clientDto.getLastName());
        user.setPassport(clientDto.getPassport());

        userRepository.save(user);

        return clientMapper.userToClientDto(user);
    }

    @Override // ovo zove onaj lisener a ne controller da znas
    public void incrementRentCar(Long id, Integer days) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d does not exists.", id)));
        user.setRentCarTotalDuration(user.getRentCarTotalDuration() + days);
        //setuj mu novi rank
        userRepository.save(user);
    }

    @Override
    public DiscountDto findDiscount(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException(String
                        .format("User with id: %d not found.", id)));
        List<UserStatus> userStatusList = userStatusRepository.findAll();
        //get discount
        Integer discount = userStatusList.stream()
                .filter(userStatus -> userStatus.getMaxTotalNumberOfRentCar() >= user.getRentCarTotalDuration()
                        && userStatus.getMinTotalNumberOfRentCar() <= user.getRentCarTotalDuration())
                .findAny()
                .get()
                .getDiscount();
        return new DiscountDto(discount);
    }
}



