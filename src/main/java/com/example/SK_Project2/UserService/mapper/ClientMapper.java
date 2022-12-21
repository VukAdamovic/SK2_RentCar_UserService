package com.example.SK_Project2.UserService.mapper;

import com.example.SK_Project2.UserService.domain.User;
import com.example.SK_Project2.UserService.domain.UserStatus;
import com.example.SK_Project2.UserService.dto.user.ClientCreateDto;
import com.example.SK_Project2.UserService.dto.user.ClientDto;
import com.example.SK_Project2.UserService.repository.RoleRepository;
import com.example.SK_Project2.UserService.repository.UserStatusRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ClientMapper {
    private RoleRepository roleRepository;
    private UserStatusRepository userStatusRepository;


    public ClientMapper(RoleRepository roleRepository, UserStatusRepository userStatusRepository) {
        this.roleRepository = roleRepository;
        this.userStatusRepository = userStatusRepository;
    }

    public ClientDto userToClientDto(User user) {
        ClientDto clientDto = new ClientDto();

        clientDto.setId(user.getId());
        clientDto.setUsername(user.getUsername());
        clientDto.setPassword(user.getPassword()); //dodao
        clientDto.setEmail(user.getEmail());
        clientDto.setPhone(user.getPhone());
        clientDto.setDayOfBirth(user.getDayOfBirth());
        clientDto.setFirstName(user.getFirstName());
        clientDto.setLastName(user.getLastName());
        clientDto.setPassport(user.getPassport());

        return clientDto;
    }

    public User clientCreateDtoToUser(ClientCreateDto clientCreateDto) {
        User user = new User();

        user.setUsername(clientCreateDto.getUsername());
        user.setPassword(clientCreateDto.getPassword());
        user.setEmail(clientCreateDto.getEmail());
        user.setPhone(clientCreateDto.getPhone());
        user.setDayOfBirth(clientCreateDto.getDayOfBirth());
        user.setFirstName(clientCreateDto.getFirstName());
        user.setLastName(clientCreateDto.getLastName());
        user.setPassport(clientCreateDto.getPassport());
        user.setRentCarTotalDuration(0);
        user.setCompanyName(null);
        user.setEmploymentDay(null);
        user.setForbidden(false);
        user.setRole(roleRepository.findRoleByName("ROLE_CLIENT").get());

        //get Rank and set Rank
        List<UserStatus> userStatusList = userStatusRepository.findAll();
        String rank = userStatusList.stream()
                .filter(userStatus -> userStatus.getMaxTotalNumberOfRentCar() >= user.getRentCarTotalDuration()
                        && userStatus.getMinTotalNumberOfRentCar() <= user.getRentCarTotalDuration())
                .findAny()
                .get()
                .getName();

        user.setRank(rank);


        //Random string - link
        UUID uuidObj = UUID.randomUUID();
        String link = uuidObj.toString().replaceAll("_","");
        user.setActivatedEmail(link);

        return user;
    }
}
