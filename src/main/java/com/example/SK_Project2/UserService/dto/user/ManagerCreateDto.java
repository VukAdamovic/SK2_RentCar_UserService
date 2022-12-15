package com.example.SK_Project2.UserService.dto.user;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class ManagerCreateDto {
    @NotBlank
    private String username;
    @Length(min = 8, max = 20)
    private String password;
    @NotBlank
    private String email;
    @NotBlank
    private String phone;
    @NotBlank  // da li moze @DateTimeFormat  iako mi je dayOfBirth tipa string
    private String dayOfBirth;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String companyName;
    @NotBlank
    private String employmentDay;

    public ManagerCreateDto() {
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
}
