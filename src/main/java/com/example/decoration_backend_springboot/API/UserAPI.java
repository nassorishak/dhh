package com.example.decoration_backend_springboot.API;

import com.example.decoration_backend_springboot.Model.Customer;
import com.example.decoration_backend_springboot.Model.Enum.Role;
import com.example.decoration_backend_springboot.Model.User;
import com.example.decoration_backend_springboot.Repository.CustomerRepository;
import com.example.decoration_backend_springboot.Repository.UserRepository;
import com.example.decoration_backend_springboot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/users")
public class UserAPI {

    @Autowired
    private UserService userService;

    private  final CustomerRepository customerRepository;

    private  final UserRepository userRepository;


    @Autowired
     private PasswordEncoder passwordEncoder;

    public UserAPI(UserRepository userRepository, CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    @PostMapping("/add/users")
//    public ResponseEntity<String> createUser(@RequestBody User user) {
//        try {
//
//            User userNew = new User();
//            userNew.setRole(user.getRole());
//            userNew.setPassword(user.getPassword());
//            userNew.setLastName(user.getLastName());
//            userNew.setFirstName(user.getFirstName());
//            userNew.setUserId(user.getUserId());
//            userNew.setEmail(user.getEmail());
//            User savedUser = userService.save(userNew);
//            return new ResponseEntity<>("User successfully created with id: " + savedUser.getUserId(), HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Failed to create user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

//    @PostMapping("/add/users")
//    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> payload) {
//        try {
//            String email = payload.get("email");
//            String password = payload.get("password");
//            String firstName = payload.get("firstName");
//            String lastName = payload.get("lastName");
//            String roleStr = payload.get("role");
//            String custAddress = payload.get("custAddress");
//            String phone = payload.get("phoneNo");
//
//            if (userRepository.findByEmail(email).isPresent()) {
//                return ResponseEntity.badRequest().body("❌ Email is already registered.");
//            }
//
//            Role role = Role.valueOf(roleStr.toUpperCase());
//            String encryptedPassword = passwordEncoder.encode(password);
//
//            if (role == Role.CUSTOMER) {
//                Customer customer = new Customer();
//                customer.setEmail(email);
//                customer.setPassword(encryptedPassword);
//                customer.setFirstName(firstName);
//                customer.setLastName(lastName);
//                customer.setCustAddress(custAddress);
//                customer.setPhone(phone);
//                customer.setRole(Role.CUSTOMER);
//
//                customerRepository.save(customer);
//                return ResponseEntity.ok("✅ Customer registered successfully: " + email);
//            } else {
//                User user = new User();
//                user.setEmail(email);
//                user.setPassword(encryptedPassword);
//                user.setFirstName(firstName);
//                user.setLastName(lastName);
//                user.setRole(role);
//
//                userRepository.save(user);
//                return ResponseEntity.ok("✅ User registered successfully: " + email);
//            }
//
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("❌ Error registering user: " + e.getMessage());
//        }
//    }
//
//@PostMapping("/add/users")
//public ResponseEntity<?> registerUser(@RequestBody Map<String, String> payload) {
//    try {
//        String email = payload.get("email");
//        String password = payload.get("password");
//        String Name = payload.get("Name");
//        String roleStr = payload.get("role");
//        String custAddress = payload.get("custAddress");
//        String phone = payload.get("phone"); // must match frontend key
//
//        // Check if email already exists
//        if (userRepository.findByEmail(email).isPresent()) {
//            return ResponseEntity.badRequest().body("❌ Email is already registered.");
//        }
//
//        Role role = Role.valueOf(roleStr.toUpperCase());
//        String encryptedPassword = passwordEncoder.encode(password);
//
//        if (role == Role.CUSTOMER) {
//            Customer customer = new Customer();
//            customer.setEmail(email);
//            customer.setPassword(encryptedPassword);
//            customer.setName(Name);
//            customer.setCustAddress(custAddress);
//            customer.setPhone(phone);
//            customer.setRole(Role.CUSTOMER);
//
//            customerRepository.save(customer);
//            return ResponseEntity.ok("✅ Customer registered successfully: " + email);
//        } else {
//            User user = new User();
//            user.setEmail(email);
//            user.setPassword(encryptedPassword);
//            user.setName(Name);
//            user.setRole(role);
//
//            userRepository.save(user);
//            return ResponseEntity.ok("✅ User registered successfully: " + email);
//        }
//
//    } catch (Exception e) {
//        return ResponseEntity.badRequest().body("❌ Error registering user: " + e.getMessage());
//    }
//}
@PostMapping("/register")
public ResponseEntity<?> registerUser(@RequestBody Map<String, String> payload) {
    try {
        // Debug: Print all received payload
        System.out.println("=== DEBUG: Received Payload ===");
        System.out.println("Full payload: " + payload);

        String email = payload.get("email");
        String password = payload.get("password");
        String name = payload.get("name");
        String roleStr = payload.get("role");
        String custAddress = payload.get("custAddress");
        String phone = payload.get("phone");

        // Debug individual fields
        System.out.println("Email: " + email);
        System.out.println("Name: " + name);
        System.out.println("Role: " + roleStr);
        System.out.println("Address: " + custAddress);
        System.out.println("Phone: " + phone);

        // Validate required fields
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Email is required.");
        }
        if (password == null || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Password is required.");
        }
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Name is required.");
        }
        if (roleStr == null || roleStr.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("❌ Role is required.");
        }

        // Check if email exists
        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.badRequest().body("❌ Email is already registered.");
        }

