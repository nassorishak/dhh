package com.example.decoration_backend_springboot.Repository;

import com.example.decoration_backend_springboot.Model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    // Change this method if `vendorId` is not defined in the `Subscription` entity
    Subscription findByVendorId(int vendorId);

    List<Subscription> findByVendor_User_UserId(int userId);

}