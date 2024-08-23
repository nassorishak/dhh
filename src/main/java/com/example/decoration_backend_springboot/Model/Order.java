package com.example.decoration_backend_springboot.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "orders")
@Data
public class Order {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int orderId;

        @Temporal(TemporalType.DATE)
        private Date date;

        private String status;
        private  String controlNumber;
        private  String quantity;

        private  String orderType;
        private String size;
        @ManyToOne
        @JoinColumn(name="customerId")
        private Customer customer;

        @ManyToOne
        @JoinColumn(name = "productId")
        private Product product;


        public String getControlNumber() {
                return controlNumber;
        }

        public void setControlNumber(String controlNumber) {
                this.controlNumber = controlNumber;
        }

        public String getPaymentStatus() {
                return null;
        }
}