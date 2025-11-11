//package com.example.decoration_backend_springboot.API;//package com.example.decoration_backend_springboot.API;
////
////import com.example.decoration_backend_springboot.Model.Customer;
////import com.example.decoration_backend_springboot.Service.CustomerService;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////
////import java.util.List;
////import java.util.Optional;
////
////@RestController
////@CrossOrigin("*")
////@RequestMapping("api/customer")
////public class CustomerAPI {
////
////    @Autowired
////    private CustomerService customerService;
////
////    @GetMapping("/get/customer")
////    public ResponseEntity<?> getCustomers() {
////        try {
////            List<Customer> customerList = customerService.findAll();
////            if (customerList.isEmpty()) {
////                return new ResponseEntity<>("No customers found", HttpStatus.OK);
////            } else {
////                return new ResponseEntity<>(customerList, HttpStatus.OK);
////            }
////        } catch (Exception e) {
////            return new ResponseEntity<>("Failed to retrieve customers", HttpStatus.BAD_REQUEST);
////        }
////    }
////
////    @PostMapping("/add/customer")
////    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
////        try {
////            Customer savedCustomer = customerService.save(customer);
////            return new ResponseEntity<>("Customer added successfully", HttpStatus.OK);
////        } catch (Exception e) {
////            return new ResponseEntity<>("Failed to add customer", HttpStatus.BAD_REQUEST);
////        }
////    }
////
////    @PutMapping("/update/{customer_id}")
////    public ResponseEntity<?> updateCustomer(@PathVariable int customer_id, @RequestBody Customer customer) {
////        try {
////            Optional<Customer> existingCustomer = customerService.findById(customer_id);
////            if (existingCustomer.isPresent()) {
////                customerService.save(customer);
////                return new ResponseEntity<>("Customer updated successfully", HttpStatus.OK);
////            } else {
////                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
////            }
////        } catch (Exception e) {
////            return new ResponseEntity<>("Failed to update customer", HttpStatus.BAD_REQUEST);
////        }
////    }
////
////    @DeleteMapping("/delete/{customer_id}")
////    public ResponseEntity<?> deleteCustomerById(@PathVariable int customer_id) {
////        try {
////            customerService.deleteById(customer_id);
////            return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
////        } catch (Exception e) {
////            return new ResponseEntity<>("Failed to delete customer", HttpStatus.BAD_REQUEST);
////        }
////    }
////
////    @GetMapping("/getById/{customer_id}")
////    public ResponseEntity<?> getCustomerById(@PathVariable int customer_id) {
////        try {
////            Optional<Customer> optionalCustomer = customerService.findById(customer_id);
////            if (optionalCustomer.isPresent()) {
////                return new ResponseEntity<>(optionalCustomer.get(), HttpStatus.OK);
////            } else {
////                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
////            }
////        } catch (Exception e) {
////            return new ResponseEntity<>("Failed to retrieve customer", HttpStatus.BAD_REQUEST);
////        }
////    }
////}
//
//import com.example.decoration_backend_springboot.Model.Customer;
//import com.example.decoration_backend_springboot.Model.Enum.Role;
//import com.example.decoration_backend_springboot.Service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
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
//    @PostMapping("/add/customer")
//    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
//        try {
//            // Validate required fields
//            if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
//                return new ResponseEntity<>("Email is required", HttpStatus.BAD_REQUEST);
//            }
//
//            Customer savedCustomer = customerService.save(customer);
//            return new ResponseEntity<>("Customer added successfully", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to add customer: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PutMapping("/update/{customer_id}")
//    public ResponseEntity<?> updateCustomer(@PathVariable int customer_id, @RequestBody Customer customer) {
//        try {
//            Optional<Customer> existingCustomer = customerService.findById(customer_id);
//            if (existingCustomer.isPresent()) {
//                Customer existing = existingCustomer.get();
//
//                // Update only the fields that are provided
//                if (customer.getName() != null) existing.setName(customer.getName());
//                if (customer.getCustAddress() != null) existing.setCustAddress(customer.getCustAddress());
//                if (customer.getPhone() != null) existing.setPhone(customer.getPhone());
//                if (customer.getEmail() != null) existing.setEmail(customer.getEmail());
//                if (customer.getPassword() != null) existing.setPassword(customer.getPassword());
//                if (customer.getRole() != null) existing.setRole(customer.getRole());
//
//                customerService.save(existing);
//                return new ResponseEntity<>("Customer updated successfully", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to update customer", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // Add a new endpoint to fix existing NULL records
//    @PutMapping("/fix-null-fields/{customer_id}")
//    public ResponseEntity<?> fixNullFields(@PathVariable int customer_id, @RequestBody Customer updates) {
//        try {
//            Optional<Customer> optionalCustomer = customerService.findById(customer_id);
//            if (optionalCustomer.isPresent()) {
//                Customer customer = optionalCustomer.get();
//
//                // Fix NULL fields with default values
//                if (customer.getName() == null) customer.setName(updates.getName() != null ? updates.getName() : "Unknown");
//                if (customer.getRole() == null) customer.setRole(Role.valueOf("CUSTOMER"));
//                if (customer.getCustAddress() == null) customer.setCustAddress(updates.getCustAddress() != null ? updates.getCustAddress() : "Not specified");
//                if (customer.getPhone() == null) customer.setPhone(updates.getPhone() != null ? updates.getPhone() : "Not specified");
//
//                customerService.save(customer);
//                return new ResponseEntity<>("Customer fields fixed successfully", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to fix customer fields", HttpStatus.BAD_REQUEST);
//        }
//    }
//}

