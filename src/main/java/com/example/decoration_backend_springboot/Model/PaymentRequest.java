package com.example.decoration_backend_springboot.Model;

import lombok.Data;

//package com.example.decoration_backend_springboot.Model;
//
//import lombok.Data;
//
//@Data
//public class PaymentRequest {
//    private double amount;
//
//
//    public double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(double amount) {
//        this.amount = amount;
//    }
//
//    public String getPaymentMethod() {
//        return getPaymentMethod();
//    }
//}
@Data
public class PaymentRequest {
    private double amount;
    private String paymentMethod;

    private int orderId;


    // Getters and Setters
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



    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}

