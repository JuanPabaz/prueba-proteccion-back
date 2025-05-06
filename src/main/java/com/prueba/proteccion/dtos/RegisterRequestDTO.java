package com.prueba.proteccion.dtos;

import com.prueba.proteccion.enums.Role;

public class RegisterRequestDTO {

    private String fullName;
    private String idNumber;
    private String address;
    private String username;
    private String password;
    private Role role;

    public RegisterRequestDTO() {
    }

    public RegisterRequestDTO(String fullName, String idNumber, String address, String username, String password, Role role) {
        this.fullName = fullName;
        this.idNumber = idNumber;
        this.address = address;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
