package com.example.decoration_backend_springboot.Repository;

import com.example.decoration_backend_springboot.Model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    // Change this method if `vendorId` is not defined in the `Subscription` entity
    Subscription findByVendorId(int vendorId);
}