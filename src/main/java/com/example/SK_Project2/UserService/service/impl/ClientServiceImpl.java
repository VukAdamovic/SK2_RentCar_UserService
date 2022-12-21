package com.example.SK_Project2.UserService.service.impl;

import com.example.SK_Project2.UserService.domain.Role;
import com.example.SK_Project2.UserService.domain.User;
import com.example.SK_Project2.UserService.domain.UserStatus;
import com.example.SK_Project2.UserService.dto.notifications.ActivateEmailDto;
import com.example.SK_Project2.UserService.dto.notifications.ChangedPasswordDto;
import com.example.SK_Project2.UserService.dto.rental.DiscountDto;
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
    private String activateEmailDestination;
    private String changedPasswordDestination;


    public ClientServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserStatusRepository userStatusRepository,
                             ClientMapper clientMapper, JmsTemplate jmsTemplate, MessageHelper messageHelper,
                             @Value("${destination.activateEmail}")String activateEmailDestination,
                             @Value("${destination.changedPassword}")String changedPasswordDestination) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userStatusRepository = userStatusRepository;
        this.clientMapper = clientMapper;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.activateEmailDestination = activateEmailDestination;
        this.changedPasswordDestination = changedPasswordDestination;
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

        ActivateEmailDto activateEmailDto = new ActivateEmailDto();

        activateEmailDto.setFirstName(client.getFirstName());
        activateEmailDto.setLastName(client.getLastName());
        activateEmailDto.setEmail(client.getEmail());
        activateEmailDto.setLink(client.getActivatedEmail());

        jmsTemplate.convertAndSend(activateEmailDestination,messageHelper.createTextMessage(activateEmailDto));
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

        String oldPassword = "";
        Boolean check = false;
        if(!(user.getPassword().equals(clientDto.getPassword()))){
            oldPassword = user.getPassword();
            check = true;
        }
        user.setId(clientDto.getId());
        user.setUsername(clientDto.getUsername());
        user.setPassword(clientDto.getPassword());
        user.setEmail(clientDto.getEmail());
        user.setPhone(clientDto.getPhone());
        user.setDayOfBirth(clientDto.getDayOfBirth());
        user.setFirstName(clientDto.getFirstName());
        user.setLastName(clientDto.getLastName());
        user.setPassport(clientDto.getPassport());

        userRepository.save(user);

        if(check) {
            ChangedPasswordDto changedPasswordDto = new ChangedPasswordDto();
            changedPasswordDto.setOldPassword(oldPassword);
            changedPasswordDto.setNewPassword(clientDto.getPassword());
            changedPasswordDto.setEmail(clientDto.getEmail());
            jmsTemplate.convertAndSend(changedPasswordDestination, messageHelper.createTextMessage(changedPasswordDto));
        }


        return clientMapper.userToClientDto(user);
    }

    @Override // ovo zove onaj lisener a ne controller da znas
    public void incrementRentCar(Long id, Integer days) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d does not exists.", id)));

        //Set rentCarTotalDuration
        user.setRentCarTotalDuration(user.getRentCarTotalDuration() + days);

        //Set new rank
        List<UserStatus> userStatusList = userStatusRepository.findAll();
        String rank = userStatusList.stream()
                .filter(userStatus -> userStatus.getMaxTotalNumberOfRentCar() >= user.getRentCarTotalDuration()
                        && userStatus.getMinTotalNumberOfRentCar() <= user.getRentCarTotalDuration())
                .findAny()
                .get()
                .getName();

        user.setRank(rank);

        userRepository.save(user);
    }

    @Override
    public void decrementRentCar(Long id, Integer days) { // ovo zove onaj lisener a ne controller da znas
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d does not exists.", id)));

        //Set rentCarTotalDuration
        user.setRentCarTotalDuration(user.getRentCarTotalDuration() - days);

        //Set new rank
        List<UserStatus> userStatusList = userStatusRepository.findAll();
        String rank = userStatusList.stream()
                .filter(userStatus -> userStatus.getMaxTotalNumberOfRentCar() >= user.getRentCarTotalDuration()
                        && userStatus.getMinTotalNumberOfRentCar() <= user.getRentCarTotalDuration())
                .findAny()
                .get()
                .getName();

        user.setRank(rank);
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

    @Override
    public Boolean verificationEmail(String link) {
        List<String> find = new ArrayList<>();
        userRepository.findAll()
                .forEach(user -> {
                            if (user.getActivatedEmail().equals(link)) {
                                user.setActivatedEmail("Activated");
                                find.add("1");
                            }
                        }
                );

        if(find.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
}



