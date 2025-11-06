package com.example.decoration_backend_springboot.Model;

public class BalanceCheckRequest {
    private String phoneNumber;
    private String network;

    public BalanceCheckRequest() {}

    public BalanceCheckRequest(String phoneNumber, String network) {
        this.phoneNumber = phoneNumber;
        this.network = network;
    }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getNetwork() { return network; }
    public void setNetwork(String network) { this.network = network; }
}