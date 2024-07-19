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

        private String vendorId;

        private String productName;

        private String productDescription;

        private Double price;

        private String image;

        private String category;

        @ManyToOne
        @JoinColumn(name = "order_id")
        private Order order;

}