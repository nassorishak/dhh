package com.example.decoration_backend_springboot.API;
import com.example.decoration_backend_springboot.Model.Vendor;
import com.example.decoration_backend_springboot.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/vendor")
public class VendorAPI {

    @Autowired
    private final VendorService vendorService;

    public VendorAPI(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @GetMapping("/get/vendors")
    public ResponseEntity<?> getVendors() {
        try {
            List<Vendor> vendorList = vendorService.findAll();
            if (vendorList.isEmpty()) {
                return new ResponseEntity<>("No vendors found", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(vendorList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve vendors", HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/add/vendor")
    public ResponseEntity<?> createVendor(@RequestBody Vendor vendor) {
        try {
            vendorService.save(vendor);
            return new ResponseEntity<>("Vendor added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add vendor", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{vendorId}")
    public ResponseEntity<?> updateVendor(@PathVariable int vendorId, @RequestBody Vendor vendor) {
        try {
            Optional<Vendor> optionalVendor = vendorService.findById(vendorId);
            if (optionalVendor.isPresent()) {
                vendorService.save(vendor);
                return new ResponseEntity<>("Vendor updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Vendor not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update vendor", HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("/delete/{vendorId}")
    public ResponseEntity<?> deleteById(@PathVariable int vendorId) {
        try {
            vendorService.deleteById(vendorId);
            return new ResponseEntity<>("Vendor deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete vendor", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getById/{vendorId}")
    public ResponseEntity<?> getVendorById(@PathVariable int vendorId) {
        try {
            Optional<Vendor> optionalVendor = vendorService.findById(vendorId);
            if (optionalVendor.isPresent()) {
                return new ResponseEntity<>(optionalVendor.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Vendor not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve vendor", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countAllOrders() {
        Long count = vendorService.countAllOrders();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @PutMapping("/block/{vendorId}")
    public ResponseEntity<String> blockVendor(@PathVariable int vendorId) {
        boolean isBlocked = vendorService.blockVendor(vendorId);
        if (isBlocked) {
            return ResponseEntity.ok("Vendor blocked successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendor not found.");
        }
    }

    @PutMapping("/unblock/{vendorId}")
    public ResponseEntity<String> unblockVendor(@PathVariable int vendorId) {
        boolean isUnblocked = vendorService.unblockVendor(vendorId);
        if (isUnblocked) {
            return ResponseEntity.ok("Vendor unblocked successfully.");
        } else {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vendor not found.");
        }
    }




}