//package com.example.decoration_backend_springboot.API;
//
//import com.example.decoration_backend_springboot.Model.Customer;
//import com.example.decoration_backend_springboot.Model.Enum.Role;
//import com.example.decoration_backend_springboot.Model.User;
//import com.example.decoration_backend_springboot.Repository.CustomerRepository;
//import com.example.decoration_backend_springboot.Repository.UserRepository;
//import com.example.decoration_backend_springboot.Service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
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
//    private  final CustomerRepository customerRepository;
//
//    private  final UserRepository userRepository;
//    private PasswordEncoder passwordEncoder;
//
//    public CustomerAPI(CustomerRepository customerRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.customerRepository = customerRepository;
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//
//    // GET all customers
//    @GetMapping("/get/all")
//    public ResponseEntity<?> getAllCustomers() {
//        try {
//            List<Customer> customerList = customerService.findAll();
//            if (customerList.isEmpty()) {
//                return new ResponseEntity<>("No customers found", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(customerList, HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to retrieve customers: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // GET customer by ID
//    @GetMapping("/get/{customer_id}")
//    public ResponseEntity<?> getCustomerById(@PathVariable int customer_id) {
//        try {
//            Optional<Customer> optionalCustomer = customerService.findById(customer_id);
//            if (optionalCustomer.isPresent()) {
//                return new ResponseEntity<>(optionalCustomer.get(), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to retrieve customer: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
////    @PostMapping("/add/users")
////    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> payload) {
////        try {
////            // Debug: Print all received payload
////            System.out.println("=== DEBUG: Received Payload ===");
////            System.out.println("Full payload: " + payload);
////
////            String email = payload.get("email");
////            String password = payload.get("password");
////            String name = payload.get("name");
////            String roleStr = payload.get("role");
////            String custAddress = payload.get("custAddress");
////            String phone = payload.get("phone");
////
////            // Debug individual fields
////            System.out.println("Email: " + email);
////            System.out.println("Name: " + name);
////            System.out.println("Role: " + roleStr);
////            System.out.println("Address: " + custAddress);
////            System.out.println("Phone: " + phone);
////
////            // Validate name is not null
////            if (name == null) {
////                return ResponseEntity.badRequest().body("❌ Name field is missing in request payload");
////            }
////            if (name.trim().isEmpty()) {
////                return ResponseEntity.badRequest().body("❌ Name cannot be empty");
////            }
////
////            if (userRepository.findByEmail(email).isPresent()) {
////                return ResponseEntity.badRequest().body("❌ Email is already registered.");
////            }
////
////            Role role = Role.valueOf(roleStr.toUpperCase());
////            String encryptedPassword = passwordEncoder.encode(password);
////
////            // Create and save User
////            User user = new User();
////            user.setEmail(email);
////            user.setName(name);  // Make sure this is being set
////            user.setPassword(encryptedPassword);
////            user.setRole(role);
////
////            System.out.println("=== DEBUG: User object before save ===");
////            System.out.println("User email: " + user.getEmail());
////            System.out.println("User name: " + user.getName());  // Check if name is set here
////            System.out.println("User role: " + user.getRole());
////
////            User savedUser = userRepository.save(user);
////
////            System.out.println("=== DEBUG: After User save ===");
////            System.out.println("Saved User ID: " + savedUser.getUserId());
////            System.out.println("Saved User Name: " + savedUser.getName());
////
////            if (role == Role.CUSTOMER) {
////                Customer customer = new Customer();
////                customer.setName(name);  // This should set the name in the inherited User fields
////                customer.setPhone(phone);
////                customer.setCustAddress(custAddress);
////                customer.setRole(Role.CUSTOMER);
////                customer.setUser((org.apache.catalina.User) savedUser);  // Make sure this relationship is correct
////
////                System.out.println("=== DEBUG: Customer object before save ===");
////                System.out.println("Customer name: " + customer.getName());
////
////                customerRepository.save(customer);
////            }
////
////            return ResponseEntity.ok("✅ User registered successfully: " + email);
////
////        } catch (Exception e) {
////            e.printStackTrace();  // Print full stack trace
////            return ResponseEntity.badRequest().body("❌ Error registering user: " + e.getMessage());
////        }
////    }
//    // GET customers by name (search)
//    @GetMapping("/search/name")
//    public ResponseEntity<?> getCustomersByName(@RequestParam String name) {
//        try {
//            List<Customer> customers = customerService.findByNameContaining(name);
//            if (customers.isEmpty()) {
//                return new ResponseEntity<>("No customers found with name: " + name, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(customers, HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to search customers by name: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // GET customer by email
//    @GetMapping("/search/email")
//    public ResponseEntity<?> getCustomerByEmail(@RequestParam String email) {
//        try {
//            Optional<Customer> customer = Optional.ofNullable(customerService.findByEmail(email));
//            if (customer.isPresent()) {
//                return new ResponseEntity<>(customer.get(), HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Customer not found with email: " + email, HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to retrieve customer by email: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // GET customers by role
//    @GetMapping("/search/role")
//    public ResponseEntity<?> getCustomersByRole(@RequestParam Role role) {
//        try {
//            List<Customer> customers = customerService.findByRole(role);
//            if (customers.isEmpty()) {
//                return new ResponseEntity<>("No customers found with role: " + role, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(customers, HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to retrieve customers by role: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // GET customers count
//
//
//    // POST - Add new customer
//    @PostMapping("/add/customer")
//    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
//        try {
//            // Validate required fields
//            if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
//                return new ResponseEntity<>("Email is required", HttpStatus.BAD_REQUEST);
//            }
//
//            Customer savedCustomer = customerService.save(customer);
//            return new ResponseEntity<>("Customer added successfully", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to add customer: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // PUT - Update customer
//    @PutMapping("/update/{customer_id}")
//    public ResponseEntity<?> updateCustomer(@PathVariable int customer_id, @RequestBody Customer customer) {
//        try {
//            Optional<Customer> existingCustomer = customerService.findById(customer_id);
//            if (existingCustomer.isPresent()) {
//                Customer existing = existingCustomer.get();
//
//                // Update only the fields that are provided
//                if (customer.getName()!= null) existing.setName(customer.getName());
//                if (customer.getCustAddress() != null) existing.setCustAddress(customer.getCustAddress());
//                if (customer.getPhone() != null) existing.setPhone(customer.getPhone());
//                if (customer.getEmail() != null) existing.setEmail(customer.getEmail());
//                if (customer.getPassword() != null) existing.setPassword(customer.getPassword());
//                if (customer.getRole() != null) existing.setRole(customer.getRole());
//
//                customerService.save(existing);
//                return new ResponseEntity<>("Customer updated successfully", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to update customer", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // PUT - Fix null fields
//    @PutMapping("/fix-null-fields/{customer_id}")
//    public ResponseEntity<?> fixNullFields(@PathVariable int customer_id, @RequestBody Customer updates) {
//        try {
//            Optional<Customer> optionalCustomer = customerService.findById(customer_id);
//            if (optionalCustomer.isPresent()) {
//                Customer customer = optionalCustomer.get();
//
//                // Fix NULL fields with default values
//                if (customer.getName() == null) customer.setName(updates.getName() != null ? updates.getName() : "Unknown");
//                if (customer.getRole() == null) customer.setRole(Role.valueOf("CUSTOMER"));
//                if (customer.getCustAddress() == null) customer.setCustAddress(updates.getCustAddress() != null ? updates.getCustAddress() : "Not specified");
//                if (customer.getPhone() == null) customer.setPhone(updates.getPhone() != null ? updates.getPhone() : "Not specified");
//
//                customerService.save(customer);
//                return new ResponseEntity<>("Customer fields fixed successfully", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to fix customer fields", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // DELETE - Delete customer by ID
//    @DeleteMapping("/delete/{customer_id}")
//    public ResponseEntity<?> deleteCustomerById(@PathVariable int customer_id) {
//        try {
//            Optional<Customer> existingCustomer = customerService.findById(customer_id);
//            if (existingCustomer.isPresent()) {
//                customerService.deleteById(customer_id);
//                return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to delete customer: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//}