        Role role = Role.valueOf(roleStr.toUpperCase());
        String encryptedPassword = passwordEncoder.encode(password);

        if (role == Role.CUSTOMER) {
            // Validate customer-specific fields
            if (custAddress == null || custAddress.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("❌ Customer address is required.");
            }
            if (phone == null || phone.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("❌ Phone number is required.");
            }

            // Create and save Customer directly
            Customer customer = new Customer();
            customer.setEmail(email);
            customer.setName(name);
            customer.setPassword(encryptedPassword);
            customer.setRole(role);
            customer.setCustAddress(custAddress);
            customer.setPhone(phone);

            System.out.println("=== DEBUG: Customer object before save ===");
            System.out.println("Customer email: " + customer.getEmail());
            System.out.println("Customer name: " + customer.getName());
            System.out.println("Customer role: " + customer.getRole());
            System.out.println("Customer address: " + customer.getCustAddress());
            System.out.println("Customer phone: " + customer.getPhone());

            Customer savedCustomer = customerRepository.save(customer);

            System.out.println("=== DEBUG: After Customer save ===");
            System.out.println("Saved Customer ID: " + savedCustomer.getUserId());
            System.out.println("Saved Customer Name: " + savedCustomer.getName());

            return ResponseEntity.ok("✅ Customer registered successfully: " + email);

        } else {
            // Handle other roles (VENDOR, ADMIN, etc.)
            User user = new User();
            user.setEmail(email);
            user.setName(name);
            user.setPassword(encryptedPassword);
            user.setRole(role);

            User savedUser = userRepository.save(user);
            return ResponseEntity.ok("✅ User registered successfully: " + email);
        }

    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("❌ Invalid role provided. Valid roles: CUSTOMER, VENDOR, ADMIN");
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body("❌ Error registering user: " + e.getMessage());
    }
}
    @GetMapping("/get/users")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> userList = userService.findAll();
            if (userList.isEmpty()) {
                return new ResponseEntity<>("No users found", HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(userList, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve users: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(@PathVariable int userId, @RequestBody User user) {
        try {
            Optional<User> existingUserOptional = userService.findById(userId);
            if (existingUserOptional.isPresent()) {
                user.setUserId(userId); // Ensure the user id is set correctly
                userService.save(user); // Update the user
                return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found with id: " + userId, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable int userId) {
        try {
            userService.deleteById(userId);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete user: " ,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getByID/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable int userId) {
        try {
            Optional<User> userOptional = userService.findById(userId);
            if (userOptional.isPresent()) {
                return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found with id: " + userId, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to retrieve user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login/{email}")
    public ResponseEntity<?> loginByEmail(@PathVariable String email){
        try {
            Optional<User> userOptional = userService.findByEmail(email);
            if (userOptional.isPresent()){
                return new ResponseEntity<>(userOptional,HttpStatus.OK);
            }else {
                return new ResponseEntity<>("No user with Email "+email, HttpStatus.NOT_FOUND);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Opps",HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> createUserLogin(@RequestBody User user) {
        try {
            String email = user.getEmail();
            String password = user.getPassword();
            User userLogin = userService.authenticate(email, password);

            if (userLogin == null) {
                return new ResponseEntity<>("Invalid user credentials", HttpStatus.UNAUTHORIZED);
            }

            // Return the authenticated user object with a successful status
            return ResponseEntity.ok(userLogin);

        } catch (Exception e) {
            return new ResponseEntity<>("Failed to login: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }





}
