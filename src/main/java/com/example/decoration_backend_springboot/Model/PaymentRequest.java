package com.example.decoration_backend_springboot.Model;

import lombok.Data;

@Data
public class PaymentRequest {
    private double amount;


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