package com.example.decoration_backend_springboot.API;

import com.example.decoration_backend_springboot.Model.Customer;
import com.example.decoration_backend_springboot.Model.Enum.Role;
import com.example.decoration_backend_springboot.Repository.CustomerRepository;
import com.example.decoration_backend_springboot.Repository.UserRepository;
import com.example.decoration_backend_springboot.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/customer")
public class CustomerAPI {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerAPI(CustomerService customerService,
                       CustomerRepository customerRepository,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // GET all customers
    @GetMapping("/get/all")
    public ResponseEntity<?> getAllCustomers() {
        try {
            List<Customer> customerList = customerService.findAll();
            if (customerList.isEmpty()) {
                return new ResponseEntity<>("No customers found", HttpStatus.OK);
            } else {
                return new ResponseEntity<>(customerList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve customers: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET customer by ID
    @GetMapping("/get/{customer_id}")
    public ResponseEntity<?> getCustomerById(@PathVariable int customer_id) {
        try {
            Optional<Customer> optionalCustomer = customerService.findById(customer_id);
            if (optionalCustomer.isPresent()) {
                return new ResponseEntity<>(optionalCustomer.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve customer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET customers by name (search)
    @GetMapping("/search/name")
    public ResponseEntity<?> getCustomersByName(@RequestParam String name) {
        try {
            List<Customer> customers = customerService.findByNameContaining(name);
            if (customers.isEmpty()) {
                return new ResponseEntity<>("No customers found with name: " + name, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(customers, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to search customers by name: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET customer by email
    @GetMapping("/search/email")
    public ResponseEntity<?> getCustomerByEmail(@RequestParam String email) {
        try {
            Customer customer = customerService.findByEmail(email);
            if (customer != null) {
                return new ResponseEntity<>(customer, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Customer not found with email: " + email, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve customer by email: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET customers by role
    @GetMapping("/search/role")
    public ResponseEntity<?> getCustomersByRole(@RequestParam Role role) {
        try {
            List<Customer> customers = customerService.findByRole(role);
            if (customers.isEmpty()) {
                return new ResponseEntity<>("No customers found with role: " + role, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(customers, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve customers by role: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET customers count - IMPLEMENT THIS
    @GetMapping("/count")
    public ResponseEntity<?> getCustomerCount() {
        try {
            long count = customerService.getCustomerCount();
            return new ResponseEntity<>(count, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to get customer count: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST - Add new customer
    @PostMapping("/add/customer")
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        try {
            // Validate required fields
            if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
                return new ResponseEntity<>("Email is required", HttpStatus.BAD_REQUEST);
            }
            if (customer.getName() == null || customer.getName().trim().isEmpty()) {
                return new ResponseEntity<>("Name is required", HttpStatus.BAD_REQUEST);
            }

            // Check if email already exists
            if (customerService.existsByEmail(customer.getEmail())) {
                return new ResponseEntity<>("Email already registered", HttpStatus.BAD_REQUEST);
            }

            // Set default role if not provided
            if (customer.getRole() == null) {
                customer.setRole(Role.CUSTOMER);
            }

            // Encrypt password if provided
            if (customer.getPassword() != null && !customer.getPassword().trim().isEmpty()) {
                customer.setPassword(passwordEncoder.encode(customer.getPassword()));
            }

            Customer savedCustomer = customerService.save(customer);
            return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to add customer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT - Update customer
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

                // Only update password if a new one is provided
                if (customer.getPassword() != null && !customer.getPassword().trim().isEmpty()) {
                    existing.setPassword(passwordEncoder.encode(customer.getPassword()));
                }

                if (customer.getRole() != null) existing.setRole(customer.getRole());

                Customer updatedCustomer = customerService.save(existing);
                return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update customer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT - Fix null fields
    @PutMapping("/fix-null-fields/{customer_id}")
    public ResponseEntity<?> fixNullFields(@PathVariable int customer_id, @RequestBody Customer updates) {
        try {
            Customer fixedCustomer = customerService.fixNullFields(
                    customer_id,
                    updates.getName(),
                    updates.getCustAddress(),
                    updates.getPhone()
            );

            if (fixedCustomer != null) {
                return new ResponseEntity<>(fixedCustomer, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fix customer fields: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE - Delete customer by ID
    @DeleteMapping("/delete/{customer_id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable int customer_id) {
        try {
            if (customerService.existsById(customer_id)) {
                customerService.deleteById(customer_id);
                return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete customer: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}