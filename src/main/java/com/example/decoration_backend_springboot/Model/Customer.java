package com.example.decoration_backend_springboot.Model;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Customer extends User{
    private  String custAddress;
    private String phone;
    private String name;

    public Customer(String custAddress, String phone, String name) {
        this.custAddress = custAddress;
        this.phone = phone;
        this.name = name;
    }

    public Customer(String email, String password, String customer, String name, String custAddress, String phone) {

    }

    public Customer() {

    }

    public String getName() {
        return name;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }
}