package com.example.decoration_backend_springboot.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int subId;
    private String subscriptionType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String serviceType;
    private String serviceName;
    private String amount;
    private String company;

    // Assuming you have a vendorId field
    private int vendorId; // Add this if it matches your repository query

    // Getters and setters
    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }
    private boolean blocked;

    public Subscription() {
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isBlocked() {
        return blocked;
    }
    @OneToOne
    @JoinColumn(name="subId")
    private Vendor vendor;



    // The @Data annotation provides other getters, setters, equals, hashCode, and toString methods
}
