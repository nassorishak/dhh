package com.example.decoration_backend_springboot.API;

import com.example.decoration_backend_springboot.Model.Subscription;
import com.example.decoration_backend_springboot.Model.Vendor;
import com.example.decoration_backend_springboot.Repository.SubscriptionRepository;
import com.example.decoration_backend_springboot.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;

//package com.example.decoration_backend_springboot.API;
//
//import com.example.decoration_backend_springboot.Model.Subscription;
//import com.example.decoration_backend_springboot.Service.SubscriptionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@CrossOrigin("http://localhost:3000")
//@RequestMapping("/subscriptions")
//public class SubscriptionAPI {
//    @Autowired
//    private SubscriptionService subscriptionService;
//
//    @PostMapping("/create")
//    public Subscription createSubscription(@RequestBody Subscription subscription) {
//        return subscriptionService.createSubscription(subscription);
//    }
//
//    @GetMapping("/calculate")
//    public String calculateFee(@RequestParam int vendorId) {
//        Subscription subscription = subscriptionService.findByVendorId(vendorId);
//        return subscriptionService.calculateFee(subscription);
//    }
//
//    @PostMapping("/block")
//    public void blockVendor(@RequestBody Subscription subscription) {
//        subscriptionService.blockVendor(subscription);
//    }
//}
@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/subscriptions")
public class SubscriptionAPI {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @PostMapping("/calculate-amount")
    public ResponseEntity<String> calculateAmount(@RequestBody Subscription subscription) {
        Vendor vendor = vendorRepository.findById(subscription.getVendorId()).orElse(null);
        if (vendor == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendor not found");
        }

        double dailyRate;
        if (subscription.getServiceType().equals("product")) {
            dailyRate = 5000.0;
        } else if (subscription.getServiceType().equals("service")) {
            dailyRate = 7000.0;
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid service type");
        }

        // Calculate the number of days between the start and end dates
        long days = ChronoUnit.DAYS.between(subscription.getStartDate().toLocalDate(), subscription.getEndDate().toLocalDate());

        // Calculate the total amount
        double totalAmount = days * dailyRate;

        // Update the subscription with the calculated amount
        subscription.setAmount(String.valueOf(totalAmount));

        // Save the subscription
        subscriptionRepository.save(subscription);

        return ResponseEntity.ok("Total amount: " + totalAmount);
    }

}