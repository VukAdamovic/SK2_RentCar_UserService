package com.example.SK_Project2.UserService.mapper;

import com.example.SK_Project2.UserService.domain.UserStatus;
import com.example.SK_Project2.UserService.dto.RankCreateDto;
import com.example.SK_Project2.UserService.dto.RankDto;
import org.springframework.stereotype.Component;

@Component
public class RankMapper {

//    private UserStatusRepository userRepository;  ako ga stavis dodaj ga u konstruktor

    public RankMapper() {}

    public RankDto userStatusToRankDto(UserStatus userStatus) {
        RankDto rankDto = new RankDto();

        rankDto.setId(userStatus.getId());
        rankDto.setName(userStatus.getName());
        rankDto.setMinTotalNumberOfRentCar(userStatus.getMinTotalNumberOfRentCar());
        rankDto.setMaxTotalNumberOfRentCar(userStatus.getMaxTotalNumberOfRentCar());
        rankDto.setDiscount(userStatus.getDiscount());

        return rankDto;
    }

    public UserStatus rankCreateDtoToUserStatus(RankCreateDto rankCreateDto) {
        UserStatus userStatus = new UserStatus();

        userStatus.setName(rankCreateDto.getName());
        userStatus.setMinTotalNumberOfRentCar(rankCreateDto.getMinTotalNumberOfRentCar());
        userStatus.setMaxTotalNumberOfRentCar(rankCreateDto.getMaxTotalNumberOfRentCar());
        userStatus.setDiscount(rankCreateDto.getDiscount());

        return userStatus;
    }
}
