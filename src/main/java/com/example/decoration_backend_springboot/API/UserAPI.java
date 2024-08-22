package com.example.decoration_backend_springboot.API;

import com.example.decoration_backend_springboot.Model.User;
import com.example.decoration_backend_springboot.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/users")
public class UserAPI {

    @Autowired
    private UserService userService;

    @PostMapping("/add/users")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            User userNew = new User();
            userNew.setRole(user.getRole());
            userNew.setPassword(user.getPassword());
            userNew.setLastName(user.getLastName());
            userNew.setFirstName(user.getFirstName());
            userNew.setUserId(user.getUserId());
            userNew.setEmail(user.getEmail());
            User savedUser = userService.save(userNew);
            return new ResponseEntity<>("User successfully created with id: " + savedUser.getUserId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
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
