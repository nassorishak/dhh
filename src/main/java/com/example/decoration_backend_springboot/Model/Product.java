package com.example.decoration_backend_springboot.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int productId;

//        private String vendorId;

        private String productName;

        private String productDescription;

        private Double price;

        @Lob
        @Column(length = 1000000)
        private byte[] image;

        private String category;



}