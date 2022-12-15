package com.example.SK_Project2.UserService.mapper;

import com.example.SK_Project2.UserService.domain.User;
import com.example.SK_Project2.UserService.dto.user.AdminCreateDto;
import com.example.SK_Project2.UserService.dto.user.AdminDto;
import com.example.SK_Project2.UserService.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {
    private RoleRepository roleRepository;

    public AdminMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public AdminDto userToAdminDto(User user) {
        AdminDto adminDto = new AdminDto();

        adminDto.setId(user.getId());
        adminDto.setUsername(user.getUsername());
        adminDto.setEmail(user.getEmail());
        adminDto.setPhone(user.getPhone());
        adminDto.setDayOfBirth(user.getDayOfBirth());
        adminDto.setFirstName(user.getFirstName());
        adminDto.setLastName(user.getLastName());
        adminDto.setForbidden(user.isForbidden());

        return adminDto;
    }

    public User adminCreateDtoToUser(AdminCreateDto adminCreateDto) {
        User user = new User();

        user.setUsername(adminCreateDto.getUsername());
        user.setPassword(adminCreateDto.getPassword());
        user.setEmail(adminCreateDto.getEmail());
        user.setPhone(adminCreateDto.getPhone());
        user.setDayOfBirth(adminCreateDto.getDayOfBirth());
        user.setFirstName(adminCreateDto.getFirstName());
        user.setLastName(adminCreateDto.getLastName());
        user.setPassport(null);
        user.setRentCarTotalDuration(null);
        user.setCompanyName(null);
        user.setEmploymentDay(null);

        user.setForbidden(false);
        user.setRole(roleRepository.findRoleByName("ROLE_ADMIN").get());

        return user;
    }
}
