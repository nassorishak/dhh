package com.example.decoration_backend_springboot.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "orders")
@Data
public class Order {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int orderId;

        private String name;

        private Double totalAmount;

        @Temporal(TemporalType.DATE)
        private Date date;

        private String status;

        private  String quantity;

        @ManyToOne
        @JoinColumn(name="customerID")
        private Customer customer;

        @ManyToOne
        @JoinColumn(name="payment_id")
        private Payment payment;
}