package com.example.SK_Project2.UserService.domain;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String phone;
    private String dayOfBirth;
    private String firstName;
    private String lastName;
    private String passport;
    private Integer rentCarTotalDuration;
    private String companyName;
    private String employmentDay;
    private boolean forbidden;
    private String rank;

    @ManyToOne
    private Role role;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public Integer getRentCarTotalDuration() {
        return rentCarTotalDuration;
    }

    public void setRentCarTotalDuration(Integer rentCarTotalDuration) {
        this.rentCarTotalDuration = rentCarTotalDuration;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEmploymentDay() {
        return employmentDay;
    }

    public void setEmploymentDay(String employmentDay) {
        this.employmentDay = employmentDay;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isForbidden() {
        return forbidden;
    }

    public void setForbidden(boolean forbidden) {
        this.forbidden = forbidden;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
