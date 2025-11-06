package com.example.decoration_backend_springboot.API;//package com.example.decoration_backend_springboot.API;
//
//import com.example.decoration_backend_springboot.Model.Customer;
//import com.example.decoration_backend_springboot.Service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@CrossOrigin("*")
//@RequestMapping("api/customer")
//public class CustomerAPI {
//
//    @Autowired
//    private CustomerService customerService;
//
//    @GetMapping("/get/customer")
//    public ResponseEntity<?> getCustomers() {
//        try {
//            List<Customer> customerList = customerService.findAll();
//            if (customerList.isEmpty()) {
//                return new ResponseEntity<>("No customers found", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(customerList, HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to retrieve customers", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PostMapping("/add/customer")
//    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
//        try {
//            Customer savedCustomer = customerService.save(customer);
//            return new ResponseEntity<>("Customer added successfully", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to add customer", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PutMapping("/update/{customer_id}")
//    public ResponseEntity<?> updateCustomer(@PathVariable int customer_id, @RequestBody Customer customer) {
//        try {
//            Optional<Customer> existingCustomer = customerService.findById(customer_id);
//            if (existingCustomer.isPresent()) {
//                customerService.save(customer);
//                return new ResponseEntity<>("Customer updated successfully", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to update customer", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @DeleteMapping("/delete/{customer_id}")
//    public ResponseEntity<?> deleteCustomerById(@PathVariable int customer_id) {
//        try {
//            customerService.deleteById(customer_id);
//            return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to delete customer", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping("/getById/{customer_id}")
//    public ResponseEntity<?> getCustomerById(@PathVariable int customer_id) {
//        try {
//            Optional<Customer> optionalCustomer = customerService.findById(customer_id);
//            if (optionalCustomer.isPresent()) {
//                return new ResponseEntity<>(optionalCustomer.get(), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to retrieve customer", HttpStatus.BAD_REQUEST);
//        }
//    }
//}

import com.example.decoration_backend_springboot.Model.Customer;
import com.example.decoration_backend_springboot.Model.Enum.Role;
import com.example.decoration_backend_springboot.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("api/customer")
public class CustomerAPI {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add/customer")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        try {
            // Validate required fields
            if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
                return new ResponseEntity<>("Email is required", HttpStatus.BAD_REQUEST);
            }

            Customer savedCustomer = customerService.save(customer);
            return new ResponseEntity<>("Customer added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add customer: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{customer_id}")
    public ResponseEntity<?> updateCustomer(@PathVariable int customer_id, @RequestBody Customer customer) {
        try {
            Optional<Customer> existingCustomer = customerService.findById(customer_id);
            if (existingCustomer.isPresent()) {
                Customer existing = existingCustomer.get();

                // Update only the fields that are provided
                if (customer.getName() != null) existing.setName(customer.getName());
                if (customer.getCustAddress() != null) existing.setCustAddress(customer.getCustAddress());
                if (customer.getPhone() != null) existing.setPhone(customer.getPhone());
                if (customer.getEmail() != null) existing.setEmail(customer.getEmail());
                if (customer.getPassword() != null) existing.setPassword(customer.getPassword());
                if (customer.getRole() != null) existing.setRole(customer.getRole());

                customerService.save(existing);
                return new ResponseEntity<>("Customer updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update customer", HttpStatus.BAD_REQUEST);
        }
    }

    // Add a new endpoint to fix existing NULL records
    @PutMapping("/fix-null-fields/{customer_id}")
    public ResponseEntity<?> fixNullFields(@PathVariable int customer_id, @RequestBody Customer updates) {
        try {
            Optional<Customer> optionalCustomer = customerService.findById(customer_id);
            if (optionalCustomer.isPresent()) {
                Customer customer = optionalCustomer.get();

                // Fix NULL fields with default values
                if (customer.getName() == null) customer.setName(updates.getName() != null ? updates.getName() : "Unknown");
                if (customer.getRole() == null) customer.setRole(Role.valueOf("CUSTOMER"));
                if (customer.getCustAddress() == null) customer.setCustAddress(updates.getCustAddress() != null ? updates.getCustAddress() : "Not specified");
                if (customer.getPhone() == null) customer.setPhone(updates.getPhone() != null ? updates.getPhone() : "Not specified");

                customerService.save(customer);
                return new ResponseEntity<>("Customer fields fixed successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fix customer fields", HttpStatus.BAD_REQUEST);
        }
    }
}