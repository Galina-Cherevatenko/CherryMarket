package com.example.cherrymarket1.dto;


import javax.validation.constraints.Email;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


public class PersonDTO {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String name;

    @NotEmpty(message = "Address should not be empty")
    @Size(min = 2, max = 100, message = "Address should be between 2 and 100 characters")
    private String address;

    @NotEmpty(message = "Phone should not be empty")
    @Size(min = 10, max = 12, message = "Phone should be between 10 and 12 characters")
    private String phone;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    public PersonDTO() {
    }

    public PersonDTO(String name, String address, String phone, String email) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
