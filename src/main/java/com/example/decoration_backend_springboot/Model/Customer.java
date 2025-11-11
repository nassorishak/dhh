package com.example.decoration_backend_springboot.Model;
import com.example.decoration_backend_springboot.Model.Enum.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
@Entity
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User {

    @Column(name = "cust_address")
    private String custAddress;

    private String phone;

    // Default constructor
    public Customer() {
        super();
    }

    // Parameterized constructor
    public Customer(String email, String password, String custAddress, String phone) {
        super();
        this.setEmail(email);

        this.setPassword(password);
        this.setRole(Role.CUSTOMER);
        this.custAddress = custAddress;
        this.phone = phone;
    }

    // Getters and setters
    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}