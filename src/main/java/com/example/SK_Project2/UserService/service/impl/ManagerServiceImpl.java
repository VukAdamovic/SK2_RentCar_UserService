package com.example.SK_Project2.UserService.service.impl;

import com.example.SK_Project2.UserService.domain.Role;
import com.example.SK_Project2.UserService.domain.User;
import com.example.SK_Project2.UserService.dto.notifications.ActivateEmailDto;
import com.example.SK_Project2.UserService.dto.notifications.ChangedPasswordDto;
import com.example.SK_Project2.UserService.dto.user.ManagerCreateDto;
import com.example.SK_Project2.UserService.dto.user.ManagerDto;
import com.example.SK_Project2.UserService.exception.NotFoundException;
import com.example.SK_Project2.UserService.mapper.ManagerMapper;
import com.example.SK_Project2.UserService.messageHelper.MessageHelper;
import com.example.SK_Project2.UserService.repository.RoleRepository;
import com.example.SK_Project2.UserService.repository.UserRepository;
import com.example.SK_Project2.UserService.service.ManagerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ManagerMapper managerMapper;
    private JmsTemplate jmsTemplate;
    private MessageHelper messageHelper;
    private String activateEmailDestination;
    private String changedPasswordDestination;


    public ManagerServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                              ManagerMapper managerMapper, JmsTemplate jmsTemplate, MessageHelper messageHelper,
                              @Value("${destination.activateEmail}")String activateEmailDestination,
                              @Value("${destination.changedPassword}")String changedPasswordDestination) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.managerMapper = managerMapper;
        this.jmsTemplate = jmsTemplate;
        this.messageHelper = messageHelper;
        this.activateEmailDestination = activateEmailDestination;
        this.changedPasswordDestination = changedPasswordDestination;
    }

    @Override
    public List<ManagerDto> findAll() {
        List<ManagerDto> managers = new ArrayList<>();
        userRepository.findAll()
                .forEach( user -> {
                            if (user.getRole().getName().equals("ROLE_MANAGER"))
                                managers.add(managerMapper.userToManagerDto(user));
                        }
                );
        return  managers;
    }

    @Override
    public ManagerDto findById(Long id) {
        return userRepository.findById(id)
                .map(managerMapper::userToManagerDto)
                .orElseThrow(()-> new NotFoundException(String.format("Manager with id: %d does not exists.", id)));
    }

    @Override
    public ManagerDto add(ManagerCreateDto managerCreateDto) {
        Role role = roleRepository.findRoleByName("ROLE_MANAGER")
                .orElseThrow(() -> new NotFoundException("Role with name: ROLE_MANAGER not found."));
        User manager = managerMapper.managerCreateDtoToUser(managerCreateDto);
        manager.setRole(role);
        userRepository.save(manager);

        ActivateEmailDto activateEmailDto = new ActivateEmailDto();

        activateEmailDto.setFirstName(manager.getFirstName());
        activateEmailDto.setLastName(manager.getLastName());
        activateEmailDto.setEmail(manager.getEmail());
        activateEmailDto.setLink(manager.getActivatedEmail());

        jmsTemplate.convertAndSend(activateEmailDestination,messageHelper.createTextMessage(activateEmailDto));
        return managerMapper.userToManagerDto(manager);
    }

    @Override
    public Boolean delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Client with id: %d does not exists.", id)));

        userRepository.delete(user);
        return true;
    }

    @Override
    public ManagerDto update(ManagerDto managerDto) {
        User user = userRepository.findById(managerDto.getId())
                .orElseThrow(() -> new NotFoundException(String.format("User with id: %d does not exists.", managerDto.getId())));

        String oldPassword = "";
        Boolean check = false;
        if(!(user.getPassword().equals(managerDto.getPassword()))){
            oldPassword = user.getPassword();
            check = true;
        }

        user.setId(managerDto.getId());
        user.setUsername(managerDto.getUsername());
        user.setPassword(managerDto.getPassword());
        user.setEmail(managerDto.getEmail());
        user.setPhone(managerDto.getPhone());
        user.setDayOfBirth(managerDto.getDayOfBirth());
        user.setFirstName(managerDto.getFirstName());
        user.setLastName(managerDto.getLastName());
        user.setCompanyName(managerDto.getCompanyName());
        user.setEmploymentDay(managerDto.getEmploymentDay());

        userRepository.save(user);

        if(check) {
            ChangedPasswordDto changedPasswordDto = new ChangedPasswordDto();
            changedPasswordDto.setOldPassword(oldPassword);
            changedPasswordDto.setNewPassword(managerDto.getPassword());
            changedPasswordDto.setEmail(managerDto.getEmail());
            jmsTemplate.convertAndSend(changedPasswordDestination, messageHelper.createTextMessage(changedPasswordDto));
        }

        return managerMapper.userToManagerDto(user);
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
