package com.example.decoration_backend_springboot.Model;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Customer extends User{
    private  String custAddress;
    private String phoneNo;
}