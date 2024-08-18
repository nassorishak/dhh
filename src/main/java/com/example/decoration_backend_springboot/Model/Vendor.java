package com.example.decoration_backend_springboot.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Vendor extends User{
    private String vendorType;
    private  String address;
    private String vendorCompany;




}