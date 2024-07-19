package com.example.decoration_backend_springboot.API;

import com.example.decoration_backend_springboot.Model.Customer;
import com.example.decoration_backend_springboot.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("api/customer")
public class CustomerAPI {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/get/customer")
    public ResponseEntity<?> getCustomers() {
        try {
            List<Customer> customerList = customerService.findAll();
            if (customerList.isEmpty()) {
                return new ResponseEntity<>("No customers found", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(customerList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve customers", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add/customer")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        try {
            Customer savedCustomer = customerService.save(customer);
            return new ResponseEntity<>("Customer added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add customer", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{customer_id}")
    public ResponseEntity<?> updateCustomer(@PathVariable int customer_id, @RequestBody Customer customer) {
        try {
            Optional<Customer> existingCustomer = customerService.findById(customer_id);
            if (existingCustomer.isPresent()) {
                customerService.save(customer);
                return new ResponseEntity<>("Customer updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update customer", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{customer_id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable int customer_id) {
        try {
            customerService.deleteById(customer_id);
            return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete customer", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getById/{customer_id}")
    public ResponseEntity<?> getCustomerById(@PathVariable int customer_id) {
        try {
            Optional<Customer> optionalCustomer = customerService.findById(customer_id);
            if (optionalCustomer.isPresent()) {
                return new ResponseEntity<>(optionalCustomer.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve customer", HttpStatus.BAD_REQUEST);
        }
    }
}
