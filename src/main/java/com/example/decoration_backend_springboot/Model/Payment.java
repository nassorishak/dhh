package com.example.decoration_backend_springboot.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Payment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int paymentId;
        private Double amount;
        private String paymentMethod;
        private String status;
        private int orderId;


        // getters and setters
}