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

    public boolean blockVendor(int vendorId) {
        Optional<Vendor> vendorOptional = vendorRepository.findById(vendorId);
        if (vendorOptional.isPresent()) {
            Vendor vendor = vendorOptional.get();
            vendor.setBlocked(true);
            vendorRepository.save(vendor);
            return true;
        }
        return false;
    }

    public boolean unblockVendor(int vendorId) {
        Optional<Vendor> vendorOptional = vendorRepository.findById(vendorId);
        if (vendorOptional.isPresent()) {
            Vendor vendor = vendorOptional.get();
            vendor.setBlocked(false);
            vendorRepository.save(vendor);
            return true;
        }
        return false;
    }

}
