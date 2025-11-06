//package com.example.decoration_backend_springboot.Model;
//
//import lombok.Data;
//
////package com.example.decoration_backend_springboot.Model;
////
////import lombok.Data;
////
////@Data
////public class PaymentRequest {
////    private double amount;
////
////
////    public double getAmount() {
////        return amount;
////    }
////
////    public void setAmount(double amount) {
////        this.amount = amount;
////    }
////
////    public String getPaymentMethod() {
////        return getPaymentMethod();
////    }
////}
//@Data
//public class PaymentRequest {
//    private double amount;
//    private String paymentMethod;
//
//    private int orderId;
//
//
//    // Getters and Setters
//    public double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(double amount) {
//        this.amount = amount;
//    }
//
//    public String getPaymentMethod() {
//        return paymentMethod;
//    }
//
//    public void setPaymentMethod(String paymentMethod) {
//        this.paymentMethod = paymentMethod;
//    }
//
//
//
//    public void setOrderId(int orderId) {
//        this.orderId = orderId;
//    }
//}
//
package com.example.decoration_backend_springboot.Model;

public class PaymentRequest {
    private String phoneNumber;
    private String pin;
    private double amount;
    private String network;
    private String controlNumber;

    public PaymentRequest() {}

    public PaymentRequest(String phoneNumber, String pin, double amount, String network, String controlNumber) {
        this.phoneNumber = phoneNumber;
        this.pin = pin;
        this.amount = amount;
        this.network = network;
        this.controlNumber = controlNumber;
    }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getNetwork() { return network; }
    public void setNetwork(String network) { this.network = network; }

    public String getControlNumber() { return controlNumber; }
    public void setControlNumber(String controlNumber) { this.controlNumber = controlNumber; }
}