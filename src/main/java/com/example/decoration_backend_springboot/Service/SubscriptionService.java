package com.example.decoration_backend_springboot.Service;

import com.example.decoration_backend_springboot.Model.Subscription;
import com.example.decoration_backend_springboot.Repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    public Subscription createSubscription(Subscription subscription) {
        // Validate the dates
        if (subscription.getStartDate() == null || subscription.getEndDate() == null) {
            throw new IllegalArgumentException("Start date and end date must not be null.");
        }
        if (subscription.getStartDate().isAfter(subscription.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }

        // Print the dates for debugging
        System.out.println("Start Date: " + subscription.getStartDate());
        System.out.println("End Date: " + subscription.getEndDate());

        // Save to the repository
        return subscriptionRepository.save(subscription);
    }

    public String calculateFee(Subscription subscription) {
        long duration = subscription.getEndDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
                - subscription.getStartDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long days = duration / (1000 * 60 * 60 * 24); // Convert to days

        if (subscription.getSubscriptionType().equalsIgnoreCase("Monthly")) {
            long months = days / 30; // Average month count
            return "Total Fee: $" + (months * 10); // Assuming $10/month
        } else if (subscription.getSubscriptionType().equalsIgnoreCase("Yearly")) {
            long years = days / 365; // Average year count
            return "Total Fee: $" + (years * 100); // Assuming $100/year
        }
        return "Invalid subscription type.";
    }

    public void blockVendor(Subscription subscription) {
        if (LocalDateTime.now().isAfter(subscription.getEndDate())) {
            subscription.setBlocked(true);
            subscriptionRepository.save(subscription);
        }
    }




    public Subscription findByVendorId(int vendorId) {
        return  subscriptionRepository.findByVendorId(vendorId);
    }
}