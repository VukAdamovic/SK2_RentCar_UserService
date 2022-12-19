package com.example.SK_Project2.UserService.dto.userStatus;

import javax.validation.constraints.NotBlank;

public class RankCreateDto {

    @NotBlank
    private String name;
    @NotBlank
    private Integer minTotalNumberOfRentCar;
    @NotBlank
    private Integer maxTotalNumberOfRentCar;
    @NotBlank
    private Integer discount;

    public RankCreateDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinTotalNumberOfRentCar() {
        return minTotalNumberOfRentCar;
    }

    public void setMinTotalNumberOfRentCar(Integer minTotalNumberOfRentCar) {
        this.minTotalNumberOfRentCar = minTotalNumberOfRentCar;
    }

    public Integer getMaxTotalNumberOfRentCar() {
        return maxTotalNumberOfRentCar;
    }

    public void setMaxTotalNumberOfRentCar(Integer maxTotalNumberOfRentCar) {
        this.maxTotalNumberOfRentCar = maxTotalNumberOfRentCar;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
