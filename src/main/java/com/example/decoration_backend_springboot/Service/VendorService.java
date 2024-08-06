package com.example.decoration_backend_springboot.Service;
import com.example.decoration_backend_springboot.Model.Vendor;
import com.example.decoration_backend_springboot.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    @Autowired
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
}
