package com.example.decoration_backend_springboot.Service;
import com.example.decoration_backend_springboot.Model.Product;
import com.example.decoration_backend_springboot.Model.Vendor;
import com.example.decoration_backend_springboot.Repository.ProductRepository;
import com.example.decoration_backend_springboot.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorService {
    @Autowired
    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public Optional<Vendor> findById(int vendorId) {
        return vendorRepository.findById(vendorId);
    }

    public List<Vendor> findAll() {
        return vendorRepository.findAll();
    }

    public Vendor save(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public void deleteById(int vendorId) {
        vendorRepository.deleteById(vendorId);
    }

    public Long countAllOrders() {
        return vendorRepository.count();
    }

//
//    public boolean blockVendor(int vendorId) {
//        Optional<Vendor> vendor = vendorRepository.findById(vendorId);
//        if (vendor.isPresent()) {
//            vendor.get().setBlocked(true); // Assuming you have a method to set blocked status
//            vendorRepository.save(vendor.get());
//            return true;
//        }
//        return false;
//    }
//
//    public boolean unblockVendor(int vendorId) {
//        Optional<Vendor> vendor = vendorRepository.findById(vendorId);
//        if (vendor.isPresent()) {
//            vendor.get().setBlocked(false); // Assuming you have a method to set blocked status
//            vendorRepository.save(vendor.get());
//            return true;
//        }
//        return false;
//    }
//public boolean isVendorBlocked(int vendorId) {
//    Optional<Vendor> vendor = vendorRepository.findById(vendorId);
//    return vendor.map(Vendor::isBlocked).orElse(false); // Assuming isBlocked returns true if blocked
//}
//
//    public boolean blockVendor(int vendorId) {
//        Optional<Vendor> vendor = vendorRepository.findById(vendorId);
//        if (vendor.isPresent()) {
//            vendor.get().setBlocked(true); // Assuming you have a method to set blocked status
//            vendorRepository.save(vendor.get());
//            return true;
//        }
//        return false;
//    }
//
//    public boolean unblockVendor(int vendorId) {
//        Optional<Vendor> vendor = vendorRepository.findById(vendorId);
//        if (vendor.isPresent()) {
//            vendor.get().setBlocked(false); // Assuming you have a method to set blocked status
//            vendorRepository.save(vendor.get());
//            return true;
//        }
//        return false;
//    }

    public boolean blockVendor(int vendorId) {
        Optional<Vendor> optionalVendor = findById(vendorId);
        if (optionalVendor.isPresent()) {
            Vendor vendor = optionalVendor.get();
            vendor.setIsBlocked(true);
            save(vendor);
            return true;
        }
        return false;
    }

    public boolean unblockVendor(int vendorId) {
        Optional<Vendor> optionalVendor = findById(vendorId);
        if (optionalVendor.isPresent()) {
            Vendor vendor = optionalVendor.get();
            vendor.setIsBlocked(false);
            save(vendor);
            return true;
        }
        return false;
    }
}
