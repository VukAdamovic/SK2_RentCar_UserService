package com.example.SK_Project2.UserService.dto;

public class RankDto {

    private Long id;
    private String name;
    private Integer minTotalNumberOfRentCar;
    private Integer maxTotalNumberOfRentCar;
    private Integer discount;


    public RankDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
