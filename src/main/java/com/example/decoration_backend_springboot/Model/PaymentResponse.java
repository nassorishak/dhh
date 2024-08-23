package com.example.decoration_backend_springboot.Model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;
@Data
public class PaymentResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;
    private Double amount;
    private String paymentMethod;
    private String status;
    private Date PaymentDate;

    private  String controlNumber;

    public PaymentResponse(int paymentId, Double amount, String paymentMethod, String status, Date paymentDate, String controlNumber) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        PaymentDate = paymentDate;
        this.controlNumber = controlNumber;
    }

    public PaymentResponse(Payment payment) {
    }

    public PaymentResponse(String receipt) {

    }

    public PaymentResponse(int paymentId, String controlNumber, double amount, String paymentMethod, String status, Date paymentDate) {
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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
}
