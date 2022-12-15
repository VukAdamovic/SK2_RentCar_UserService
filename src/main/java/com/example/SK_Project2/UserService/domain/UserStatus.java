package com.example.SK_Project2.UserService.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer minTotalNumberOfRentCar;
    private Integer maxTotalNumberOfRentCar;
    private Integer discount;

    public UserStatus() {
    }

    public UserStatus(String name, Integer minTotalNumberOfRentCar, Integer maxTotalNumberOfRentCar, Integer discount) {
        this.name = name;
        this.minTotalNumberOfRentCar = minTotalNumberOfRentCar;
        this.maxTotalNumberOfRentCar = maxTotalNumberOfRentCar;
        this.discount = discount;
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
