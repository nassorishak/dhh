package com.example.decoration_backend_springboot.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

@Entity
@Data
public class Payment {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int paymentId;
        private double amount;
        private String paymentMethod;
        private String status;
        private Date PaymentDate;
        private  String controlNumber;

        @PrePersist
        public  void generateControlNumber(){
                if (controlNumber==null){
                        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                        String randomPart = String.format("%03d",new Random().nextInt(1000));
                        controlNumber = "IS-HAK-"+datePart+randomPart;

                }
        }

        @OneToOne
        @JoinColumn(name="orderId")
        private Order order;

        public void setOrderId(int orderId) {

        }

        public int getPaymentId() {
                return paymentId;
        }

        public void setPaymentId(int paymentId) {
                this.paymentId = paymentId;
        }

        public double getAmount() {
                return amount;
        }

        public void setAmount(double amount) {
                this.amount = amount;
        }

        public String getPaymentMethod() {
                return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
                this.paymentMethod = paymentMethod;
        }

        public String getStatus() {
                return status;
        }

        public void setStatus(String status) {
                this.status = status;
        }

        public Date getPaymentDate() {
                return PaymentDate;
        }

        public void setPaymentDate(Date paymentDate) {
                PaymentDate = paymentDate;
        }

        public String getControlNumber() {
                return controlNumber;
        }

        public void setControlNumber(String controlNumber) {
                this.controlNumber = controlNumber;
        }

        public Order getOrder() {
                return order;
        }

        public void setOrder(Order order) {
                this.order = order;
        }

        public void setPhoneNumber(String phoneNumber) {
        }

        public void setTransactionId(String transactionId) {
        }

        public String getTransactionId() {
                return toString();
        }


        // getters and setters
}